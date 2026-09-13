package edu.iudigital.radio

import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import edu.iudigital.radio.api.StationCatalog
import org.junit.Test

/**
 * RF-06 — LazyColumn / catálogo de emisoras (≥5, ids únicos, stream URL).
 * ROJO: StationCatalog.defaults lanza NotImplementedError hasta el PR de UI/Audio.
 */
class Rf06StationCatalogTest {

    @Test
    fun catalogo_debeTenerAlMenosCincoEmisoras() {
        assertWithMessage("StationCatalog.defaults para LazyColumn")
            .that(StationCatalog.defaults.size)
            .isAtLeast(5)
    }

    @Test
    fun catalogo_idsDebenSerUnicos() {
        val ids = StationCatalog.defaults.map { it.id }
        assertThat(ids).containsNoDuplicates()
    }

    @Test
    fun cadaEmisora_debeTenerNombreYStreamUrl() {
        assertThat(StationCatalog.defaults).isNotEmpty()
        StationCatalog.defaults.forEach { station ->
            assertThat(station.id).isNotEmpty()
            assertThat(station.name).isNotEmpty()
            assertThat(station.streamUrl).isNotEmpty()
        }
    }
}
