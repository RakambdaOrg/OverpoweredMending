plugins {
    id("java-library")
    alias(libs.plugins.neoforge)
}

neoForge {
    val modId: String by project

    version = libs.versions.neoforgeVersion.get()
    accessTransformers.from(project.files("src/main/resources/META-INF/neoforge.accesstransformer.cfg"))

    runs {
        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            systemProperty("forge.logging.console.level", "error")
            logLevel = org.slf4j.event.Level.DEBUG
        }

        register("client") {
            client()
            ideName = "runOMNeoForgeClient"
            gameDirectory = project.file("./run/client")
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        register("server") {
            server()
            ideName = "runOMNeoForgeServer"
            gameDirectory = project.file("./run/server")
            programArgument("--nogui")
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        register("gameTestServer") {
            type = "gameTestServer"
            ideName = "runFTNeoForgeTestServer"
            gameDirectory = project.file("./run/test")
            systemProperty("forge.enabledGameTestNamespaces", modId)
        }
    }

    mods {
        create(modId) {
            sourceSet(sourceSets.main.get())
        }
    }
}

sourceSets {
    main {
        resources.srcDir("src/generated/resources")
    }
}

val localRuntime: Configuration by configurations.creating
configurations.runtimeClasspath {
    extendsFrom(localRuntime)
}

dependencies {
    implementation(project(":common"))
}

tasks {
    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        from("src/main/resources", "../common/src/main/resources")

        filesMatching("META-INF/neoforge.mods.toml") {
            expand(project.properties + mapOf<String, String>("minecraftVersion" to libs.versions.minecraftVersion.get()))
        }
    }
}