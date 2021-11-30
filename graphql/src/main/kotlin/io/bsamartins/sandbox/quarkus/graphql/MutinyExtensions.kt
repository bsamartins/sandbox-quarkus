package io.bsamartins.sandbox.quarkus.graphql

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni

fun <T : Any> T.asUni(): Uni<T> = Uni.createFrom().item(this)

fun <T : Any> Iterable<T>.asMulti(): Multi<T> = Multi.createFrom().iterable(this)
