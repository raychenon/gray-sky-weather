
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
        named("release") {
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

    implementation(Kotlin.stdlib.jdk8)
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.lifecycle.extensions)
    implementation(AndroidX.lifecycle.viewModelKtx)
    implementation(AndroidX.lifecycle.liveDataKtx)
    implementation(AndroidX.recyclerView) // "androidx.recyclerview:recyclerview:_")
    // For control over item selection of both touch and mouse driven selection
    // implementation(AndroidX.recyclerViewSelection) TODO: 1.1.0 does not exist
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0-rc01")

    // network
    implementation(Square.retrofit2.retrofit)
    implementation(Square.okHttp3.okHttp)
    implementation(Square.retrofit2.converter.gson)
    implementation("com.google.code.gson:gson:_") // TODO

    implementation(Square.picasso)

    // Koin for Android
    implementation("org.koin:koin-android:_") // TODO add koin
    // or Koin for Lifecycle scoping
    implementation("org.koin:koin-android-scope:_")
    // or Koin for Android Architecture ViewModel
    implementation("org.koin:koin-android-viewmodel:_")
    // Koin AndroidX Experimental features
//    implementation("org.koin:koin-androidx-ext:$koin_version")

    implementation("com.github.GrenderG:Toasty:1.5.0")
    // log
    implementation(JakeWharton.timber)

    // Coroutines
    implementation(KotlinX.coroutines.core)
    implementation(KotlinX.coroutines.android)

    implementation("com.google.firebase:firebase-analytics:17.5.0")
    implementation("com.google.android.gms:play-services-location:17.0.0")
    // implementation(Google.firebase.analytics) // TODO does not work

    testImplementation(Testing.junit4)
    testImplementation("org.koin:koin-test:_")
    testImplementation(Testing.mockito.kotlin)

    // testImplementation(for pure JVM unit tests)
    testImplementation(KotlinX.coroutines.test)
    testImplementation(AndroidX.archCore.testing)

    androidTestImplementation(AndroidX.test.ext.junit)
    androidTestImplementation(AndroidX.test.espresso.core)

    ktlint("com.pinterest:ktlint:0.38.1")
}

tasks.withType<KtlintCheckTask> {
    reporterOutputDir.set(project.layout.buildDirectory.dir("reports/ktlint"))
}