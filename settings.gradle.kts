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
        maven("https://jitpack.io") {
            credentials {
                username = System.getenv("GITHUB_USER") ?: providers.gradleProperty("github.user").orNull
                password = System.getenv("GITHUB_TOKEN") ?: providers.gradleProperty("github.token").orNull
            }
        }
    }
}

rootProject.name = "ComposeLibrary"
include(":app")
include(":mylibrary")
