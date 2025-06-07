import org.gradle.api.tasks.Delete

buildscript {

    repositories {
        google()
        mavenCentral()
    }

    val gradleVersion = property("gradleVersion") as String
    val kotlinVersion = property("kotlinVersion") as String
    val daggerVersion = property("daggerVersion") as String

    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$daggerVersion")
    }

}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
