package io.bsamartins.sandbox.quarkus.graphql

import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.graphql.Description
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Query
import org.eclipse.microprofile.graphql.Source

@GraphQLApi
class FilmResource(private val galaxyService: GalaxyService) {

    @Description("Get all Films from a galaxy far far away")
    @Query("allFilms")
    fun allFilms(): Uni<List<Film>> {
        return galaxyService.allFilms()
    }

    fun heroes(@Source film: Film): Uni<List<Hero>> {
        return galaxyService.getHeroesByFilm(film)
    }
}
