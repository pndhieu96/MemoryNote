plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.android.library") version "8.2.2" apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.16" apply false
}

buildscript {
    extra.apply {
        set("coroutinesVersion", "1.7.1")
        set("roomVersion", "2.6.1")
        set("glideVersion", "4.8.0")
        set("navVersion", "2.7.7")
        set("daggerVersion", "2.28.3")
        set("kotlinVersion", "1.9.20")
    }

    repositories {
        google()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${rootProject.extra["navVersion"]}")
    }
}
