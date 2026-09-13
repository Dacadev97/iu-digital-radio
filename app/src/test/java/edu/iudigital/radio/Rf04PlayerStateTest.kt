package edu.iudigital.radio

import com.google.common.truth.Truth.assertThat
import edu.iudigital.radio.mvp.Mvp
import org.junit.Test

/**
 * RF-04 — Estado de reproducción (play / pause / mute / estación).
 * Red: StubRadioSession no muta estado.
 */
class Rf04PlayerStateTest {

    @Test
    fun play_debePonerIsPlayingTrue() {
        val session = Mvp.radioSession()
        session.play()
        assertThat(session.isPlaying).isTrue()
    }

    @Test
    fun pause_debePonerIsPlayingFalse() {
        val session = Mvp.radioSession()
        session.play()
        session.pause()
        assertThat(session.isPlaying).isFalse()
    }

    @Test
    fun toggleMute_debeAlternarIsMuted() {
        val session = Mvp.radioSession()
        val before = session.isMuted
        session.toggleMute()
        assertThat(session.isMuted).isEqualTo(!before)
        session.toggleMute()
        assertThat(session.isMuted).isEqualTo(before)
    }

    @Test
    fun selectStation_debeActualizarSelectedStationId() {
        val session = Mvp.radioSession()
        session.selectStation("campus-1")
        assertThat(session.selectedStationId).isEqualTo("campus-1")
    }
}
