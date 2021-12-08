package io.bsamartins.sandbox.quarkus.neo4j.familytree

import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain
import org.neo4j.ogm.session.SessionFactory
import org.neo4j.ogm.session.query

@QuarkusMain
class FamilyTreeApplication(private val sessionFactory: SessionFactory) : QuarkusApplication {
    private val session = sessionFactory.openSession()
    override fun run(vararg args: String?): Int {
        loadData()
        return 0
    }

    private fun loadData() {
        val l1_jorge = person(name = "Jorge")
        val l1_margarida = person(name = "Margarida")

        setMarried(l1_jorge, l1_margarida)

        val l1_1_pedro = person(name = "Pedro")
        val l1_1_emilia = person(name = "Emilia")
        val l1_1_joao = person(name = "Joao")
        val l1_1_francisco = person(name = "Francisco")
        val l1_1_margarida = person(name = "Margarida")
        val l1_1_antonio = person(name = "Antonio")

        setParents(
            l1_jorge, l1_margarida,
            setOf(l1_1_pedro, l1_1_emilia, l1_1_joao, l1_1_francisco, l1_1_margarida, l1_1_antonio)
        )

        val l2_1_pedro = person(name = "Pedro")

        val l1_1_1_bernardo = person(name = "Bernardo")
        val l1_1_1_barbara = person(name = "Barbara")
        val l1_1_1_pedro = person(name = "Pedro")

        setParents(
            l2_1_pedro, l1_1_emilia,
            setOf(l1_1_1_bernardo, l1_1_1_barbara, l1_1_1_pedro)
        )

        val l2_1_catinha = person(name = "Catinha")

        val l1_1_1_guilherme = person(name = "Guilherme")
        val l1_1_1_filipa = person(name = "Filipa")

        setParents(l1_1_pedro, l2_1_catinha, setOf(l1_1_1_guilherme, l1_1_1_filipa))

        session.save(l1_jorge)
        session.save(l1_margarida)

        session.save(l1_1_pedro)
        session.save(l1_1_emilia)
        session.save(l1_1_joao)
        session.save(l1_1_francisco)
        session.save(l1_1_margarida)
        session.save(l1_1_antonio)

        session.save(l1_1_1_bernardo)
        session.save(l1_1_1_barbara)
        session.save(l1_1_1_pedro)

//        findAll()
        findSiblings(l1_1_antonio)
        findNephews(l1_1_antonio)
    }

    private fun setMarried(p1: Person, p2: Person) {
        p1.marriedTo = p2
        p2.marriedTo = p1
    }

    private fun setParents(father: Person, mother: Person, children: Set<Person>) {
        father.parentOf.addAll(children)
        mother.parentOf.addAll(children)
        children.forEach {
            it.father = father
            it.mother = mother
        }
    }

    /* Queries */

    private fun findAll() {
        val result = session.query<Person>(
            """
                MATCH (p: Person)
                RETURN p
            """.trimIndent()
        )
        result.forEach { println(it) }
    }

    private fun findSiblings(p: Person) {
        val query = """
            MATCH (p: Person)-[:FATHER]->()-[:PARENT_OF]->(child)
            WHERE ID(p) = ${'$'}id AND ID(child) <> ${'$'}id 
            RETURN child
        """.trimIndent()
        println(query)
        val result = session.query(Person::class.java, query, mapOf("id" to p.id))
        println("Siblings of ${p.name}")
        result.forEach { println(" - ${it.name}") }
    }

    private fun findNephews(p: Person) {
        val query = """
            MATCH (p: Person)-[:FATHER]->()-[:PARENT_OF]->(child)-[:PARENT_OF]->(nephew)
            WHERE ID(p) = ${'$'}id AND ID(child) <> ${'$'}id 
            RETURN nephew
        """.trimIndent()
        println(query)
        val result = session.query(Person::class.java, query, mapOf("id" to p.id))
        println("Nephews of ${p.name}")
        result.forEach { println(" - ${it.name}") }
    }
}
