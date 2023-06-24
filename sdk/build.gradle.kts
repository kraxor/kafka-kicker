plugins {
    kotlin("jvm")
    id("org.openapi.generator") version "6.6.0"
}

group = "ch.sonofabit.kafka.kicker"
version = "1.0.0-SNAPSHOT"

openApiGenerate {
    generatorName.set("kotlin")
    inputSpec.set("$projectDir/openapi/openapi.yaml")
    skipValidateSpec.set(true) // FIXME (upstream) the generated schema is missing some descriptions
}

repositories {
    mavenCentral()
}

dependencies {
//    testImplementation(platform("org.junit:junit-bom:5.9.1"))
//    testImplementation("org.junit.jupiter:junit-jupiter")
//
//    testImplementation("io.kotest:kotest-assertions-core:5.6.2")
//
//    testImplementation("org.testcontainers:junit-jupiter:1.18.1")
}

tasks.test {
    useJUnitPlatform()
}
