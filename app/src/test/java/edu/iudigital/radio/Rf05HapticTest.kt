package edu.iudigital.radio

import com.google.common.truth.Truth.assertWithMessage
import edu.iudigital.radio.api.HapticPort
import edu.iudigital.radio.api.Product
import org.junit.Test

/**
 * RF-05 — Vibración / háptica en Play, Pause y Mute.
 * ROJO hasta que RadioSession invoque HapticPort en esos controles.
 */
class Rf05HapticTest {

    @Test
    fun playPauseMute_debenDispararHaptica() {
        val haptic = RecordingHapticPort()
        val session = Product.radioSession(haptic)

        session.play()
        session.pause()
        session.toggleMute()

        assertWithMessage("Play + Pause + Mute deben llamar HapticPort.tick() (RF-05)")
            .that(haptic.ticks)
            .isAtLeast(3)
    }
}

/** Helper de test (QA) — no es código de producto. */
class RecordingHapticPort : HapticPort {
    var ticks: Int = 0
        private set

    override fun tick() {
        ticks++
    }
}
