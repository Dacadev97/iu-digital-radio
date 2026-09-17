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
import edu.iudigital.radio.hardware.AndroidHapticPort

private data class UiStation(val id: String, val name: String, val genre: String)

private val stations = listOf(
    UiStation("iu-live", "IU Digital Radio", "Institucional"),
    UiStation("campus-pop", "Campus Pop", "Pop"),
    UiStation("rock-classic", "Rock Clasico", "Rock"),
    UiStation("frecuencia-urbana", "Frecuencia Urbana", "Urbana"),
    UiStation("electro-night", "Electro Night", "Electronica")
)

@Composable
fun RadioHomeScreen() {
    val context = LocalContext.current
    val haptic = remember { AndroidHapticPort(context) }
    val session = remember { Product.radioSession(haptic) }

    var selectedId by rememberSaveable { mutableStateOf(stations.first().id) }
    val selected = stations.first { it.id == selectedId }

    // Mantener estación seleccionada sincronizada con el contrato RF-04.
    LaunchedEffect(Unit) {
        session.selectStation(selectedId)
    }

    Surface(color = Color(0xFF07111F), modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            TopBar()
            Spacer(Modifier.height(18.dp))
            UserProfileBanner()
            Spacer(Modifier.height(18.dp))
            ProfileHeader()
            Spacer(Modifier.height(18.dp))
            PlayerCard(
                station = selected,
                isPlaying = session.isPlaying,
                isMuted = session.isMuted,
                onPlayPause = {
                    if (session.isPlaying) session.pause() else session.play()
                },
                onMute = { session.toggleMute() }
            )
            Spacer(Modifier.height(18.dp))
            Text("Estaciones", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(stations) { station ->
                    StationRow(station, station.id == selectedId) {
                        selectedId = station.id
                        session.selectStation(station.id)
                    }
                }
            }
            BottomNav()
        }
    }
}

@Composable
private fun UserProfileBanner() {
    val camera = remember { Product.profileCamera() }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            camera.onPreviewCaptured(bitmap)
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
                    .clickable { launcher.launch() },
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
private fun ProfileHeader() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(64.dp).clip(CircleShape).background(Color(0xFF162238))
        ) { Icon(Icons.Default.Radio, null, tint = Color(0xFF9B5CFF), modifier = Modifier.size(34.dp)) }
        Spacer(Modifier.width(14.dp))
        Column {
            Text("Ahora en vivo", color = Color(0xFFB8C1D9))
            Text("IU Digital Radio", color = Color.White, fontWeight = FontWeight.Bold)
            Text("Tu musica, siempre contigo", color = Color(0xFF8E99B6))
        }
    }
}

@Composable
private fun PlayerCard(
    station: UiStation,
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
                    Icon(if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp, null, tint = Color.White)
                }
            }
        }
    }
}

@Composable
private fun StationRow(station: UiStation, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).clickable(onClick = onClick)
            .background(if (selected) Color(0xFF26385F) else Color(0xFF101A2E)).padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.MusicNote, null, tint = Color(0xFF9B5CFF))
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(station.name, color = Color.White, fontWeight = FontWeight.SemiBold)
            Text(station.genre, color = Color(0xFF8E99B6))
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