buildscript {
    dependencies {
        classpath(libs.devIceRockResourcesGeneratorPlugin)
    }
}
plugins {
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.composePlugin).apply(false)
}
