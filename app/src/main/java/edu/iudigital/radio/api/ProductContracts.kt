package edu.iudigital.radio.api

/**
 * Contratos vacíos del MVP (sin implementación).
 * Los squads crean las clases reales en sus PRs hasta verdear los tests RF.
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
 * Punto de entrada para tests. Sin stubs: siempre NotImplementedError hasta el PR del squad.
 */
object Product {
    fun profileCamera(): ProfileCamera =
        throw NotImplementedError(
            "RF-02: Hardware debe implementar ProfileCamera y devolverla aquí. Ver docs/ROLES.md"
        )

    fun radioSession(@Suppress("UNUSED_PARAMETER") haptic: HapticPort = HapticPort { }): RadioSession =
        throw NotImplementedError(
            "RF-04/RF-05/RF-07: State/Audio/Hardware deben implementar RadioSession. Ver docs/ROLES.md"
        )
}
