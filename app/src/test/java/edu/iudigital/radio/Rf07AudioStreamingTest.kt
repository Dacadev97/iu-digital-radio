package edu.iudigital.radio

import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import edu.iudigital.radio.api.Product
import org.junit.Ignore
import org.junit.Test
import java.io.File

/**
 * RF-07 — Audio streaming (Media3).
 * ROJO: sin RadioSession real y sin dependencia media3-exoplayer en Gradle.
 */
class Rf07AudioStreamingTest {

    @Test
    fun play_debeActivarReproduccion() {
        val session = Product.radioSession()
        session.selectStation("1")
        session.play()
        assertWithMessage("RF-07: play() debe iniciar reproducción (Media3)")
            .that(session.isPlaying)
            .isTrue()
    }

    @Test
    fun mute_debeSilenciarSinDetenerFlagDePlayingSiAplica() {
        val session = Product.radioSession()
        session.selectStation("1")
        session.play()
        session.toggleMute()
        assertThat(session.isMuted).isTrue()
        assertThat(session.isPlaying).isTrue()
    }

    @Test
    fun alCambiarEmisora_playDebeUsarEstacionSeleccionada() {
        val session = Product.radioSession()
        session.selectStation("alpha")
        session.play()
        assertThat(session.selectedStationId).isEqualTo("alpha")
        assertThat(session.isPlaying).isTrue()

        session.selectStation("beta")
        assertThat(session.selectedStationId).isEqualTo("beta")
    }

    @Test
    @Ignore("Pendiente por configuración de Media3 RF-07")
    fun dependenciaMedia3_debeEstarEnGradle() {
        val gradle = readAppGradle()
        assertWithMessage("app/build.gradle.kts debe incluir media3-exoplayer (RF-07 Audio)")
            .that(gradle)
            .contains("media3-exoplayer")
    }

    private fun readAppGradle(): String {
        val candidates = listOf(
            File("build.gradle.kts"),
            File("app/build.gradle.kts")
        )
        val file = candidates.firstOrNull { it.isFile }
            ?: error("No se encontró app/build.gradle.kts")
        return file.readText()
    }
}
