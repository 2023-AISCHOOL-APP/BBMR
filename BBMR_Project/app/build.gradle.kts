plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // parcelable 사용
    id("kotlin-parcelize")

}

android {
    namespace = "com.example.bbmr_project"
    compileSdk = 34

    // viewBinding -> findViewById 관리
    viewBinding{
        enable = true
    }

    defaultConfig {
        applicationId = "com.example.bbmr_project"
        minSdk = 24
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
    buildFeatures {
        viewBinding = true
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

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.gridlayout:gridlayout:1.0.0")
    implementation("androidx.camera:camera-mlkit-vision:1.4.0-alpha02")
    implementation("androidx.media3:media3-common:1.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("com.google.android.material:material:1.10.0")
    implementation ("androidx.fragment:fragment-ktx:1.3.0")
    // mlkit + CameraX API
    val camerax_version = "1.2.0-rc01"
    implementation("androidx.camera:camera-core:${camerax_version}")
    implementation("androidx.camera:camera-lifecycle:${camerax_version}")
    implementation("androidx.camera:camera-view:${camerax_version}")
    implementation("androidx.camera:camera-camera2:${camerax_version}")
    implementation("com.google.mlkit:face-detection:16.1.5")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.6.0")
    // retrofit

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    // 231121 --- 코드 추가(google, okhttp3)
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.2.1")

    // Glide 사용 -> 사진을 URL로 받아옴
    implementation ("com.github.bumptech.glide:glide:4.12.0") // 최신 버전 확인 필요
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")


}