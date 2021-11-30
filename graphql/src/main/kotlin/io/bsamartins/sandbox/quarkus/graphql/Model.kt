package io.bsamartins.sandbox.quarkus.graphql

import org.eclipse.microprofile.graphql.Ignore
import java.time.LocalDate

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Model

@Model
data class Film(
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
    val name: String,
    val surname: String,
    val height: Double,
    val mass: Int,
    val darkSide: Boolean,
    val lightSaber: LightSaber,
    val episodeIds: List<Int> = emptyList(),
)

enum class LightSaber {
    RED, BLUE, GREEN
}
