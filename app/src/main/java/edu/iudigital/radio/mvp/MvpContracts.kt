package edu.iudigital.radio.mvp

/**
 * Contratos del MVP. Los stubs fallan los tests a propósito (Red).
 * Los developers reemplazan el comportamiento hasta Green.
 */

/** RF-01: señal de que la home Compose del MVP existe (no el stub de MainActivity). */
object ComposeUiContract {
    /** Debe ser `true` cuando exista `RadioHomeScreen` (o equivalente) 100% Compose. */
    const val homeScreenImplemented: Boolean = false
}

/** RF-02: cámara de perfil vía TakePicturePreview. */
interface ProfileCamera {
    val hasProfilePhoto: Boolean
    fun onPreviewCaptured()
}

class StubProfileCamera : ProfileCamera {
    override val hasProfilePhoto: Boolean = false
    override fun onPreviewCaptured() {
        // sin efecto — TDD rojo
    }
}

/** RF-04 / RF-05 / RF-07: sesión de reproducción. */
interface RadioSession {
    val isPlaying: Boolean
    val isMuted: Boolean
    val selectedStationId: String?
    fun play()
    fun pause()
    fun toggleMute()
    fun selectStation(stationId: String)
}

/**
 * Stub no funcional. Implementación real: Media3 + estado Compose / ViewModel.
 */
class StubRadioSession(
    @Suppress("UNUSED_PARAMETER") haptic: HapticPort = NoOpHapticPort
) : RadioSession {
    override val isPlaying: Boolean = false
    override val isMuted: Boolean = false
    override val selectedStationId: String? = null

    override fun play() {
        // no-op — no dispara háptica ni audio (TDD rojo)
    }

    override fun pause() {}

    override fun toggleMute() {}

    override fun selectStation(stationId: String) {}
}

/** RF-05: retroalimentación háptica en controles. */
fun interface HapticPort {
    fun tick()
}

object NoOpHapticPort : HapticPort {
    override fun tick() {}
}

/** Puerto de grabación para tests unitarios. */
class RecordingHapticPort : HapticPort {
    var ticks: Int = 0
        private set

    override fun tick() {
        ticks++
    }
}

/**
 * Factory del MVP. Developers pueden sustituir por DI real;
 * los tests usan estas fábricas / clases públicas.
 */
object Mvp {
    fun profileCamera(): ProfileCamera = StubProfileCamera()
    fun radioSession(haptic: HapticPort = NoOpHapticPort): RadioSession =
        StubRadioSession(haptic)
}
