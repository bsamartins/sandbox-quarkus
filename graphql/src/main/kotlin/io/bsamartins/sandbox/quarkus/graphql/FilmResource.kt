package io.bsamartins.sandbox.quarkus.graphql

import org.eclipse.microprofile.graphql.Description
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Mutation
import org.eclipse.microprofile.graphql.Query
import org.eclipse.microprofile.graphql.Source
import org.slf4j.LoggerFactory

@GraphQLApi
class FilmResource(private val service: GalaxyService) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Description("Get all Films from a galaxy far far away")
    @Query("allFilms")
    fun allFilms(): List<Film> {
        return service.allFilms()
    }

    @Query
    fun getHeroesWithSurname(surname: String): List<Hero> {
        return service.getHeroesBySurname(surname)
    }

    @Query
    @Description("Get a Film from a galaxy far far away")
    fun getFilm(filmId: Int): Film {
        return service.getFilm(filmId)
    }

    @Mutation
    fun createHero(input: HeroInput): Hero {
        return service.addHero(
            Hero(
                name = input.name!!,
                surname = input.surname!!,
                lightSaber = input.lightSaber!!,
                darkSide = input.darkSide!!,
            )
        )
    }

    @Mutation
    fun deleteHero(id: Int): Hero {
        return service.deleteHero(id)
    }

    fun director(@Source films: List<Film>): List<Director?> {
        logger.info("Fetching director for films {}", films)
        val directors = mutableMapOf<Int, Director>()
        return films.map { film ->
            film.directorId ?: return@map null
            return@map directors.computeIfAbsent(film.directorId) { id ->
                logger.info("Fetching director for {}", film)
                service.getDirector(id)
            }
        }
    }

    fun heroes(@Source film: Film): List<Hero>? {
        film.heroIds ?: return null
        logger.info("Fetching heroes for {}", film)
        return service.getHeroesByFilm(film.heroIds)
    }
}
