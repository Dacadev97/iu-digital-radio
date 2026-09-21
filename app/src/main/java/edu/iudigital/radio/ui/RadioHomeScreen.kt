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
import androidx.compose.ui.graphics.Brush
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

    val selected = stations.firstOrNull { it.id == selectedId }

    // Mantener estación seleccionada sincronizada con el contrato RF-04.
    LaunchedEffect(Unit) {
        try {
            val fetched = repository.fetchStations()
            stations = fetched
            session.updateStations(fetched)
            val firstId = fetched.firstOrNull()?.id
            selectedId = firstId
            firstId?.let { session.selectStation(it) }
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
        bottomBar = { BottomNav() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item { TopBar() }
            item { UserProfileBanner() }
            item { ProfileHeader(selected?.name ?: "Cargando...") }

            errorMessage?.let { message ->
                item { Text(message, color = Color(0xFFFF6B6B)) }
            }

            when {
                isLoading -> item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF9B5CFF))
                    }
                }
                selected != null -> {
                    item {
                        PlayerCard(
                            station = selected,
                            isPlaying = session.isPlaying,
                            isMuted = session.isMuted,
                            onPlayPause = {
                                if (session.isPlaying) session.pause() else session.play()
                            },
                            onMute = { session.toggleMute() }
                        )
                    }
                    item { Text("Estaciones", color = Color.White, fontWeight = FontWeight.Bold) }
                    items(stations) { station ->
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
private fun TopBar() {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Icon(Icons.Default.Menu, null, tint = Color.White)
        Spacer(Modifier.weight(1f))
        Icon(Icons.Default.Mic, null, tint = Color(0xFF9B5CFF))
        Text(" IU Digital Radio", color = Color.White, fontWeight = FontWeight.Bold)
        Spacer(Modifier.weight(1f))
        Icon(Icons.Default.Search, null, tint = Color.White)
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
            Text(stationName, color = Color.White, fontWeight = FontWeight.Bold)
            Text("Tu musica, siempre contigo", color = Color(0xFF8E99B6))
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

    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF101A2E)), shape = RoundedCornerShape(24.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(22.dp)) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(150.dp).clip(CircleShape).background(
                    Brush.radialGradient(listOf(Color(0xFF9B5CFF), Color(0xFF101A2E)))
                )
            ) { Icon(Icons.Default.GraphicEq, null, tint = Color.White, modifier = Modifier.size(70.dp)) }
            Spacer(Modifier.height(14.dp))
            Text(station.name, color = Color.White, fontWeight = FontWeight.Bold)
            Text(station.genre, color = Color(0xFF8E99B6))
            Slider(value = if (isPlaying) 0.42f else 0.0f, onValueChange = {})
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {}) { Icon(Icons.Default.SkipPrevious, null, tint = Color.White) }
                FilledIconButton(onClick = onPlayPause, modifier = Modifier.size(58.dp)) {
                    Icon(if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow, null)
                }
                IconButton(onClick = {}) { Icon(Icons.Default.SkipNext, null, tint = Color.White) }
                IconButton(onClick = onMute) {
                    Icon(if (isMuted) Icons.AutoMirrored.Filled.VolumeOff else Icons.AutoMirrored.Filled.VolumeUp, null, tint = Color.White)
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
        Icon(Icons.Default.MusicNote, null, tint = Color(0xFF9B5CFF))
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

@Composable
private fun BottomNav() {
    Row(Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceAround) {
        Icon(Icons.Default.Home, null, tint = Color(0xFF9B5CFF))
        Icon(Icons.Default.Search, null, tint = Color(0xFF8E99B6))
        Icon(Icons.Default.Favorite, null, tint = Color(0xFF8E99B6))
        Icon(Icons.Default.MoreHoriz, null, tint = Color(0xFF8E99B6))
    }
}

@Preview(showBackground = true)
@Composable
private fun RadioHomeScreenPreview() {
    RadioHomeScreen()
}
