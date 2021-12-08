package io.bsamartins.sandbox.quarkus.neo4j.familytree

import org.neo4j.driver.Driver
import org.neo4j.ogm.drivers.bolt.driver.BoltDriver
import org.neo4j.ogm.session.SessionFactory
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Disposes
import javax.enterprise.inject.Produces

class FamilyTreeOgmConfig {
    @Produces
    @ApplicationScoped
    fun produceSessionFactory(driver: Driver): SessionFactory {
        return SessionFactory(BoltDriver(driver), "io.bsamartins.sandbox.quarkus.neo4j.familytree")
    }

    fun disposeSessionFactory(@Disposes sessionFactory: SessionFactory) {
        sessionFactory.close()
    }
}
