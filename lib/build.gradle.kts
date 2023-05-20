plugins {
    id("org.jetbrains.kotlin.jvm")
    kotlin("kapt")
    kotlin("plugin.allopen")
}

group = "ch.sonofabit.kafka.kicker"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("javax.inject:javax.inject:1")

    implementation("org.apache.kafka:kafka-clients:3.2.3")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("io.kotest:kotest-assertions-core:5.6.2")

    testImplementation("org.testcontainers:testcontainers:1.18.1")
    testImplementation("org.testcontainers:kafka:1.18.1")
    testImplementation("org.testcontainers:junit-jupiter:1.18.1")
}

allOpen {
    annotation("javax.inject.*")
}

tasks.test {
    useJUnitPlatform()
}
