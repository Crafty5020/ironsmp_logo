
plugins {
    `java`
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.18"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    maven {
    name = "papermc"
    url = uri("https://repo.papermc.io/repository/maven-public/")
  }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")
    paperweight.paperDevBundle("1.21.8-R0.1-SNAPSHOT")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION
