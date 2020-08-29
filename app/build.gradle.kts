import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jlleitschuh.gradle.ktlint.KtlintCheckTask

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("com.google.gms.google-services")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        applicationId = "io.betterapps.graysky"
        minSdkVersion(23)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("release"){
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    // Enables data binding.
    buildFeatures.dataBinding = true
}

val ktlint by configurations.creating

dependencies {

    //implementation(fileTree(dir = "libs", include: listOf("*.jar")))
    implementation(kotlin("stdlib", "_"))
    implementation("androidx.core:core-ktx:_")
    implementation("androidx.appcompat:appcompat:_")
    implementation("androidx.constraintlayout:constraintlayout:_")
    implementation("androidx.lifecycle:lifecycle-extensions:_")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:_")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:_")
    implementation("androidx.recyclerview:recyclerview:_")
    // For control over item selection of both touch and mouse driven selection
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0-rc01")

    // network
    implementation("com.squareup.retrofit2:retrofit:_")
    implementation("com.squareup.okhttp3:okhttp:_")
    implementation("com.squareup.retrofit2:converter-gson:_")
    implementation("com.google.code.gson:gson:_")

    implementation("com.squareup.picasso:picasso:_")

    // Koin for Android
    implementation("org.koin:koin-android:_")
    // or Koin for Lifecycle scoping
    implementation("org.koin:koin-android-scope:_")
    // or Koin for Android Architecture ViewModel
    implementation("org.koin:koin-android-viewmodel:_")
    // Koin AndroidX Experimental features
//    implementation("org.koin:koin-androidx-ext:$koin_version")

    // log
    implementation("com.jakewharton.timber:timber:_")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:_")

    implementation("com.google.firebase:firebase-analytics:_")

    implementation("junit:junit:_")

    testImplementation("junit:junit:_")
    testImplementation("org.koin:koin-test:_")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:_")

    // testImplementation(for pure JVM unit tests)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:_")
    testImplementation("androidx.arch.core:core-testing:_")

    androidTestImplementation("androidx.test.ext:junit:_")
    androidTestImplementation("androidx.test.espresso:espresso-core:_")

    ktlint("com.pinterest:ktlint:0.38.1")
}

tasks.withType<KtlintCheckTask> {
    reporterOutputDir.set(project.layout.buildDirectory.dir("reports/ktlint"))
}