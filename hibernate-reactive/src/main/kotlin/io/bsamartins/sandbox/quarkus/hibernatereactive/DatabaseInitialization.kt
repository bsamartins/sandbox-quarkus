package io.bsamartins.sandbox.quarkus.hibernatereactive

import io.quarkus.runtime.Startup
import kotlinx.coroutines.runBlocking
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped

@Startup
@ApplicationScoped
class DatabaseInitialization {

    @PostConstruct
    fun initialize(): Unit = runBlocking {
        Hero(name = "Spider-Man", firstName = "Peter", lastName = "Parker").persistAndFlushSuspending()
        Hero(name = "Superman", firstName = "Clark", lastName = "Kent").persistAndFlushSuspending()
        Hero(name = "Professor X", firstName = "Charles", lastName = "Xavier").persistAndFlushSuspending()
    }
}
