package io.bsamartins.sandbox.quarkus.graphql

import io.smallrye.mutiny.Uni
import java.time.LocalDate
import java.time.Month
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class GalaxyService {
    private val heroes: MutableMap<Int, Hero> = mutableMapOf()
    private var heroCounter: Int = 0

    private val films: MutableMap<Int, Film> = mutableMapOf()
    private var filmCounter: Int = 0

    private val directors: MutableMap<Int, Director> = mutableMapOf()

    fun allFilms(): Uni<List<Film>> = films.values.toList().asUni()

    fun getFilm(id: Int): Uni<Film> {
        return films.getValue(id).asUni()
    }

    fun getHeroesByFilm(film: Film): Uni<List<Hero>> {
        return heroes.values.filter { hero -> hero.episodeIds.contains(film.episodeID) }
            .asUni()
    }

    fun addHero(hero: Hero): Uni<Hero> {
        val newHero = hero.copy(id = heroCounter++)
        heroes[newHero.id!!] = newHero
        return newHero.asUni()
    }

    fun deleteHero(id: Int): Uni<Hero> {
        return heroes.remove(id)!!.asUni()
    }

    fun getHeroesBySurname(surname: String): Uni<List<Hero>> {
        return heroes.values.filter { hero -> hero.surname == surname }.asUni()
    }

    fun getDirector(id: Int): Uni<Director> = directors.getValue(id).asUni()

    init {
        val georgeLucas = Director(id = 0, name = "George Lucas")
        directors[georgeLucas.id!!] = georgeLucas

        val aNewHope = Film(
            id = filmCounter++,
            title = "A New Hope",
            releaseDate = LocalDate.of(1977, Month.MAY, 25),
            episodeID = 4,
            directorId = georgeLucas.id,
        )

        val theEmpireStrikesBack = Film(
            id = filmCounter++,
            title = "The Empire Strikes Back",
            releaseDate = LocalDate.of(1980, Month.MAY, 21),
            episodeID = 5,
            directorId = georgeLucas.id,
        )

        val returnOfTheJedi = Film(
            id = filmCounter++,
            title = "Return Of The Jedi",
            releaseDate = LocalDate.of(1983, Month.MAY, 25),
            episodeID = 6,
            directorId = georgeLucas.id,
        )

        films[aNewHope.id!!] = aNewHope
        films[theEmpireStrikesBack.id!!] = theEmpireStrikesBack
        films[returnOfTheJedi.id!!] = returnOfTheJedi

        val luke = Hero(
            id = heroCounter++,
            name = "Luke",
            surname = "Skywalker",
            lightSaber = LightSaber.GREEN,
            darkSide = false,
            episodeIds = listOf(4, 5, 6),
        )

        val leia = Hero(
            id = heroCounter++,
            name = "Leia",
            surname = "Organa",
            darkSide = false,
            lightSaber = LightSaber.BLUE,
            episodeIds = listOf(4, 5, 6),
        )

        val vader = Hero(
            id = heroCounter++,
            name = "Darth",
            surname = "Vader",
            darkSide = true,
            lightSaber = LightSaber.RED,
            episodeIds = listOf(4, 5, 6),
        )

        heroes[luke.id!!] = luke
        heroes[leia.id!!] = leia
        heroes[vader.id!!] = vader
    }
}
