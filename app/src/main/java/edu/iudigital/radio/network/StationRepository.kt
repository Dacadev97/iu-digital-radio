package edu.iudigital.radio.network

import edu.iudigital.radio.api.Station
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
// Retrofit: la clase principal de la librería, la que arma el motor de red.
// GsonConverterFactory: el "traductor automático" JSON -> Kotlin que usa Gson

class StationRepository {

    private val api: RadioBrowserApi = Retrofit.Builder() //Construimos el objeto Retrofit con el patron Builder
        .baseUrl("https://de1.api.radio-browser.info/") // servidor público estable
        .addConverterFactory(GsonConverterFactory.create()) //Aqui cuando obtenemos la respuesta en JSON la convertimos al tipo List<StationDto> que se definio en RadioBrowserApi usandon GSON
        .build() // Armo el objeto Retrofit final, ya listo para usar
        .create(RadioBrowserApi::class.java)

    suspend fun fetchStations(): List<Station> { //suspend es como el async/await pero en una sola linea, sirve para hacer llamadas al servidor de forma asincrona sin pausar el funcionamiento de la app mientras se recibe la respuesta
        return api.searchStations().map { dto -> //En este caso la verdadera funcionalidad que se comunica con el servidor es api.searchStations() por eso la funcion debe ser suspend, sino no compilaria
            Station(
                id = dto.stationUuid,
                name = dto.name,
                streamUrl = dto.urlResolved,
                genre = dto.tags?.split(",")?.firstOrNull()?.trim()?.replaceFirstChar { it.uppercase() } ?: "General",
                logoUrl = dto.favicon?.takeIf { it.isNotBlank() }
            ) //Este es el nuevo objeto Station que construimos y agregamos al basicamente es List<Station>
        }
    }
}