package io.bsamartins.sandbox.quarkus.hibernatereactive

import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Path("heroes")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
class HeroResource(private val heroRepository: HeroRepository) {
    @GET
    suspend fun get(): List<Hero> {
        return heroRepository.findAllSuspending()
    }
}
