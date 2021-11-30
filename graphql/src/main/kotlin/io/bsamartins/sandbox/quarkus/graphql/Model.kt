package io.bsamartins.sandbox.quarkus.graphql

import org.eclipse.microprofile.graphql.Ignore
import org.eclipse.microprofile.graphql.Input
import java.time.LocalDate

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Model

@Model
data class Film(
    val id: Int? = null,
    val title: String,
    val episodeID: Int,
    @Ignore
    val directorId: Int,
    val releaseDate: LocalDate,
)

@Model
data class Director(val id: Int?, val name: String)

@Model
data class Hero(
    val id: Int? = null,
    val name: String,
    val surname: String,
    val darkSide: Boolean,
    val lightSaber: LightSaber,
    val episodeIds: List<Int> = emptyList(),
)

@Model
@Input("HeroInput")
class HeroInput {
    var name: String? = null
    var surname: String? = null
    var darkSide: Boolean? = null
    var lightSaber: LightSaber? = null
    var episodeIds: List<Int> = emptyList()
}

enum class LightSaber {
    RED, BLUE, GREEN
}
