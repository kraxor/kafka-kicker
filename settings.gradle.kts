/*
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10" apply false
    kotlin("plugin.allopen") version "1.8.10" apply false


            // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

rootProject.name = "kafka-kicker"

include("lib")

include("cli")

include("service")
apply(from = "service/settings.gradle.kts")