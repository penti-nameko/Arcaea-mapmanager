plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    // --- ここを追加 ---
    kotlin("kapt")
    // ----------------
}

android {
    // ... (namespace, compileSdk 等)

    defaultConfig {
        // ...

        // Room のスキーマをエクスポートする設定（任意ですが推奨）
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }
}

dependencies {
    val room_version = "2.6.1"

    // Room
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version") // これを動かすために kapt プラグインが必要

    // Lifecycle & Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
}