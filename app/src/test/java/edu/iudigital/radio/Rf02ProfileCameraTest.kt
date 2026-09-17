package edu.iudigital.radio

import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import edu.iudigital.radio.api.Product
import org.junit.Ignore
import org.junit.Test

/**
 * RF-02 — Perfil con cámara (TakePicturePreview).
 * ROJO: Product.profileCamera() lanza NotImplementedError hasta el PR de Hardware.
 */
class Rf02ProfileCameraTest {

    @Test
    @Ignore("Pendiente por integración de RF-02")
    fun alCapturarPreview_debeMarcarFotoDePerfil() {
        val camera = Product.profileCamera()
        assertThat(camera.hasProfilePhoto).isFalse()

        camera.onPreviewCaptured()

        assertWithMessage("Tras TakePicturePreview, el estado de perfil debe tener foto")
            .that(camera.hasProfilePhoto)
            .isTrue()
    }
}
