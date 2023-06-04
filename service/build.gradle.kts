plugins {
    id("org.jetbrains.kotlin.jvm")
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("$quarkusPlatformGroupId:$quarkusPlatformArtifactId:$quarkusPlatformVersion"))
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-config-yaml")

    implementation("io.quarkus:quarkus-smallrye-reactive-messaging")
    implementation("io.quarkus:quarkus-smallrye-reactive-messaging-kafka")

    implementation("io.quarkus:quarkus-resteasy-reactive")
    implementation("io.quarkus:quarkus-resteasy-reactive-kotlin-serialization")
    implementation("io.quarkus:quarkus-hibernate-reactive-rest-data-panache")
    implementation("io.quarkus:quarkus-hibernate-reactive-panache-kotlin")
//    implementation("io.quarkus:quarkus-hibernate-reactive")
    implementation("io.quarkus:quarkus-reactive-pg-client")

    implementation(enforcedPlatform("$quarkusPlatformGroupId:quarkus-amazon-services-bom:$quarkusPlatformVersion"))
    implementation("io.quarkiverse.amazonservices:quarkus-amazon-ssm")
    implementation("io.quarkiverse.amazonservices:quarkus-amazon-secretsmanager")

    implementation(project(":lib"))

    testImplementation("io.rest-assured:kotlin-extensions")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("io.kotest:kotest-assertions-core:5.6.2")
    testImplementation("io.quarkus:quarkus-test-vertx")
}

group = "ch.sonofabit.kafka-kicker.backend.service"
version = "1.0-SNAPSHOT"

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
    useJUnitPlatform()
}

//annotationProcessor("io.quarkus:quarkus-panache-common")
