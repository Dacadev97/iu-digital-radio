package edu.iudigital.radio

/**
 * Modelo de dominio de una emisora/pista.
 * PO: RF-06 / RF-07 — lista dinámica + reproducción.
 */
data class Station(
    val id: String,
    val name: String,
    val streamUrl: String,
    val genre: String = "General"
)

object StationCatalog {
    /** Catálogo inicial (mock). Audio squad puede reemplazar por Radio Browser API. */
    val defaults: List<Station> = listOf(
        Station("1", "IU Digital News", "https://stream.example/iud-news", "Noticias"),
        Station("2", "Campus Beats", "https://stream.example/campus-beats", "Música"),
        Station("3", "Tech Talks", "https://stream.example/tech-talks", "Educación"),
        Station("4", "Chocó Vibes", "https://stream.example/choco-vibes", "Regional"),
        Station("5", "Study Focus", "https://stream.example/study-focus", "Instrumental"),
    )
}
