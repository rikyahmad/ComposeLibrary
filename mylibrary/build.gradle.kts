import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

group = providers.gradleProperty("POM_GROUP").get()
version = providers.gradleProperty("VERSION_NAME").get()

android {
    namespace = "com.staygrateful.mylibrary"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures { buildConfig = false }
    resourcePrefix = "mylib_"

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// load local.properties (untuk kredensial GPR)
val lp = Properties().apply {
    val f = rootProject.file("local.properties")
    if (f.exists()) f.inputStream().use { load(it) }
}
val gprUser = lp.getProperty("gpr.user") ?: System.getenv("GITHUB_USERNAME") ?: "github"
val gprKey  = lp.getProperty("gpr.key")  ?: System.getenv("GITHUB_TOKEN") ?: ""

afterEvaluate {
    publishing {
        publications {
            // pakai named kalau sudah ada “release”; kalau belum ada, boleh create<...>("release")
            create<MavenPublication>("release") {
                from(components["release"])
                artifactId = "composelibrary" // ← lowercase, aman
                // optional: pom info
                pom {
                    name.set("composelibrary")
                    packaging = "aar"
                }
            }
        }
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/rikyahmad/ComposeLibrary")
                credentials {
                    // ambil dari local.properties
                    username = gprUser
                    password = gprKey
                }
            }
        }
    }
}
