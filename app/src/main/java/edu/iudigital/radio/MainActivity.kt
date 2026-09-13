package edu.iudigital.radio

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.graphics.Bitmap

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RadioHomeScreen()
                }
            }
        }
    }
}

@Composable
fun RadioHomeScreen(
    stations: List<Station> = StationCatalog.defaults
) {
    val context = LocalContext.current
    var isPlaying by rememberSaveable { mutableStateOf(false) }
    var isMuted by rememberSaveable { mutableStateOf(false) }
    var selectedStationId by rememberSaveable {
        mutableStateOf(stations.firstOrNull()?.id ?: "")
    }
    var profileBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val selected = stations.firstOrNull { it.id == selectedStationId } ?: stations.first()

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        profileBitmap = bitmap?.asImageBitmap()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProfileHeader(
            photo = profileBitmap,
            onTakePhoto = { cameraLauncher.launch(null) }
        )

        PlayerCard(
            station = selected,
            isPlaying = isPlaying,
            isMuted = isMuted,
            onPlay = {
                hapticTick(context)
                isPlaying = true
            },
            onPause = {
                hapticTick(context)
                isPlaying = false
            },
            onMuteToggle = {
                hapticTick(context)
                isMuted = !isMuted
            }
        )

        Text(
            text = "Emisoras",
            style = MaterialTheme.typography.titleMedium
        )
        StationList(
            stations = stations,
            selectedId = selectedStationId,
            onSelect = { station ->
                selectedStationId = station.id
                // Al cambiar emisora, dejamos listo el estado de reproducción
                isPlaying = false
            }
        )
    }
}

@Composable
private fun ProfileHeader(
    photo: ImageBitmap?,
    onTakePhoto: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (photo != null) {
                Image(
                    bitmap = photo,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(16.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("Estudiante IU Digital", style = MaterialTheme.typography.titleMedium)
                Text("Toca para capturar foto de perfil", style = MaterialTheme.typography.bodySmall)
            }
            Button(onClick = onTakePhoto) {
                Text("Cámara")
            }
        }
    }
}

@Composable
private fun PlayerCard(
    station: Station,
    isPlaying: Boolean,
    isMuted: Boolean,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onMuteToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(station.name, style = MaterialTheme.typography.titleLarge)
            Text(station.genre, style = MaterialTheme.typography.bodyMedium)
            Text(
                if (isPlaying) "Reproduciendo…" else "Pausado",
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = onPlay) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Play")
                }
                IconButton(onClick = onPause) {
                    Icon(Icons.Default.Pause, contentDescription = "Pause")
                }
                IconButton(onClick = onMuteToggle) {
                    Icon(
                        if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                        contentDescription = "Mute"
                    )
                }
            }
        }
    }
}

@Composable
private fun StationList(
    stations: List<Station>,
    selectedId: String,
    onSelect: (Station) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(stations, key = { it.id }) { station ->
            val selected = station.id == selectedId
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(station) },
                colors = CardDefaults.cardColors(
                    containerColor = if (selected) {
                        MaterialTheme.colorScheme.secondaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(station.name, style = MaterialTheme.typography.titleSmall)
                    Text(station.genre, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

/** RF-05: retroalimentación háptica corta en controles. */
fun hapticTick(context: Context) {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val manager = context.getSystemService(VibratorManager::class.java)
        manager?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    } ?: return

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(40, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(40)
    }
}

@Preview(showBackground = true)
@Composable
private fun RadioHomePreview() {
    MaterialTheme { RadioHomeScreen() }
}
