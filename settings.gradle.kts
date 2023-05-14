/*
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10" apply false

    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

rootProject.name = "kafka-kicker"
include("cli")
include("lib")
