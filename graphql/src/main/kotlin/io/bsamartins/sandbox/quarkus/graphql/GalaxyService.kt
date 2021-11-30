package io.bsamartins.sandbox.quarkus.graphql

import io.smallrye.mutiny.Uni
import java.time.LocalDate
import java.time.Month
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class GalaxyService {
    private val heroes: MutableList<Hero> = mutableListOf()
    private val films: MutableList<Film> = mutableListOf()
    private val directors: MutableMap<Int, Director> = mutableMapOf()

    fun allFilms(): Uni<List<Film>> = films.asUni()

    fun getFilm(id: Int): Uni<Film> {
        return films[id].asUni()
    }

    fun getHeroesByFilm(film: Film): Uni<List<Hero>> {
        return heroes.filter { hero -> hero.episodeIds.contains(film.episodeID) }
            .asUni()
    }

    fun addHero(hero: Hero): Uni<Hero> {
        heroes.add(hero)
        return hero.asUni()
    }

    fun deleteHero(id: Int): Uni<Hero> {
        return heroes.removeAt(id).asUni()
    }

    fun getHeroesBySurname(surname: String): Uni<List<Hero>> {
        return heroes.filter { hero -> hero.surname == surname }.asUni()
    }

    fun getDirector(id: Int): Uni<Director> = directors.getValue(id).asUni()

    init {
        val georgeLucas = Director(id = 0, name = "George Lucas")
        directors[georgeLucas.id!!] = georgeLucas

        val aNewHope = Film(
            title = "A New Hope",
            releaseDate = LocalDate.of(1977, Month.MAY, 25),
            episodeID = 4,
            directorId = georgeLucas.id,
        )

        val theEmpireStrikesBack = Film(
            title = "The Empire Strikes Back",
            releaseDate = LocalDate.of(1980, Month.MAY, 21),
            episodeID = 5,
            directorId = georgeLucas.id,
        )

        val returnOfTheJedi = Film(
            title = "Return Of The Jedi",
            releaseDate = LocalDate.of(1983, Month.MAY, 25),
            episodeID = 6,
            directorId = georgeLucas.id,
        )

        films.add(aNewHope)
        films.add(theEmpireStrikesBack)
        films.add(returnOfTheJedi)

        val luke = Hero(
            name = "Luke",
            surname = "Skywalker",
            height = 1.7,
            mass = 73,
            lightSaber = LightSaber.GREEN,
            darkSide = false,
            episodeIds = listOf(4, 5, 6),
        )

        val leia = Hero(
            name = "Leia",
            surname = "Organa",
            height = 1.5,
            mass = 51,
            darkSide = false,
            lightSaber = LightSaber.BLUE,
            episodeIds = listOf(4, 5, 6),
        )

        val vader = Hero(
            name = "Darth",
            surname = "Vader",
            height = 1.9,
            mass = 89,
            darkSide = true,
            lightSaber = LightSaber.RED,
            episodeIds = listOf(4, 5, 6),
        )

        heroes.add(luke)
        heroes.add(leia)
        heroes.add(vader)
    }
}
