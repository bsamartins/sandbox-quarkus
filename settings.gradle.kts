pluginManagement {
    val quarkusVersion: String by settings
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("io.quarkus") version quarkusVersion
    }
}
rootProject.name = "sandbox-quarkus"
