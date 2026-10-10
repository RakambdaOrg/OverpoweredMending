plugins {
    alias(libs.plugins.loom)
}

dependencies {
    minecraft(libs.minecraft)

    implementation(libs.bundles.fabric) {
        exclude(module = "fabric-api-deprecated")
    }

    implementation(project(":common"))

    api(libs.modmenu)
}

tasks {
    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        from("src/main/resources", "../common/src/main/resources")

        filesMatching("fabric.mod.json") {
            expand(project.properties)
        }
    }
}

loom {
    val modId: String by project

    splitEnvironmentSourceSets()

    mods {
        create(modId) {
            sourceSet(sourceSets["main"])
            sourceSet(sourceSets["client"])
        }
    }

    runs {
        create("OMFabricClient") {
            client()
            runDir("run/client")

            property("fabric.log.level", "info")
            vmArg("-XX:+ShowCodeDetailsInExceptionMessages")
            programArgs("--uuid=f13e3278-dfb8-4948-98cc-7701b5c62e8c", "--username=Dev")
        }
        create("OMFabricServer") {
            server()
            runDir("run/server")

            property("fabric.log.level", "info")
            vmArg("-XX:+ShowCodeDetailsInExceptionMessages")
        }
    }
}
fabricApi {
    configureTests {
        createSourceSet = true
        modId = "overpoweredmending_gametest"
        enableClientGameTests = false
    }
}

sourceSets.named("gametest") {
    // Loader independent scenarios, shared with Forge and NeoForge
    java.srcDir("../gametest/src/main/java")
    resources.srcDir("../gametest/src/main/resources")
}

apply(from = rootProject.file("gametest/gametest.gradle"))

// Game tests are run on every loader through the root "gameTest" task rather than as part of "check"
tasks.named("check") {
    setDependsOn(dependsOn.filterNot { it == "runGameTest" })
}

loom.runs.named("gameTest") {
    property("fabric-api.gametest.report-file", layout.buildDirectory.file("gametest/junit.xml").get().asFile.absolutePath)
    vmArg("-XX:+ShowCodeDetailsInExceptionMessages")
}
