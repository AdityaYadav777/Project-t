plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.aditya.projectt"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aditya.projectt"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
   buildFeatures{
       viewBinding=true
       //noinspection DataBindingWithoutKapt
       dataBinding =true
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




    // Add the dependency for the Firebase Authentication library


    // Also add the dependency for the Google Play services library and specify its version
    implementation ("com.github.ZEGOCLOUD:zego_inapp_chat_uikit_android:+")
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation ("app.rive:rive-android:8.7.0")
    implementation ("androidx.startup:startup-runtime:1.1.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.jsibbold:zoomage:1.3.1")
    implementation ("nl.joery.animatedbottombar:library:1.1.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation("io.coil-kt:coil:2.5.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-firestore:24.10.2")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("androidx.activity:activity:1.8.0")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation("com.google.firebase:firebase-database-ktx:20.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    implementation("com.google.firebase:firebase-messaging")

}