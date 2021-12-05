package io.bsamartins.sandbox.quarkus.module.main

import io.bsamartins.sandbox.quarkus.module.dependency.TestService
import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain

@QuarkusMain
class Application(private val testService: TestService) : QuarkusApplication {
    override fun run(vararg args: String?): Int {
        testService.hello()
        return 0
    }
}
