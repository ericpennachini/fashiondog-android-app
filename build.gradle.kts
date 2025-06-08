import org.gradle.api.tasks.Delete

buildscript {

    repositories {
        google()
        mavenCentral()
    }

}

tasks.register<Delete>("clean") {
    delete(project.layout.buildDirectory)
}
