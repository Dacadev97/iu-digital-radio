package edu.iudigital.radio.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.iudigital.radio.api.Product
import edu.iudigital.radio.api.Station
import edu.iudigital.radio.api.StationCatalog
import edu.iudigital.radio.audio.ExoRadioSession
import edu.iudigital.radio.hardware.AndroidHapticPort
import edu.iudigital.radio.network.StationRepository
import androidx.compose.ui.platform.LocalInspectionMode
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import coil.compose.AsyncImage
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.vector.rememberVectorPainter

@Composable
fun RadioHomeScreen() {
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current //Evita instanciar ExoPlayer en la vista previa de Compose (Layoutlib) pasando null
    // como contexto cuando LocalInspectionMode.current es true, previniendo el
    // NullPointerException de AudioCapabilities que veíamos en el Preview.
    val haptic = remember { AndroidHapticPort(context) }
    val session = remember { Product.radioSession(haptic, if (isPreview) null else context) }
    val repository = remember { StationRepository() } // "remember" evita crear un StationRepository nuevo en cada re renderizado de la pantalla

    var stations by remember { mutableStateOf<List<Station>>(emptyList()) }
    var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    val selected = stations.firstOrNull { it.id == selectedId }
    val filteredStations = if (searchQuery.isBlank()) {
        stations
    } else {
        stations.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    // Mantener estación seleccionada sincronizada con el contrato RF-04.
    LaunchedEffect(Unit) {
        try {
            val fetched = repository.fetchStations()
            stations = fetched
            session.updateStations(fetched)
            val firstId = fetched.firstOrNull()?.id
            selectedId = firstId
            firstId?.let { session.selectStation(it) }
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            stations = StationCatalog.defaults
            session.updateStations(StationCatalog.defaults)
            selectedId = StationCatalog.defaults.firstOrNull()?.id
        errorMessage = "No se pudo conectar a Radio Browser. Mostrando catálogo local."
        } finally {
            // "finally" se ejecuta pase lo que pase (éxito o error)
            isLoading = false
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            (session as? ExoRadioSession)?.release()
        }
    }

    // Ahora todo el contenido vive en UN SOLO LazyColumn (con scroll real de punta a
    // punta) y el BottomNav queda fijo abajo gracias a Scaffold(bottomBar = ...).
    Scaffold(
        containerColor = Color(0xFF07111F),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            TopBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it }
            )
            Spacer(Modifier.height(14.dp))
            UserProfileBanner()
            Spacer(Modifier.height(14.dp))
            ProfileHeader(selected?.name ?: "Cargando...")
            Spacer(Modifier.height(14.dp))

            errorMessage?.let { message ->
                Text(message, color = Color(0xFFFF6B6B))
                Spacer(Modifier.height(8.dp))
            }

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF9B5CFF))
                    }
                }
                selected != null -> {
                    PlayerCard(
                        station = selected,
                        isPlaying = session.isPlaying,
                        isMuted = session.isMuted,
                        onPlayPause = {
                            if (session.isPlaying) session.pause() else session.play()
                        },
                        onMute = { session.toggleMute() }
                    )
                    Spacer(Modifier.height(14.dp))
                    Text("Estaciones", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    // Caja con altura restante (weight) que SÍ tiene su propio scroll,
                    // independiente del resto de la pantalla (que ahora no scrollea).
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(filteredStations) { station ->
                            StationRow(station, station.id == selectedId) {
                                selectedId = station.id
                                session.selectStation(station.id)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UserProfileBanner() {
    val context = LocalContext.current
    val camera = remember { Product.profileCamera() }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        // Cancelar (null) no debe crashear ni marcar foto.
        if (bitmap != null) {
            camera.onPreviewCaptured(bitmap)
        }
    }

    // FIX bug cámara: TakePicturePreview abre la cámara del sistema, pero sin el
    // permiso CAMERA concedido en tiempo de ejecución, la app de cámara se cerraba
    // sola y regresaba al escritorio (típico en MIUI/Xiaomi). Este segundo launcher
    // pide el permiso primero; solo si el usuario acepta, ahí sí se abre la cámara.
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) cameraLauncher.launch()
    }

    fun onCameraClick() {
        val hasPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            cameraLauncher.launch()
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF162238)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF26385F))
                    .clickable { onCameraClick() },
                contentAlignment = Alignment.Center
            ) {
                val avatar = camera.avatarBitmap
                if (avatar != null) {
                    Image(
                        bitmap = avatar.asImageBitmap(),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        Icons.Default.AddAPhoto,
                        contentDescription = "Tomar foto",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Perfil de Usuario", color = Color.White, fontWeight = FontWeight.Bold)
                Text(
                    if (camera.hasProfilePhoto) "Foto lista" else "Toca para tomar foto",
                    color = Color(0xFF8E99B6),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun TopBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    var isSearchExpanded by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = {
            isSearchExpanded = !isSearchExpanded
            if (!isSearchExpanded) onSearchQueryChange("")
        }) {
            Icon(Icons.Default.Search, null, tint = Color.White)
        }

        if (isSearchExpanded) {
            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = { Text("Buscar emisora...", color = Color(0xFF8E99B6)) },
                singleLine = true,
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF162238),
                    unfocusedContainerColor = Color(0xFF162238),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color(0xFF9B5CFF),
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        } else {
            Spacer(Modifier.weight(1f))
            Icon(Icons.Default.Mic, null, tint = Color(0xFF9B5CFF))
            Text(" IU Digital Radio", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ProfileHeader(stationName: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(64.dp).clip(CircleShape).background(Color(0xFF162238))
        ) { Icon(Icons.Default.Radio, null, tint = Color(0xFF9B5CFF), modifier = Modifier.size(34.dp)) }
        Spacer(Modifier.width(14.dp))
        Column {
            Text("Ahora en vivo", color = Color(0xFFB8C1D9))
            Text(
                stationName,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text("Tu musica, siempre contigo", color = Color(0xFF8E99B6))
        }
    }
}

@Composable
private fun WaveformVisualizer(isPlaying: Boolean, modifier: Modifier = Modifier) {
    val barCount = 24
    val infiniteTransition = rememberInfiniteTransition(label = "waveform")

    // Una animación independiente por barra, cada una con duración distinta,
    // para que no suban y bajen todas al mismo tiempo (eso se vería como un
    // parpadeo raro, no como una onda natural).
    val barHeights = List(barCount) { index ->
        val duration = 380 + (index % 6) * 90
        infiniteTransition.animateFloat(
            initialValue = 0.15f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = duration, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bar_$index"
        )
    }

    Canvas(modifier = modifier) {
        val barWidth = size.width / (barCount * 3f)
        val gap = barWidth
        val totalContentWidth = barCount * barWidth + (barCount - 1) * gap
        val startX = (size.width - totalContentWidth) / 2f  // margen sobrante repartido en ambos lados

        for (i in 0 until barCount) {
            val heightFraction = if (isPlaying) barHeights[i].value else 0.15f
            val barHeight = size.height * heightFraction
            val x = startX + i * (barWidth + gap)
            drawRoundRect(
                color = Color(0xFF9B5CFF),
                topLeft = Offset(x, (size.height - barHeight) / 2f),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(barWidth / 2f, barWidth / 2f)
            )
        }
    }
}

@Composable
private fun PlayerCard(
    station: Station,
    isPlaying: Boolean,
    isMuted: Boolean,
    onPlayPause: () -> Unit,
    onMute: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
        ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF101A2E)),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth(0.95f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().padding(vertical = 22.dp, horizontal = 20.dp)
            ) {
                AsyncImage(
                    model = station.logoUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF26385F)),
                    contentScale = ContentScale.Crop,
                    error = rememberVectorPainter(Icons.Default.Radio),
                    fallback = rememberVectorPainter(Icons.Default.Radio)
                )
                Spacer(Modifier.height(14.dp))
                WaveformVisualizer(
                    isPlaying = isPlaying,
                    modifier = Modifier.fillMaxWidth().height(70.dp)
                )
                Spacer(Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        24.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FilledIconButton(onClick = onPlayPause, modifier = Modifier.size(64.dp)) {
                        Icon(
                            if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    IconButton(onClick = onMute) {
                        Icon(
                            if (isMuted) Icons.AutoMirrored.Filled.VolumeOff else Icons.AutoMirrored.Filled.VolumeUp,
                            null,
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StationRow(station: Station, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).clickable(onClick = onClick)
            .background(if (selected) Color(0xFF26385F) else Color(0xFF101A2E)).padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = station.logoUrl,
            contentDescription = null,
            modifier = Modifier.size(24.dp).clip(CircleShape),
            contentScale = ContentScale.Crop,
            error = rememberVectorPainter(Icons.Default.MusicNote),
            fallback = rememberVectorPainter(Icons.Default.MusicNote)
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(
                station.name,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                station.genre,
                color = Color(0xFF8E99B6),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (selected) Text("ON", color = Color(0xFF9B5CFF), fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
private fun RadioHomeScreenPreview() {
    RadioHomeScreen()
}
