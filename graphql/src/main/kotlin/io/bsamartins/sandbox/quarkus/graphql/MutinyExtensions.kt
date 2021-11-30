package io.bsamartins.sandbox.quarkus.graphql

import io.smallrye.mutiny.Uni

fun <T : Any> T.asUni(): Uni<T> = Uni.createFrom().item(this)
