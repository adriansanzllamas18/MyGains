import org.jetbrains.kotlin.gradle.model.Kapt

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    id("kotlin-kapt") // Para usar KAPT
    id("com.google.dagger.hilt.android") // Aplica el plugin de Hilt
}

android {
    namespace = "com.example.mygains"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mygains"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation ("com.github.tehras:charts:0.2.4-alpha")
    implementation ("androidx.navigation:navigation-compose:2.8.1")
    implementation ("androidx.compose.material3:material3:1.3.0")
    implementation ("com.google.firebase:firebase-bom:33.3.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.7.2")

    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    // Hilt para Compose (para la integración con Jetpack Compose)
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")  // Para usar Hilt con Compose


    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation ("com.google.android.gms:play-services-fitness:21.0.0")

    // Google Sign-In
    implementation ("com.google.android.gms:play-services-auth:20.7.0")

    //corrutinas
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation ("io.coil-kt:coil-compose:2.4.0")


    implementation ("com.github.bumptech.glide:glide:4.13.0")

    // The compose calendar library for Android
    implementation("com.kizitonwose.calendar:compose:2.6.0")


    implementation ("com.airbnb.android:lottie-compose:5.0.0")

    implementation ("com.google.mlkit:barcode-scanning:17.3.0")

    // Dependencias de CameraX
    implementation ("com.google.guava:guava:31.0.1-android") // o la última versión disponible
    implementation ("androidx.camera:camera-core:1.1.0")
    implementation ("androidx.camera:camera-camera2:1.1.0")
    implementation ("androidx.camera:camera-lifecycle:1.1.0")
    implementation ("androidx.camera:camera-view:1.4.0") // Para PreviewView


    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    // Conversor JSON (Gson es uno de los más comunes)
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")

    implementation ("com.github.tehras:charts:0.2.4-alpha") // ComposeCharts

    implementation("com.patrykandpatrick.vico:compose:1.6.4")
    implementation("com.patrykandpatrick.vico:compose-m3:1.6.4")


}