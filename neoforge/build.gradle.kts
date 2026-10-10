plugins {
    id("java-library")
    alias(libs.plugins.neoforge)
}

sourceSets {
    main {
        resources.srcDir("src/generated/resources")
    }
    create("gametest") {
        // Loader independent scenarios, shared with Fabric and Forge
        java.srcDir("../gametest/src/main/java")
        resources.srcDir("../gametest/src/main/resources")
        compileClasspath += main.get().output
        runtimeClasspath += main.get().output
    }
}

apply(from = rootProject.file("gametest/gametest.gradle"))

configurations.named("gametestImplementation") {
    extendsFrom(configurations.implementation.get())
}

neoForge {
    val modId: String by project

    version = libs.versions.neoforgeVersion.get()
    accessTransformers.from(project.files("src/main/resources/META-INF/neoforge.accesstransformer.cfg"))

    addModdingDependenciesTo(sourceSets["gametest"])

    mods {
        create(modId) {
            sourceSet(sourceSets.main.get())
        }
        create("overpoweredmending_gametest") {
            sourceSet(sourceSets["gametest"])
        }
    }

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
            loadedMods = setOf(mods[modId])
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        register("server") {
            server()
            ideName = "runOMNeoForgeServer"
            gameDirectory = project.file("./run/server")
            programArgument("--nogui")
            loadedMods = setOf(mods[modId])
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        register("gameTestServer") {
            type = "gameTestServer"
            ideName = "runFTNeoForgeTestServer"
            gameDirectory = project.file("./run/test")
            sourceSet = sourceSets["gametest"]
            loadedMods = setOf(mods[modId], mods["overpoweredmending_gametest"])
        }
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
            expand(project.properties + mapOf("minecraftVersion" to libs.versions.minecraftVersion.get()))
        }
    }
}