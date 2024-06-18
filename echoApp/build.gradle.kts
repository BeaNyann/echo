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
    flatDir {
        dirs("libs")
    }
}

dependencies {
    implementation(fileTree("libs") { include("*.jar") })
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