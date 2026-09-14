package edu.iudigital.radio.api

/**
 * Contratos vacíos del MVP (sin implementación).
 * Cada rol de la célula crea las clases reales en su PR hasta verdear los tests RF.
 * Ver docs/ROLES.md.
 */

/** RF-01 — poner `true` cuando exista la home Compose del MVP. */
object ComposeUiContract {
    const val homeScreenImplemented: Boolean = true
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
    val defaults: List<Station> = listOf(
        Station(
            id = "iu-live",
            name = "IU Digital Radio",
            streamUrl = "https://stream.zeno.fm/iu-digital-radio",
            genre = "Institucional"
        ),
        Station(
            id = "campus-pop",
            name = "Campus Pop",
            streamUrl = "https://stream.zeno.fm/campus-pop",
            genre = "Pop"
        ),
        Station(
            id = "rock-classic",
            name = "Rock Clasico",
            streamUrl = "https://stream.zeno.fm/rock-classic",
            genre = "Rock"
        ),
        Station(
            id = "urbana",
            name = "Frecuencia Urbana",
            streamUrl = "https://stream.zeno.fm/frecuencia-urbana",
            genre = "Urbana"
        ),
        Station(
            id = "electro-night",
            name = "Electro Night",
            streamUrl = "https://stream.zeno.fm/electro-night",
            genre = "Electronica"
        )
    )
}


/**
 * Punto de entrada para tests. Sin stubs: NotImplementedError hasta el PR del rol en la célula.
 */
object Product {
    fun profileCamera(): ProfileCamera =
        throw NotImplementedError(
            "RF-02: rol Hardware — implementar ProfileCamera. Ver docs/ROLES.md"
        )

    fun radioSession(@Suppress("UNUSED_PARAMETER") haptic: HapticPort = HapticPort { }): RadioSession =
        throw NotImplementedError(
            "RF-04/RF-05/RF-07: roles State/Audio/Hardware — implementar RadioSession. Ver docs/ROLES.md"
        )
}
