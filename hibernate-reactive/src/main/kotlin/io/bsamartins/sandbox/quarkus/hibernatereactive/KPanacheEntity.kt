package io.bsamartins.sandbox.quarkus.hibernatereactive

import io.quarkus.hibernate.reactive.panache.PanacheEntity
import io.smallrye.mutiny.coroutines.awaitSuspending

suspend inline fun <reified T : PanacheEntity> T.persistSuspending(): T {
    return this.persist<T>().awaitSuspending()
}

suspend inline fun <reified T : PanacheEntity> T.persistAndFlushSuspending(): T {
    return this.persistAndFlush<T>().awaitSuspending()
}
