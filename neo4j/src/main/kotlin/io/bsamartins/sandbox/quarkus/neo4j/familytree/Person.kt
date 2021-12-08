package io.bsamartins.sandbox.quarkus.neo4j.familytree

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.util.*

@NodeEntity
class Person {
    @Id
    var id: Int? = null
    var name: String? = null

    @Relationship("PARENT_OF")
    var parentOf: MutableSet<Person> = mutableSetOf()

    @Relationship("MARRIED_TO")
    var marriedTo: Person? = null
    override fun toString(): String {
        return "Person(id=$id, name=$name, parentOf=(...), marriedTo=(...))"
    }

    @Relationship("FATHER")
    var father: Person? = null

    @Relationship("MOTHER")
    var mother: Person? = null
}

private var personSequence: Int = 0

fun person(name: String): Person {
    return Person().apply {
        this.id = personSequence++
        this.name = name
    }
}
