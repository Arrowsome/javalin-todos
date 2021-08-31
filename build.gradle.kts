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
}