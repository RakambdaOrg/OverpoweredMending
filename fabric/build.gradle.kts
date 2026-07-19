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