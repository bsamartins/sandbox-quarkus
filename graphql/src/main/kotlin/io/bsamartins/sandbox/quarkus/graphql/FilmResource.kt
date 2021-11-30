package io.bsamartins.sandbox.quarkus.graphql

import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.graphql.Description
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Mutation
import org.eclipse.microprofile.graphql.Query
import org.eclipse.microprofile.graphql.Source


@GraphQLApi
class FilmResource(private val galaxyService: GalaxyService) {

    @Description("Get all Films from a galaxy far far away")
    @Query("allFilms")
    fun allFilms(): Uni<List<Film>> {
        return galaxyService.allFilms()
    }

    @Query
    fun getHeroesWithSurname(surname: String): Uni<List<Hero>> {
        return galaxyService.getHeroesBySurname(surname)
    }

    @Query
    @Description("Get a Film from a galaxy far far away")
    fun getFilm(filmId: Int): Uni<Film> {
        return galaxyService.getFilm(filmId)
    }

    @Mutation
    fun createHero(hero: Hero): Uni<Hero> {
        return galaxyService.addHero(hero)
    }

    @Mutation
    fun deleteHero(id: Int): Uni<Hero> {
        return galaxyService.deleteHero(id)
    }

    fun heroes(@Source film: Film): Uni<List<Hero>> {
        return galaxyService.getHeroesByFilm(film)
    }
}
