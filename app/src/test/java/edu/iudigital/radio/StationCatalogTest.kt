package edu.iudigital.radio

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class StationCatalogTest {
    @Test
    fun catalog_hasAtLeastFiveStations() {
        assertThat(StationCatalog.defaults.size).isAtLeast(5)
    }

    @Test
    fun catalog_idsAreUnique() {
        val ids = StationCatalog.defaults.map { it.id }
        assertThat(ids).containsNoDuplicates()
    }

    @Test
    fun station_requiresStreamUrl() {
        StationCatalog.defaults.forEach { station ->
            assertThat(station.streamUrl).isNotEmpty()
            assertThat(station.name).isNotEmpty()
        }
    }
}
