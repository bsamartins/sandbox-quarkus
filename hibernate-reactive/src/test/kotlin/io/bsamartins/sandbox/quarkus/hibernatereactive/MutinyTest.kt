package io.bsamartins.sandbox.quarkus.hibernatereactive

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.groups.MultiOnItem
import org.junit.jupiter.api.Test
import java.time.Duration
import kotlin.random.Random

class MutinyTest {

    @Test
    fun testOrder() {
        val range = 1..20
        val result = Multi.createFrom().iterable(range)
            .onItem().invoke { i -> println("Item $i") }
            .onItem().delayItBy { Duration.ofMillis(Random.nextLong(1, 1000)) }
            .collect().asList()
            .await().indefinitely()

        println(result)
    }

    private fun <T> MultiOnItem<T>.delayItBy(supplier: (T) -> Duration): Multi<T> {
        return call { i ->
            val duration = supplier(i)
            println("Waiting $duration")
            Uni.createFrom().nullItem<Int>()
                .onItem().delayIt().by(duration)
        }
    }
}
