package edu.iudigital.radio.hardware

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import edu.iudigital.radio.api.ProfileCamera

/**
 * RF-02 — Cámara de perfil con estado observable por Compose.
 */
class ProfileCameraImpl : ProfileCamera {
    private var _hasProfilePhoto by mutableStateOf(false)
    private var _avatarBitmap by mutableStateOf<Bitmap?>(null)

    override val hasProfilePhoto: Boolean
        get() = _hasProfilePhoto

    override val avatarBitmap: Bitmap?
        get() = _avatarBitmap

    override fun onPreviewCaptured() {
        _hasProfilePhoto = true
    }

    override fun onPreviewCaptured(bitmap: Bitmap) {
        _avatarBitmap = bitmap
        _hasProfilePhoto = true
    }
}
