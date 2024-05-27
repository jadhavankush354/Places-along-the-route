plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.placesalongtheroute"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.placesalongtheroute"
        minSdk = 24
        targetSdk = 34
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
            excludes += "/META-INF/NOTICE.md"
            excludes += "/META-INF/LICENSE.md"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Firebase
    implementation ("com.google.firebase:firebase-appcheck-safetynet:16.1.2")
    implementation("com.google.firebase:firebase-firestore:25.0.0")
    implementation("com.google.firebase:firebase-storage-ktx:21.0.0")

    // Navigation
    implementation ("androidx.navigation:navigation-compose:2.7.7")

    // Icons
    implementation ("androidx.compose.material:material-icons-extended:1.6.7")

    // Google Map
    implementation ("com.google.maps:google-maps-services:0.17.0")
    implementation ("com.google.maps.android:maps-compose:4.3.3")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation ("com.google.android.gms:play-services-location:21.2.0")

    // Retrofit for making network requests
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:adapter-rxjava3:2.9.0")

    // Coroutine support for Retrofit
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.1")

    // Runtime
    implementation ("androidx.work:work-runtime:2.9.0")
    implementation ("androidx.compose.ui:ui:1.6.7")
    implementation ("androidx.navigation:navigation-compose:2.7.7")
    implementation ("androidx.compose.ui:ui-tooling:1.6.7")
    implementation ("androidx.compose.compiler:compiler:1.5.14")
    implementation ("androidx.compose.material:material:1.6.7")

    //Mail sender
    implementation("com.sun.mail:android-mail:1.6.7")
}