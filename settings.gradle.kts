import de.fayard.refreshVersions.bootstrapRefreshVersions
import de.fayard.refreshVersions.migrateRefreshVersionsIfNeeded

buildscript {
    val useSnapshot = false
    repositories { mavenLocal() ; gradlePluginPortal() }
    dependencies.classpath(
        if (useSnapshot) "de.fayard.refreshVersions:refreshVersions:0.9.6-SNAPSHOT" else "de.fayard.refreshVersions:refreshVersions:0.9.5"
////                                                                                                                    # available:0.9.6"

    )
}
plugins {
    id("com.gradle.enterprise").version("3.1.1")
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishOnFailure()
    }
}

include(":app")
rootProject.name = "GraySky"

migrateRefreshVersionsIfNeeded("0.9.5") // Will be automatically removed by refreshVersions when upgraded to the latest version.

bootstrapRefreshVersions()
