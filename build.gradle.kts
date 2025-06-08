import org.gradle.api.tasks.Delete

buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.classpath.gradle.androidGradlePlugin)
        classpath(libs.classpath.jetbrains.kotlinGradlePlugin)
        classpath(libs.classpath.dagger.hilt.androidGradlePlugin)
    }

}

tasks.register<Delete>("clean") {
    delete(project.layout.buildDirectory)
}
