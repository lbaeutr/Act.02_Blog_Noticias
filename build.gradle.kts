plugins {
    kotlin("jvm") version "2.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // JUnit 5 para pruebas unitarias
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")

    // https://mvnrepository.com/artifact/org.mongodb/mongodb-driver-kotlin-sync
    implementation("org.mongodb:mongodb-driver-kotlin-sync:5.3.0")
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.3.0")
    // https://mvnrepository.com/artifact/io.github.cdimascio/dotenv-kotlin
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.2")

    // https://mvnrepository.com/artifact/org.mongodb/bson-kotlin esto sirve para poder usar el Document de mongo
    implementation("org.mongodb:bson-kotlin:5.3.0")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}