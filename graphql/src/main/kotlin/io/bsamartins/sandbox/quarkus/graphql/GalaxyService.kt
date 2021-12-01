package io.bsamartins.sandbox.quarkus.graphql

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

    fun allFilms(): List<Film> = films.values.toList()

    fun getFilm(id: Int): Film {
        return films.getValue(id)
    }

    fun getHeroesByIds(heroIds: Set<Int>): List<Hero> {
        return heroes.values.filter { hero -> heroIds.contains(hero.id) }
    }

    fun addHero(hero: Hero): Hero {
        val newHero = hero.copy(id = heroCounter++)
        heroes[newHero.id!!] = newHero
        return newHero
    }

    fun deleteHero(id: Int): Hero {
        return heroes.remove(id)!!
    }

    fun getHeroesBySurname(surname: String): List<Hero> {
        return heroes.values.filter { hero -> hero.surname == surname }
    }

    fun getDirector(id: Int): Director = directors.getValue(id)

    init {
        val georgeLucas = Director(id = 0, name = "George Lucas")
        directors[georgeLucas.id!!] = georgeLucas

        val luke = Hero(
            id = heroCounter++,
            name = "Luke",
            surname = "Skywalker",
            lightSaber = LightSaber.GREEN,
            darkSide = false,
        )

        val leia = Hero(
            id = heroCounter++,
            name = "Leia",
            surname = "Organa",
            darkSide = false,
            lightSaber = LightSaber.BLUE,
        )

        val vader = Hero(
            id = heroCounter++,
            name = "Darth",
            surname = "Vader",
            darkSide = true,
            lightSaber = LightSaber.RED,
        )

        heroes[luke.id!!] = luke
        heroes[leia.id!!] = leia
        heroes[vader.id!!] = vader

        val aNewHope = Film(
            id = filmCounter++,
            title = "A New Hope",
            releaseDate = LocalDate.of(1977, Month.MAY, 25),
            directorId = georgeLucas.id,
            heroIds = setOf(luke.id, leia.id, vader.id)
        )

        val theEmpireStrikesBack = Film(
            id = filmCounter++,
            title = "The Empire Strikes Back",
            releaseDate = LocalDate.of(1980, Month.MAY, 21),
            directorId = georgeLucas.id,
            heroIds = setOf(luke.id, leia.id, vader.id)
        )

        val returnOfTheJedi = Film(
            id = filmCounter++,
            title = "Return Of The Jedi",
            releaseDate = LocalDate.of(1983, Month.MAY, 25),
            directorId = georgeLucas.id,
            heroIds = setOf(luke.id, leia.id, vader.id)
        )

        val rogueSquadron = Film(
            id = filmCounter++,
            title = "Rogue Squadron",
        )

        films[aNewHope.id!!] = aNewHope
        films[theEmpireStrikesBack.id!!] = theEmpireStrikesBack
        films[returnOfTheJedi.id!!] = returnOfTheJedi
        films[rogueSquadron.id!!] = rogueSquadron
    }
}
