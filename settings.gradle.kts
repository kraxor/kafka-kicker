/*
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    val kotlinVersion = "1.8.10"
    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.allopen") version kotlinVersion apply false
    
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

rootProject.name = "kafka-kicker"

include("lib", "cli", "sdk", "ui")

include("service")
apply(from = "service/settings.gradle.kts")
