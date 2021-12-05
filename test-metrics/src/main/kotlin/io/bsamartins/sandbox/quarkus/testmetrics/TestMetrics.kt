package io.bsamartins.sandbox.quarkus.testmetrics

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tags
import io.quarkus.runtime.Startup
import org.slf4j.LoggerFactory
import java.time.Duration
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.enterprise.context.ApplicationScoped

@Startup
@ApplicationScoped
class TestMetrics(
    private val meterRegistry: MeterRegistry,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private lateinit var thread: Thread

    @PostConstruct
    fun start() {
        val counter1 = meterRegistry.counter("io.bsamartins.test-counter", Tags.of("id", "1"))
        val counter2 = meterRegistry.counter("io.bsamartins.test-counter", Tags.of("id", "2"))
        val maxCount = 10
        var count = 1
        val sleep = Duration.ofSeconds(10)
        thread = Thread {
            var running = true
            while (running && !Thread.interrupted()) {
                counter1.increment(count.toDouble())
                counter2.increment()
                logger.info("Counter 1 {} -> {}", count, counter1.count())
                logger.info("Counter 2 {} -> {}", count, counter2.count())
                count++
                if (count <= maxCount) {
                    Thread.sleep(sleep.toMillis())
                } else {
                    running = false
                }
            }
            logger.info("Exiting thread")
        }
        logger.info("Starting thread")
        thread.start()
    }

    @PreDestroy
    fun stop() {
        if (::thread.isInitialized) {
            thread.interrupt()
        }
    }
}
