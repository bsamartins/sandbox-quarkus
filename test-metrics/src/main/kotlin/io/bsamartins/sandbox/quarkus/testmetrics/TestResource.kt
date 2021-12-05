package io.bsamartins.sandbox.quarkus.testmetrics

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tags
import org.slf4j.LoggerFactory
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders

@Path("/api/test")
class TestResource(private val meterRegistry: MeterRegistry) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @GET
    @Path("client-metrics")
    fun hello(@Context headers: HttpHeaders): String {
        logger.info("Headers: {}", headers.requestHeaders)
        meterRegistry.counter("io.bsamartins.request", Tags.of("client", headers.getHeaderString("User-Agent"))).increment()
        return "Hello World"
    }
}
