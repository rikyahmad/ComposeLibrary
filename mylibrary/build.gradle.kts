plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

group = "com.github.rikyahmad"

android {
    namespace = "com.staygrateful.mylibrary"
    compileSdk = 36

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

// Publication for JitPack
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                artifactId = "mylibrary"      // ← artifact name for external consume
                pom {
                    name.set("ComposeLibrary")
                    description.set("Reusable components for Compose")
                    url.set("https://github.com/rikyahmad/ComposeLibrary")
                    licenses { license { name.set("Apache-2.0") } }
                    developers { developer { id.set("rikyahmad") } }
                    scm { url.set("https://github.com/rikyahmad/ComposeLibrary.git") }
                }
            }
        }
    }
}