package edu.iudigital.radio

import com.google.common.truth.Truth.assertThat
import edu.iudigital.radio.mvp.Mvp
import org.junit.Test

/**
 * RF-07 — Audio streaming (Media3): play/pause/mute afectan la sesión de audio.
 * Red: stub sin motor de audio / sin cambio de estado.
 *
 * Nota: la integración real con ExoPlayer la hace el squad Audio;
 * estos tests validan el contrato de sesión que debe cablearse al player.
 */
class Rf07AudioStreamingTest {

    @Test
    fun play_debeActivarReproduccion() {
        val session = Mvp.radioSession()
        session.selectStation("1")
        session.play()
        assertThat(session.isPlaying)
            .named("RF-07: play() debe iniciar reproducción (Media3)")
            .isTrue()
    }

    @Test
    fun mute_debeSilenciarSinDetenerFlagDePlayingSiAplica() {
        val session = Mvp.radioSession()
        session.selectStation("1")
        session.play()
        session.toggleMute()
        assertThat(session.isMuted).isTrue()
        assertThat(session.isPlaying).isTrue()
    }

    @Test
    fun alCambiarEmisora_playDebeUsarEstacionSeleccionada() {
        val session = Mvp.radioSession()
        session.selectStation("alpha")
        session.play()
        assertThat(session.selectedStationId).isEqualTo("alpha")
        assertThat(session.isPlaying).isTrue()

        session.selectStation("beta")
        assertThat(session.selectedStationId).isEqualTo("beta")
    }

    @Test
    fun dependenciaMedia3_debeEstarEnGradle() {
        val gradle = readAppGradle()
        assertThat(gradle)
            .named("app/build.gradle.kts debe incluir media3-exoplayer (RF-07)")
            .contains("media3-exoplayer")
    }

    private fun readAppGradle(): String {
        val candidates = listOf(
            java.io.File("build.gradle.kts"),
            java.io.File("app/build.gradle.kts")
        )
        val file = candidates.firstOrNull { it.isFile }
            ?: error("No se encontró app/build.gradle.kts")
        return file.readText()
    }
}
