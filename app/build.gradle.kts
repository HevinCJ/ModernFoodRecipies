plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-android")
    id("kotlin-kapt")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}






android {
    namespace = "com.example.modernfoodrecipe"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.modernfoodrecipe"
        minSdk = 27
        targetSdk = 33
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

    kapt {
        correctErrorTypes = true
    }


    buildFeatures {
        viewBinding = true
        dataBinding = true

    }

    dependencies {

        val  nav_version = "2.6.0"
        val room_version = "2.5.2"




        // Kotlin navigation
        implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
        implementation("androidx.navigation:navigation-ui-ktx:$nav_version")



        //room
        implementation("androidx.room:room-runtime:$room_version")
        annotationProcessor("androidx.room:room-compiler:$room_version")
        ksp("androidx.room:room-compiler:$room_version")
        implementation("androidx.room:room-ktx:$room_version")



        //retrofit
        implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        implementation ("com.squareup.retrofit2:converter-gson:2.7.2")


        //dagger_hilt
        implementation ("com.google.dagger:hilt-android:2.48")
        annotationProcessor ("com.google.dagger:hilt-compiler:2.48")
        kapt("com.google.dagger:hilt-android-compiler:2.48")

        //coroutines
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

        //lifecycle
        val lifecycle_version = "2.6.2"

        // ViewModel
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
        // ViewModel utilities for Compose
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
        // LiveData
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
        // Lifecycles only (without ViewModel or LiveData)
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
        // Lifecycle utilities for Compose
        implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle_version")
        // Saved state module for ViewModel
        implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")
        // Annotation processor
        kapt("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")

        //coil for faster image loading
        implementation("io.coil-kt:coil:2.4.0")

        //gson
        implementation("com.google.code.gson:gson:2.10.1")

        //shimmer recyclerview
        implementation ("com.facebook.shimmer:shimmer:0.5.0")

        //jsoup
        implementation("org.jsoup:jsoup:1.16.1")

        //datastore
        // Preferences DataStore (SharedPreferences like APIs)
        dependencies {
            implementation("androidx.datastore:datastore-preferences:1.0.0")

            // optional - RxJava2 support
            implementation("androidx.datastore:datastore-preferences-rxjava2:1.0.0")

            // optional - RxJava3 support
            implementation("androidx.datastore:datastore-preferences-rxjava3:1.0.0")
        }

        // Alternatively - use the following artifact without an Android dependency.
        dependencies {
            implementation("androidx.datastore:datastore-preferences-core:1.0.0")
        }


        implementation("androidx.core:core-ktx:1.9.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.9.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    }
}
dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}

