plugins {
    kotlin("jvm") version "1.5.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.javalin:javalin:3.13.11")
    implementation("org.slf4j:slf4j-simple:1.7.32")
    implementation("mysql:mysql-connector-java:8.0.26")
    implementation("com.fasterxml.jackson.core:jackson-core:2.12.5")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.5")
    implementation("com.auth0:java-jwt:3.18.1")
    implementation("org.postgresql:postgresql:42.2.23")
}

//tasks.create("stage") {
//    dependsOn("installDist")
//}