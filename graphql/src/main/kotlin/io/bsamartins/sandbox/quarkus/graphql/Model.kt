package io.bsamartins.sandbox.quarkus.graphql

import java.time.LocalDate

class Film {
    var title: String? = null
    var episodeID: Int? = null
    var director: String? = null
    var releaseDate: LocalDate? = null
}

class Hero {
    var name: String? = null
    var surname: String? = null
    var height: Double? = null
    var mass: Int? = null
    var darkSide: Boolean? = null
    var lightSaber: LightSaber? = null
    var episodeIds: List<Int> = emptyList()
}

enum class LightSaber {
    RED, BLUE, GREEN
}
