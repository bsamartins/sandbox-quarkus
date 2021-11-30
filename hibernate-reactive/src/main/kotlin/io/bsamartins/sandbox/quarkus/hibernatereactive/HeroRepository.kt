package io.bsamartins.sandbox.quarkus.hibernatereactive

import io.quarkus.hibernate.reactive.panache.PanacheRepository
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.coroutines.awaitSuspending
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class HeroRepository : PanacheRepository<Hero> {
    suspend fun findAllSuspending(): List<Hero> = findAll(Sort.empty()).list<Hero>().awaitSuspending()
}
