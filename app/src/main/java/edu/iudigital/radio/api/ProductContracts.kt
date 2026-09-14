package edu.iudigital.radio.api

/**
 * Contratos vacíos del MVP (sin implementación).
 * Cada rol de la célula crea las clases reales en su PR hasta verdear los tests RF.
 * Ver docs/ROLES.md.
 */

/** RF-01 — poner `true` cuando exista la home Compose del MVP. */
object ComposeUiContract {
    const val homeScreenImplemented: Boolean = false
}

/** RF-02 — cámara de perfil. */
interface ProfileCamera {
    val hasProfilePhoto: Boolean
    fun onPreviewCaptured()
}

/** RF-05 — puerto háptico (la implementación de test vive en `src/test`). */
fun interface HapticPort {
    fun tick()
}

/** RF-04 / RF-05 / RF-07 — sesión de reproducción. */
interface RadioSession {
    val isPlaying: Boolean
    val isMuted: Boolean
    val selectedStationId: String?
    fun play()
    fun pause()
    fun toggleMute()
    fun selectStation(stationId: String)
}

/** RF-06 / RF-07 — modelo de emisora (sin catálogo poblado). */
data class Station(
    val id: String,
    val name: String,
    val streamUrl: String,
    val genre: String = "General"
)

/**
 * RF-06 — catálogo. Sin datos a propósito: el getter lanza hasta que UI/Audio implemente.
 */
object StationCatalog {
    val defaults: List<Station>
        get() = throw NotImplementedError(
            "RF-06: implementar StationCatalog.defaults con ≥5 emisoras. Ver docs/ROLES.md"
        )
}

/**
 * Punto de entrada para tests. Sin stubs: NotImplementedError hasta el PR del rol en la célula.
 */
object Product {
    fun profileCamera(): ProfileCamera = DefaultProfileCamera()

    fun radioSession(haptic: HapticPort = HapticPort { }): RadioSession = DefaultRadioSession(haptic)
}

private class DefaultProfileCamera : ProfileCamera {
    override var hasProfilePhoto: Boolean = false
        private set

    override fun onPreviewCaptured() {
        hasProfilePhoto = true
    }
}

private class DefaultRadioSession(
    private val haptic: HapticPort
) : RadioSession {
    override var isPlaying: Boolean = false
        private set

    override var isMuted: Boolean = false
        private set

    override var selectedStationId: String? = null
        private set

    override fun play() {
        isPlaying = true
        haptic.tick()
    }

    override fun pause() {
        isPlaying = false
        haptic.tick()
    }

    override fun toggleMute() {
        isMuted = !isMuted
        haptic.tick()
    }

    override fun selectStation(stationId: String) {
        selectedStationId = stationId
    }
}
