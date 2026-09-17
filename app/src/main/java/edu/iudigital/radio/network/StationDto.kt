package edu.iudigital.radio.network

import com.google.gson.annotations.SerializedName
// Importamos una herramienta de la librería Gson (la que convierte JSON <-> Kotlin).

/**
 * Representa una estación tal como la devuelve la API de Radio Browser.
 * Los nombres de campo respetan el JSON real de la API (snake_case).
 */
data class StationDto(  // "data class" en Kotlin es una clase pensada solo para GUARDAR datos
    // Ideal para un DTO, que justamente solo transporta datos


    //Con @SerializedName() indico cual es la clave exacta que recibo del JSON que me devuelve
    //el servidor, y le indico el nombre de la variable que quiero que este asociada a esa clave
    @SerializedName("stationuuid") val stationUuid: String,

    @SerializedName("name") val name: String,

    @SerializedName("url_resolved") val urlResolved: String,
    // Este es el campo más importante: es la URL real del stream de audio,
    // la que le vamos a pasar a ExoPlayer con setMediaItem().

    @SerializedName("favicon") val favicon: String?,
    // El "?" al final del tipo (String?) significa "puede ser nulo".
    // ¿Por qué aquí sí y en los anteriores no? Porque no todas las estaciones
    // tienen un logo/ícono cargado en la base de datos de Radio Browser.

    @SerializedName("country") val country: String?,
    // País de la estación. También puede venir vacío/nulo, por eso el "?".

    @SerializedName("tags") val tags: String?
    // Etiquetas o géneros ("rock,talk,news"), vienen como un solo texto separado
    // por comas, no como una lista.
)