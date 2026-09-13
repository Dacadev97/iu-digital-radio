package edu.iudigital.radio

import com.google.common.truth.Truth.assertThat
import edu.iudigital.radio.mvp.Mvp
import org.junit.Test

/**
 * RF-02 — Perfil con cámara (TakePicturePreview).
 * Red: StubProfileCamera no actualiza hasProfilePhoto.
 */
class Rf02ProfileCameraTest {

    @Test
    fun alCapturarPreview_debeMarcarFotoDePerfil() {
        val camera = Mvp.profileCamera()
        assertThat(camera.hasProfilePhoto).isFalse()

        camera.onPreviewCaptured()

        assertThat(camera.hasProfilePhoto)
            .named("Tras TakePicturePreview, el estado de perfil debe tener foto")
            .isTrue()
    }
}
