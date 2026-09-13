package edu.iudigital.radio

/**
 * Modelo de dominio de una emisora.
 * RF-06 / RF-07 — lista + stream URL.
 */
data class Station(
    val id: String,
    val name: String,
    val streamUrl: String,
    val genre: String = "General"
)

/**
 * STUB — catálogo vacío a propósito (TDD rojo).
 * Squad UI/Audio: popular con ≥5 emisoras reales o mock con URLs de stream.
 */
object StationCatalog {
    val defaults: List<Station> = emptyList()
}
