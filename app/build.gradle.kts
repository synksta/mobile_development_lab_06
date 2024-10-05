plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
//    kotlin("kapt")
//    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.mobile_development_lab_06"
    compileSdk = 34

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.mobile_development_lab_06"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.lifecycle.viewmodel.ktx) // Замените на актуальную версию
    implementation(libs.androidx.lifecycle.livedata.ktx) // Замените на актуальную версию

    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.room.compiler)

    // To use Kotlin Symbol Processing (KSP)
    ksp(libs.room.compiler)

    implementation(libs.androidx.activity.ktx) // Замените на актуальную версию
    implementation(libs.androidx.fragment.ktx) // Замените на актуальную версию
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v280) // Замените на актуальную версию
    implementation(libs.androidx.lifecycle.livedata.ktx.v280)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}