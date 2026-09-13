package edu.iudigital.radio

import com.google.common.truth.Truth.assertThat
import edu.iudigital.radio.mvp.Mvp
import edu.iudigital.radio.mvp.RecordingHapticPort
import org.junit.Test

/**
 * RF-05 — Vibración / háptica en Play, Pause y Mute.
 * Red: stub no invoca HapticPort.
 */
class Rf05HapticTest {

    @Test
    fun playPauseMute_debenDispararHaptica() {
        val haptic = RecordingHapticPort()
        val session = Mvp.radioSession(haptic)

        session.play()
        session.pause()
        session.toggleMute()

        assertThat(haptic.ticks)
            .named("Play + Pause + Mute deben llamar HapticPort.tick() (RF-05)")
            .isAtLeast(3)
    }
}
