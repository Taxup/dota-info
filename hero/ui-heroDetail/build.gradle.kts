apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    "implementation"(project(Modules.heroInteractors))
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.heroDomain))

    "implementation"(Coil.coil)
}