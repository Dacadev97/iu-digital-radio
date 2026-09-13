package edu.iudigital.radio

import com.google.common.truth.Truth.assertThat
import edu.iudigital.radio.api.Product
import org.junit.Test

/**
 * RF-04 — Estado de reproducción (play / pause / mute / estación).
 * ROJO: Product.radioSession() lanza NotImplementedError hasta el PR de State.
 */
class Rf04PlayerStateTest {

    @Test
    fun play_debePonerIsPlayingTrue() {
        val session = Product.radioSession()
        session.play()
        assertThat(session.isPlaying).isTrue()
    }

    @Test
    fun pause_debePonerIsPlayingFalse() {
        val session = Product.radioSession()
        session.play()
        session.pause()
        assertThat(session.isPlaying).isFalse()
    }

    @Test
    fun toggleMute_debeAlternarIsMuted() {
        val session = Product.radioSession()
        val before = session.isMuted
        session.toggleMute()
        assertThat(session.isMuted).isEqualTo(!before)
        session.toggleMute()
        assertThat(session.isMuted).isEqualTo(before)
    }

    @Test
    fun selectStation_debeActualizarSelectedStationId() {
        val session = Product.radioSession()
        session.selectStation("campus-1")
        assertThat(session.selectedStationId).isEqualTo("campus-1")
    }
}
