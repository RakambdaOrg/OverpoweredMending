plugins {
    alias(libs.plugins.loom)
}

dependencies {
    minecraft(libs.minecraft)
//    mappings(loom.officialMojangMappings())
    mappings("net.fabricmc:yarn:1.18.1+build.2:v2")

    modImplementation(libs.bundles.fabric)

    implementation(project(":common"))

    modImplementation(libs.modmenu) {
        exclude(group = "net.fabricmc.fabric-api")
    }
}

tasks {
    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        from("src/main/resources", "../common/src/main/resources")

        filesMatching("fabric.mod.json") {
            expand(project.properties)
        }
    }

    compileJava {
        options.encoding = "UTF-8"
        options.isDeprecation = true
        options.release.set(17)
    }
}

loom {
    val modId: String by project

    mixin {
        getDefaultRefmapName().set("fabric.${modId}.refmap.json")
    }

    runs {
        create("overpoweredMendingClient") {
            client()

            property("fabric.log.level", "debug")
            vmArg("-XX:+ShowCodeDetailsInExceptionMessages")
        }
        create("overpoweredMendingServer") {
            server()

            property("fabric.log.level", "info")
            vmArg("-XX:+ShowCodeDetailsInExceptionMessages")
        }
    }
}