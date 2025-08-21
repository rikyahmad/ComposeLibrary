import java.util.Properties

val localProps = Properties().apply {
    val f = rootDir.resolve("local.properties")
    if (f.exists()) f.inputStream().use { load(it) }
}

val gprUser: String = localProps.getProperty("gpr.user") ?: System.getenv("GITHUB_USERNAME")
val gprKey: String = localProps.getProperty("gpr.key") ?: System.getenv("GITHUB_TOKEN")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io") {
            if (gprKey.isNotBlank()) {
                credentials {
                    username = gprUser
                    password = gprKey
                }
            }
        }
        maven(url = "https://maven.pkg.github.com/rikyahmad/ComposeLibrary") {
            if (gprKey.isNotBlank()) {
                credentials {
                    username = gprUser
                    password = gprKey
                }
            }
        }
    }
}

rootProject.name = "ComposeLibrary"
include(":app")
include(":mylibrary")
