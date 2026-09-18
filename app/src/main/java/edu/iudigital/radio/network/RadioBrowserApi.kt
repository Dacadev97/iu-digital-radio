package edu.iudigital.radio.network

import retrofit2.http.GET
import retrofit2.http.Query
// Importamos dos anotaciones de Retrofit: @GET y @Query (para agregar parámetros a la petición).

interface RadioBrowserApi {

    @GET("json/stations/search") //Aqui creamos la peticion GET para consultar las estaciones, ademas definimos la ruta que debe ir despues de la url base que configuramos en StationRepository
    suspend fun searchStations(
        @Query("limit") limit: Int = 30, //@Query sirve para agregar esto como parámetro en la URL: ...search?limit=30
        @Query("hidebroken") hideBroken: Boolean = true, //...&hidebroken=true
        @Query("order") order: String = "clickcount", //&order=clickcount
        @Query("reverse") reverse: Boolean = true, //&reverse=true
        @Query("countrycode") countryCode: String = "CO" //&countrycode=CO
        //entonces la url quedaria: https://de1.api.radio-browser.info/json/stations/search?limit=30&hidebroken=true&order=clickcount&reverse=true&countrycode=CO
    ): List<StationDto> //Este es el tipo de retorno, en este caso la respuesta la queremos en una Lista de StationDto
}