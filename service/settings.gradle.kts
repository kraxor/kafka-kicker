pluginManagement {
    val quarkusPluginId = "io.quarkus"
    val quarkusPluginVersion = "3.0.3.Final"

    plugins {
        id(quarkusPluginId) version quarkusPluginVersion
    }

    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
}
