plugins {
    kotlin("jvm") apply false
    kotlin("plugin.allopen") apply false
    kotlin("plugin.jpa") apply false
    id("io.quarkus") apply false
}

group = "io.bsamartins.sandbox.quarkus"
version = "1.0-SNAPSHOT"

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "io.quarkus")

    repositories {
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        val implementation by configurations
        val testImplementation by configurations
        val quarkusVersion: String by project
        val kotlinVersion: String by project

        implementation(platform("org.jetbrains.kotlin:kotlin-bom:$kotlinVersion"))
        implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:$quarkusVersion"))

        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        implementation("io.quarkus:quarkus-kotlin")
        implementation("io.quarkus:quarkus-arc")

        testImplementation("io.quarkus:quarkus-junit5")
        testImplementation("io.mockk:mockk:1.12.1")
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    configure<org.jetbrains.kotlin.noarg.gradle.NoArgExtension> {
        annotation("javax.persistence.Entity")
    }

    configure<org.jetbrains.kotlin.allopen.gradle.AllOpenExtension> {
        annotation("javax.ws.rs.Path")
        annotation("javax.enterprise.context.ApplicationScoped")
        annotation("javax.persistence.Entity")
        annotation("io.quarkus.test.junit.QuarkusTest")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
        kotlinOptions.javaParameters = true
    }
}
