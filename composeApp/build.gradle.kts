import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    //kotlin("jvm") version "1.9.22" // or kotlin("multiplatform") or any other kotlin plugin
    kotlin("plugin.serialization") version "1.9.22"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {

        val koin_version="3.4.0"

        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
                implementation("io.insert-koin:koin-core:$koin_version")


            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.androidx.activity.compose)


                implementation("io.insert-koin:koin-android:$koin_version")
//                implementation("com.spotify.android:auth:1.2.5")
//                implementation("com.spotify.android:spotify-player:1.1.0")
            }
        }


    }

    repositories {
        mavenCentral()
        google()
    }
}

android {
    namespace = "org.example.project"
    //compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileSdk = 34

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        //targetSdk = libs.versions.android.targetSdk.get().toInt()
        targetSdk =34
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    repositories {
        mavenCentral()
        google()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)


        implementation("androidx.core:core-ktx:1.12.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.11.0")

        val lifecycle_version ="2.6.2"
        val coroutine_version="1.7.3"
        val koin_version="3.4.0"

        //Lifecycle
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
        implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
        implementation( "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
        implementation ("androidx.activity:activity-ktx:1.8.2")

        //Koin
        implementation ("io.insert-koin:koin-android:$koin_version")
        implementation ("io.insert-koin:koin-androidx-compose:$koin_version")


        //Coroutines
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version")
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutine_version")


        //Compose Navigation
        implementation ("androidx.navigation:navigation-compose:2.7.7")

    }
}
