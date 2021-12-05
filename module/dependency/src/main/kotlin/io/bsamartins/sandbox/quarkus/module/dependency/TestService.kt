package io.bsamartins.sandbox.quarkus.module.dependency

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class TestService {
    fun hello() {
        println("Hello World from service")
    }
}
