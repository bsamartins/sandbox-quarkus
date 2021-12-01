package io.bsamartins.sandbox.quarkus.graphql

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.quarkus.test.junit.QuarkusMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.specification.RequestSpecification
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

@QuarkusTest
internal class FilmResourceTest {

    private var galaxyService: GalaxyService = mockk()

    @BeforeEach
    fun setUp() {
        QuarkusMock.installMockForType(galaxyService, GalaxyService::class.java)
    }

    @Nested
    inner class AllFilms {
        @Test
        fun `should be successful`() {
            every { galaxyService.allFilms() } returns listOf(
                Film(
                    id = 1,
                    title = "SW Christmas Special",
                    directorId = 10,
                    releaseDate = LocalDate.of(2020, 12, 31),
                    heroIds = setOf(100),
                )
            )
            every { galaxyService.getDirector(any()) } returns Director(id = 10, name = "George Lucas")
            every { galaxyService.getHeroesByIds(any()) } returns listOf(
                Hero(
                    id = 100,
                    name = "Luke",
                    surname = "Skywalker",
                    darkSide = true,
                    lightSaber = LightSaber.GREEN,
                )
            )

            val query = GraphQLQuery(
                query = """
                { 
                    allFilms {
                        id
                        title
                        releaseDate      
                        director {
                            id
                            name
                        }
                        heroes {
                            id
                            name
                            surname
                            darkSide
                            lightSaber
                        }
                    } 
                }
                """.trimIndent()
            )

            Given { graphql(query) }
                .When { post("/graphql") }
                .Then {
                    statusCode(200)
                        .body("data.allFilms[0].id", equalTo(1))
                        .body("data.allFilms[0].title", equalTo("SW Christmas Special"))
                        .body("data.allFilms[0].releaseDate", equalTo("2020-12-31"))
                        .body("data.allFilms[0].director.id", equalTo(10))
                        .body("data.allFilms[0].director.name", equalTo("George Lucas"))
                        .body("data.allFilms[0].heroes[0].id", equalTo(100))
                        .body("data.allFilms[0].heroes[0].name", equalTo("Luke"))
                        .body("data.allFilms[0].heroes[0].surname", equalTo("Skywalker"))
                        .body("data.allFilms[0].heroes[0].darkSide", equalTo("Skywalker"))
                        .body("data.allFilms[0].heroes[0].lightSaber", equalTo("Skywalker"))
                }

            verify { galaxyService.allFilms() }
            verify { galaxyService.getDirector(10) }
            verify { galaxyService.getHeroesByIds(setOf(100)) }
        }
    }
}

private fun RequestSpecification.graphql(query: GraphQLQuery): RequestSpecification {
    return body(query.toString()).contentType(ContentType.JSON)
}
