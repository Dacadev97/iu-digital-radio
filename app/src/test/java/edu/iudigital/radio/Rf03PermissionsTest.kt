package edu.iudigital.radio

import com.google.common.truth.Truth.assertWithMessage
import org.junit.Test
import java.io.File

/**
 * RF-03 — Permisos CAMERA / VIBRATE (+ INTERNET para streams).
 * ROJO: Manifest shell sin esos permisos.
 */
class Rf03PermissionsTest {

    @Test
    fun manifest_debeDeclararCameraVibrateEInternet() {
        val manifest = readManifest()
        assertDeclaresPermission(manifest, "android.permission.CAMERA")
        assertDeclaresPermission(manifest, "android.permission.VIBRATE")
        assertDeclaresPermission(manifest, "android.permission.INTERNET")
    }

    private fun assertDeclaresPermission(manifest: String, name: String) {
        assertWithMessage("AndroidManifest debe declarar $name — RF-03 Hardware. Ver docs/ROLES.md")
            .that(manifest)
            .contains("android:name=\"$name\"")
    }

    private fun readManifest(): String {
        val candidates = listOf(
            File("src/main/AndroidManifest.xml"),
            File("app/src/main/AndroidManifest.xml")
        )
        val file = candidates.firstOrNull { it.isFile }
            ?: error("No se encontró AndroidManifest.xml")
        return file.readText()
    }
}
