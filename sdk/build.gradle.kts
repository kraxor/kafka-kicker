import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    kotlin("jvm")
    id("org.openapi.generator") version "6.6.0"
}

group = "ch.sonofabit.kafka.kicker"
version = "1.0.0-SNAPSHOT"


fun TaskContainer.registerClientSdk(name: String, config: GenerateTask.() -> Unit) =
    register("generate_$name", GenerateTask::class.java) {
        group = "client sdk generators"
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
