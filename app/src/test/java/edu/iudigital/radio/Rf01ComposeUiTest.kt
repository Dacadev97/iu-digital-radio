package edu.iudigital.radio

import com.google.common.truth.Truth.assertThat
import edu.iudigital.radio.mvp.ComposeUiContract
import org.junit.Test
import java.io.File

/**
 * RF-01 — UI 100% Jetpack Compose.
 * Red hasta que el squad UI marque el contrato e implemente la home Compose (sin layouts XML).
 */
class Rf01ComposeUiTest {

    @Test
    fun homeScreenCompose_debeEstarImplementada() {
        assertThat(ComposeUiContract.homeScreenImplemented)
            .named("ComposeUiContract.homeScreenImplemented — crear RadioHomeScreen y poner true")
            .isTrue()
    }

    @Test
    fun noDebeHaberLayoutsXml() {
        val layoutDir = resolveAppFile("src/main/res/layout")
        if (!layoutDir.exists()) return
        val xml = layoutDir.walkTopDown().filter { it.isFile && it.extension == "xml" }.toList()
        assertThat(xml)
            .named("RF-01 prohíbe res/layout/*.xml — UI solo Compose")
            .isEmpty()
    }

    private fun resolveAppFile(relative: String): File {
        val fromModule = File(relative)
        if (fromModule.exists()) return fromModule
        return File("app/$relative")
    }
}
