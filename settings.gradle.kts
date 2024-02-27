pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "SodaWorld"
include(":app")
include(":common:base")
include(":common:database")
include(":common:wanandroid_api")
include(":modules:music")
include(":modules:account")
include(":modules:wanandroid")
