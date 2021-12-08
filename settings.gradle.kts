pluginManagement {
    val quarkusVersion: String by settings
    val kotlinVersion: String by settings
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.allopen") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion
        id("io.quarkus") version quarkusVersion
    }
}
rootProject.name = "sandbox-quarkus"

include(
    "hibernate-reactive",
    "graphql",
    "test-metrics",
    "module:dependency",
    "module:main",
    "neo4j"
)
