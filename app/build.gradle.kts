plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 35

    defaultConfig {
        applicationId = "ar.com.ericpennachini.fashiondog.app"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        //useIR = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = property("composeVersion") as String
//        kotlinCompilerVersion = property("kotlinVersion") as String
    }
    namespace = "ar.com.ericpennachini.fashiondog.app"

//    packagingOptions {
//        resources {
//            excludes += '/META-INF/{AL2.0,LGPL2.1}'
//        }
//    }
}

dependencies {

    // Base Android dependencies
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.material)
    implementation(libs.accompanist.systemuicontroller)

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
//    implementation("androidx.compose.foundation:foundation:1.1.1")
//    implementation("androidx.compose.material3:material3:1.0.1")
//    implementation("androidx.compose.material:material:1.1.1")
//    implementation("androidx.compose.material:material-icons-core:1.1.1")
//    implementation("androidx.compose.material:material-icons-extended:1.1.1")
//    implementation("androidx.compose.runtime:runtime-livedata:1.1.1")
//    implementation("androidx.compose.ui:ui:1.1.1")
//    implementation("androidx.compose.ui:ui-tooling-preview:1.1.1")
//    implementation("androidx.compose.ui:ui-tooling:1.1.1")

    // Hilt DI
    implementation(libs.androidx.hilt.lifecycle.viewmodel)
    implementation(libs.dagger.hilt.android)
    annotationProcessor(libs.androidx.hilt.compiler)
    kapt(libs.dagger.hilt.compiler)
    kapt(libs.androidx.hilt.compiler)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)

}
