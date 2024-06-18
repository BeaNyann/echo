import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("io.gitlab.arturbosch.detekt")
    kotlin("jvm")
    id("org.jetbrains.dokka")
}

val dokkaVersion = extra["dokka.version"] as String

dependencies {
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:$dokkaVersion")
}

val dokkaHtml by tasks.getting(DokkaTask::class)

kotlin {
    jvmToolchain(17)
}

tasks.jar {
    manifest {
        attributes["Implementation-Title"] = "echoLib"
        attributes["Implementation-Version"] = project.version
    }
    from(sourceSets.main.get().output)
}

val dokkaJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(dokkaHtml.outputDirectory)
}

val sourcesJar by tasks.registering(Jar::class) {
    dependsOn("jar")
}

tasks.register("releaseLocal") {
    dependsOn("dokkaJar", "sourcesJar")
}