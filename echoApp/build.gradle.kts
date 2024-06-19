plugins {
    id("io.gitlab.arturbosch.detekt")
    kotlin("jvm")
    application
}

application {
    mainClass.set("com.github.beanyann.MainKt")
}

kotlin {
    jvmToolchain(17)
}

repositories {
    maven {
        url = uri("https://plugins.gradle.org/m2/")
        credentials {
            username = System.getenv("GITHUB_USER")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation("com.github.beanyann:echoLib:1.0.0")
}

tasks.register<Jar>("fatJar") {
    archiveClassifier.set("fat")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
        attributes["Implementation-Title"] = "Echo"
        attributes["Implementation-Version"] = project.version
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath
            .get()
            .filter { it.name.endsWith("jar") }
            .map { project.zipTree(it) }
    })
}