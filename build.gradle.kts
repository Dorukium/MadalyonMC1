import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion

plugins {
    id("checkstyle")
    id("org.openjfx.javafxplugin") version "0.0.13" apply false
}

group = "org.jackhuang"
version = "3.0"

subprojects {
    apply {
        plugin("java")
        plugin("idea")
        plugin("maven-publish")
        plugin("checkstyle")
    }

    // Java toolchain for all subprojects (configure via extension since the typed accessor may not be available yet)
    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    repositories {
        flatDir {
            name = "libs"
            dirs = setOf(rootProject.file("lib"))
        }

        System.getenv("MAVEN_CENTRAL_REPO").let { repo ->
            if (repo.isNullOrBlank())
                mavenCentral()
            else
                maven(url = repo)
        }

        maven(url = "https://jitpack.io")
        maven(url = "https://libraries.minecraft.net")
        maven(url = "https://repo1.maven.org/maven2")
        maven(url = "https://repo.maven.apache.org/maven2")
        maven(url = "https://jcenter.bintray.com")
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf(
            "-Xlint:unchecked",
            "-Xlint:deprecation",
            "-parameters"
        ))
    }

    @Suppress("UnstableApiUsage")
    tasks.withType<Checkstyle> {
        maxHeapSize.set("2g")
        enabled = false
    }

    // Use default Checkstyle configuration; do not attempt to set non-existing properties

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging.showStandardStreams = true
        
        // JVM arguments for tests
        jvmArgs = listOf(
            "-Xmx2g",
            "-XX:+UseG1GC",
            "-Dfile.encoding=UTF-8"
        )
    }

    configure<PublishingExtension> {
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])
                
                pom {
                    name.set(project.name)
                    description.set("HMCL ${project.name} module")
                    url.set("https://github.com/Dorukium/hmcl1")
                    
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    
                    developers {
                        developer {
                            id.set("madalyonmc")
                            name.set("MadalyonMC Team")
                            email.set("admin@madalyonmc.com")
                        }
                    }
                    
                    scm {
                        connection.set("scm:git:git://github.com/Dorukium/hmcl1.git")
                        developerConnection.set("scm:git:ssh://github.com:Dorukium/hmcl1.git")
                        url.set("https://github.com/Dorukium/hmcl1")
                    }
                }
            }
        }
        repositories {
            mavenLocal()
        }
    }

    tasks.register("checkstyle") {
        dependsOn(tasks["checkstyleMain"], tasks["checkstyleTest"])
    }
}

// HMCL subproject will configure JavaFX in its own build script

// Default tasks
defaultTasks("clean", "build")

// Custom tasks (removed custom types to ensure build compatibility)

// Build info task
tasks.register("buildInfo") {
    group = "build"
    description = "Display build information"
    
    doLast {
        println("MadalyonMC Launcher Build Information")
        println("====================================")
        println("Version: $version")
        println("Group: $group")
        println("Java Version: ${System.getProperty("java.version")}")
        println("Gradle Version: ${gradle.gradleVersion}")
        println("Build Time: ${java.time.LocalDateTime.now()}")
        println("Build User: ${System.getProperty("user.name")}")
        println("OS: ${System.getProperty("os.name")} ${System.getProperty("os.version")}")
        println("Architecture: ${System.getProperty("os.arch")}")
    }
}

// Clean build task
tasks.register("cleanBuild") {
    group = "build"
    description = "Clean and build all modules"
    dependsOn("clean", "build")
}

// Fast build task (skip tests)
tasks.register("fastBuild") {
    group = "build"
    description = "Fast build without tests"
    
    doFirst {
        gradle.startParameter.excludedTaskNames.add("test")
    }
    
    dependsOn("clean", "build")
}