import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    kotlin("jvm")
    id("org.openapi.generator") version "6.6.0"
}

group = "ch.sonofabit.kafka.kicker"
version = "1.0.0-SNAPSHOT"


fun TaskContainer.registerClientSdk(name: String, config: GenerateTask.() -> Unit) =
    register("generateSdkClient-$name", GenerateTask::class.java) {
        group = "SDK clients"
        generatorName.set(name)
        inputSpec.set("$projectDir/openapi/openapi.yaml")
        outputDir.set("$projectDir/clients/$name")
        skipValidateSpec.set(true) // FIXME (upstream) the generated schema is missing some descriptions
        config()
    }

tasks.registerClientSdk("kotlin") {}
tasks.registerClientSdk("typescript-fetch") {}
tasks.registerClientSdk("typescript-axios") {}

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
