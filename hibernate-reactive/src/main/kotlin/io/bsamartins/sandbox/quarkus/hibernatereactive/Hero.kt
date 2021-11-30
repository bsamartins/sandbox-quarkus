package io.bsamartins.sandbox.quarkus.hibernatereactive

import io.quarkus.hibernate.reactive.panache.PanacheEntity
import javax.persistence.Cacheable
import javax.persistence.Entity

@Entity
@Cacheable
data class Hero(
    val name: String,
    val firstName: String? = null,
    val lastName: String? = null,
) : PanacheEntity()
