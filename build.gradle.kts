// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version = "1.4.0"
    repositories {
        google()
        jcenter()
        maven(
                "https://plugins.gradle.org/m2/"
        )
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath(kotlin("gradle-plugin", version = "$kotlin_version"))
        classpath("org.jlleitschuh.gradle:ktlint-gradle:9.3.0")
        classpath("com.google.gms:google-services:4.3.3")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.0"
    id( "org.jlleitschuh.gradle.ktlint") version "9.3.0"
}


subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint") // Version should be inherited from parent

    // Optionally configure plugin
    ktlint {
        debug.set(true)
    }
}
allprojects {
    repositories {
        google()
        jcenter()
    }
}
