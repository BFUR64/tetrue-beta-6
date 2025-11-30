plugins {
    java
    application
    eclipse
    id("com.gradleup.shadow") version "9.2.2"
}

group = "com.teic"
version = "0.0.3"

repositories { mavenCentral() }

dependencies {
    implementation(libs.guava)
    implementation("com.googlecode.lanterna:lanterna:3.1.3")
    implementation("com.fasterxml.jackson.core:jackson-core:2.19.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.2")
}

java { toolchain { languageVersion.set(JavaLanguageVersion.of(21)) } }

application { mainClass.set("com.teic.trueris.App") }

tasks.withType<Test>().configureEach { isEnabled = false }

tasks.named("shadowJar") {
    // Set the classifier for the JAR name
    this as com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
    archiveClassifier.set("all")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "com.teic.trueris.App"
    }
}
