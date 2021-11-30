package io.bsamartins.sandbox.quarkus.graphql

import io.smallrye.mutiny.Uni
import java.time.LocalDate
import java.time.Month
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class GalaxyService {
    private val heroes: MutableList<Hero> = mutableListOf()
    private val films: MutableList<Film> = mutableListOf()

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

    init {
        val aNewHope = Film()
        aNewHope.title = "A New Hope"
        aNewHope.releaseDate = LocalDate.of(1977, Month.MAY, 25)
        aNewHope.episodeID = 4
        aNewHope.director = "George Lucas"

        val theEmpireStrikesBack = Film()
        theEmpireStrikesBack.title = "The Empire Strikes Back"
        theEmpireStrikesBack.releaseDate = LocalDate.of(1980, Month.MAY, 21)
        theEmpireStrikesBack.episodeID = 5
        theEmpireStrikesBack.director = "George Lucas"

        val returnOfTheJedi = Film()
        returnOfTheJedi.title = "Return Of The Jedi"
        returnOfTheJedi.releaseDate = LocalDate.of(1983, Month.MAY, 25)
        returnOfTheJedi.episodeID = 6
        returnOfTheJedi.director = "George Lucas"

        films.add(aNewHope)
        films.add(theEmpireStrikesBack)
        films.add(returnOfTheJedi)

        val luke = Hero()
        luke.name = "Luke"
        luke.surname = "Skywalker"
        luke.height = 1.7
        luke.mass = 73
        luke.lightSaber = LightSaber.GREEN
        luke.darkSide = false
        luke.episodeIds = listOf(4, 5, 6)

        val leia = Hero()
        leia.name = "Leia"
        leia.surname = "Organa"
        leia.height = 1.5
        leia.mass = 51
        leia.darkSide = false
        leia.episodeIds = listOf(4, 5, 6)

        val vader = Hero()
        vader.name = "Darth"
        vader.surname = "Vader"
        vader.height = 1.9
        vader.mass = 89
        vader.darkSide = true
        vader.lightSaber = LightSaber.RED
        vader.episodeIds = listOf(4, 5, 6)

        heroes.add(luke)
        heroes.add(leia)
        heroes.add(vader)
    }
}
