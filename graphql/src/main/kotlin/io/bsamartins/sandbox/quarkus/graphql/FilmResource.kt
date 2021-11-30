package io.bsamartins.sandbox.quarkus.graphql

import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.asUni
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.eclipse.microprofile.graphql.Description
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Query
import org.eclipse.microprofile.graphql.Source

@GraphQLApi
class FilmResource(private val galaxyService: GalaxyService) {

    @Description("Get all Films from a galaxy far far away")
    @Query("allFilms")
    fun allFilms(): Uni<List<Film>> {
        return runBlocking { async { galaxyService.allFilms } }.asUni()
    }

    fun heroes(@Source film: Film): Uni<List<Hero>> {
        return runBlocking { async { galaxyService.getHeroesByFilm(film) } }.asUni()
    }
}
