package edu.iudigital.radio

import com.google.common.truth.Truth.assertWithMessage
import edu.iudigital.radio.api.ComposeUiContract
import org.junit.Test
import java.io.File

/**
 * RF-01 — UI 100% Jetpack Compose.
 * ROJO hasta que UI marque el contrato e implemente la home (sin layouts XML).
 */
class Rf01ComposeUiTest {

    @Test
    fun homeScreenCompose_debeEstarImplementada() {
        assertWithMessage(
            "RF-01: crear RadioHomeScreen (Compose), cablear MainActivity " +
                "y poner ComposeUiContract.homeScreenImplemented = true. Ver docs/ROLES.md"
        ).that(ComposeUiContract.homeScreenImplemented).isTrue()
    }

    @Test
    fun noDebeHaberLayoutsXml() {
        val layoutDir = resolveAppFile("src/main/res/layout")
        if (!layoutDir.exists()) return
        val xml = layoutDir.walkTopDown().filter { it.isFile && it.extension == "xml" }.toList()
        assertWithMessage("RF-01 prohíbe res/layout/*.xml — UI solo Compose")
            .that(xml)
            .isEmpty()
    }

    private fun resolveAppFile(relative: String): File {
        val fromModule = File(relative)
        if (fromModule.exists()) return fromModule
        return File("app/$relative")
    }
}
