import de.fayard.refreshVersions.bootstrapRefreshVersions

buildscript {
    repositories { mavenLocal() ; gradlePluginPortal() }
    dependencies.classpath(
        "de.fayard.refreshVersions:refreshVersions:0.9.5"
        //"de.fayard.refreshVersions:refreshVersions:0.9.6-SNAPSHOT"
    )
}

include(":app")
rootProject.name = "GraySky"

bootstrapRefreshVersions()
