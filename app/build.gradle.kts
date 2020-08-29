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
    val kotlin_version = "1.4.0"
    val retrofit_version = "2.7.1"
    val okhttp_version = "4.2.2"
    val gson_version = "2.8.6"
    val retrofit_gson_version = "2.9.0"
    val coroutines_version = "1.3.2"
    val databinding = "3.1.4"
    val navigationx = "2.2.2"
    val mockito = "3.3.3"
    val koin_version = "2.1.5"

    //implementation(fileTree(dir = "libs", include: listOf("*.jar")))
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.recyclerview:recyclerview:1.1.0")
    // For control over item selection of both touch and mouse driven selection
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0-rc01")

    // network
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.okhttp3:okhttp:${okhttp_version}")
    implementation("com.squareup.retrofit2:converter-gson:${retrofit_gson_version}")
    implementation("com.google.code.gson:gson:$gson_version")

    implementation("com.squareup.picasso:picasso:2.71828")

    // Koin for Android
    implementation("org.koin:koin-android:$koin_version")
    // or Koin for Lifecycle scoping
    implementation("org.koin:koin-android-scope:$koin_version")
    // or Koin for Android Architecture ViewModel
    implementation("org.koin:koin-android-viewmodel:$koin_version")
    // Koin AndroidX Experimental features
//    implementation("org.koin:koin-androidx-ext:$koin_version")

    // log
    implementation("com.jakewharton.timber:timber:4.7.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")

    implementation("com.google.firebase:firebase-analytics:17.5.0")

    implementation("junit:junit:4.12")

    testImplementation("junit:junit:4.12")
    testImplementation("org.koin:koin-test:$koin_version")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")

    // testImplementation(for pure JVM unit tests)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version")
    testImplementation("androidx.arch.core:core-testing:2.0.1")

    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")

    ktlint("com.pinterest:ktlint:0.38.1")
}

tasks.withType<KtlintCheckTask> {
    reporterOutputDir.set(project.layout.buildDirectory.dir("reports/ktlint"))
}