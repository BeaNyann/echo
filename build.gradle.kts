plugins {
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt")
}

allprojects {
    group = "com.github.beanyann.echo"
    version = extra["echo.version"] as String
}

subprojects {
    repositories {
        mavenCentral()
    }
}

tasks.register<Copy>("copyLib") {
    group = "build"
    description = "Copy the library JAR to the app"
    dependsOn(":echoLib:jar")
    from("echoLib/build/libs")
    into("echoApp/libs")
}