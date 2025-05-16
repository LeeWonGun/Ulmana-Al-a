// Gradle Sync 시 테스트 출력
println("✅ [Gradle Test] OPENAI_KEY = ${project.findProperty("OPENAI_API_KEY")}")

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.ulmanaala"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ulmanaala"
        minSdk = 33
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // ✅ 환경 변수 읽기 (수정된 부분)
        val openaiKey = project.findProperty("OPENAI_API_KEY") as String? ?: ""
        buildConfigField("String", "OPENAI_API_KEY", "\"$openaiKey\"")
    }

    // ✅ BuildConfig 생성 활성화
    buildFeatures {
        buildConfig = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}