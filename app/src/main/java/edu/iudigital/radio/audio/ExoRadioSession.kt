package edu.iudigital.radio.audio

import androidx.media3.exoplayer.ExoPlayer
import edu.iudigital.radio.api.HapticPort
import edu.iudigital.radio.api.RadioSession
import edu.iudigital.radio.api.StationCatalog

class ExoRadioSession(
    private val haptic: HapticPort
) : RadioSession {

    private var exoPlayer: ExoPlayer? = null

    //Kotlin deja separar los modificadores de acceso get y set a la hora de definir si queremos que
    // sea private o public, en este caso como isPlaying, isMuted, selectedStationId vienen de una interface que ya los expone
    // como publicos, por lo que debemos dejar el modificador de lectura get como publico, pero el set lo ponemos
    // privado para solo acceder a ellos dentro de la misma clase.
    override var isPlaying: Boolean = false
        private set
    override var isMuted: Boolean = false
        private set
    override var selectedStationId: String? = null
        private set

    override fun play() {
        exoPlayer?.play()
        isPlaying = true
    }

    override fun pause() {
        exoPlayer?.pause()
        isPlaying = false
    }

    //Aqui no tocamos isPlaying para que mutear no detenga el flag de reproduccion
    override fun toggleMute() {
        isMuted = !isMuted
        exoPlayer?.volume = if (isMuted) 0f else 1f
    }

    override fun selectStation(stationId: String) {
        selectedStationId = stationId
        val station = StationCatalog.defaults.firstOrNull { it.id == stationId }
        // aquí, si exoPlayer existe, hay que darle la nueva URL con setMediaItem + prepare()
    }
}