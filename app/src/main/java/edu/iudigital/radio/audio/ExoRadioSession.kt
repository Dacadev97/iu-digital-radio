package edu.iudigital.radio.audio

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import edu.iudigital.radio.api.HapticPort
import edu.iudigital.radio.api.RadioSession
import edu.iudigital.radio.api.Station
import edu.iudigital.radio.api.StationCatalog

class ExoRadioSession(
    private val haptic: HapticPort,
    context: Context? = null
) : RadioSession {

    private val exoPlayer: ExoPlayer? = context?.let { ExoPlayer.Builder(it).build() }

    private var stations: List<Station> = StationCatalog.defaults

    //Kotlin deja separar los modificadores de acceso get y set a la hora de definir si queremos que
    // sea private o public, en este caso como isPlaying, isMuted, selectedStationId vienen de una interface que ya los expone
    // como publicos, por lo que debemos dejar el modificador de lectura get como publico, pero el set lo ponemos
    // privado para solo acceder a ellos dentro de la misma clase.
    override var isPlaying: Boolean by mutableStateOf(false)
        private set
    override var isMuted: Boolean by mutableStateOf(false)
        private set
    override var selectedStationId: String? by mutableStateOf(null)
        private set

    override fun play() {
        exoPlayer?.play()
        isPlaying = true
        haptic.tick()
    }

    override fun pause() {
        exoPlayer?.pause()
        isPlaying = false
        haptic.tick()
    }

    override fun toggleMute() {
        isMuted = !isMuted
        exoPlayer?.volume = if (isMuted) 0f else 1f
        haptic.tick()
    }

    override fun selectStation(stationId: String) {
        selectedStationId = stationId
        val station = stations.firstOrNull { it.id == stationId }
        station?.let {
            exoPlayer?.setMediaItem(MediaItem.fromUri(it.streamUrl))
            exoPlayer?.prepare()
            if (isPlaying) exoPlayer?.play()
        }
    }

    override fun updateStations(stations: List<Station>) {
        this.stations = stations
    }

    fun release() {
        exoPlayer?.release()
    }
}