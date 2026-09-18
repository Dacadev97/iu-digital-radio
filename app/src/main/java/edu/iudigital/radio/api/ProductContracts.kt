package edu.iudigital.radio.api

import android.content.Context
import android.graphics.Bitmap
import edu.iudigital.radio.audio.ExoRadioSession
import edu.iudigital.radio.hardware.ProfileCameraImpl

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
    val avatarBitmap: Bitmap?
    /** Contrato unitario Rf02 — marca que hay foto de perfil. */
    fun onPreviewCaptured()
    /** Captura real desde TakePicturePreview (cancelar = no llamar). */
    fun onPreviewCaptured(bitmap: Bitmap)
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
    fun updateStations(stations: List<Station>)
}

/** RF-06 / RF-07 — modelo de emisora (sin catálogo poblado). */
data class Station(
    val id: String,
    val name: String,
    val streamUrl: String,
    val genre: String
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
        ProfileCameraImpl()

    fun radioSession(haptic: HapticPort = HapticPort { }, context: Context? = null): RadioSession =
        ExoRadioSession(haptic, context)
}
