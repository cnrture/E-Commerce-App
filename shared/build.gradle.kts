plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composePlugin)
    alias(libs.plugins.serialization)
    id("dev.icerock.mobile.multiplatform-resources")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
        sourceSets {
            getByName("androidMain").kotlin.srcDirs("build/generated/moko/androidMain/src")
        }
    }

    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export(libs.devIceRockMvvmCore.get())
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
            export(libs.devIceRockResources)
        }
    }

    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(libs.ktorClient)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.ktorClientDarwin)
            }
        }
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.animation)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                //sharedVm
                api(libs.kmmViewmodelCore)

                //di
                api(libs.koinCore)

                //network
                implementation(libs.ktorClientCore)
                implementation(libs.ktorClientJson)
                implementation(libs.ktorClientLogging)
                implementation(libs.ktorClientContentNegotiation)
                implementation(libs.ktorSerializationKotlinxJson)
                implementation(libs.kotlinxSerializationCore)

                //imageloading
                implementation(libs.imageLoader)

                //coroutines
                implementation(libs.kotlinxCoroutinesCore)

                api(libs.precompose)

                api(libs.devIceRockMvvmCompose)
                api(libs.devIceRockMvvmCore)
                api(libs.devIceRockMvvmFlow)
                api(libs.devIceRockMvvmFlowCompose)
                api(libs.devIceRockResources)
                api(libs.devIceRockResourcesCompose)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}


multiplatformResources {
    multiplatformResourcesPackage = "com.canerture.ecommercecm"
}

android {
    namespace = "com.canerture.ecommercecm"
    compileSdk = 34
    defaultConfig {
        minSdk = 23
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}