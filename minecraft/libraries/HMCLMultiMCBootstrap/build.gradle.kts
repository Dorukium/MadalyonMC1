import java.time.LocalDate

plugins {
    id("java-library")
}

dependencies {
    implementation(project(":HMCLCore"))
    
    // MultiMC specific dependencies
    implementation(libs.gson)
    implementation(libs.commons.lang3)
    implementation(libs.commons.io)
    implementation(libs.httpclient)
    implementation(libs.httpmime)
    implementation(libs.commons.codec)
    implementation(libs.commons.compress)
    implementation(libs.xz)
    implementation(libs.slf4j)
    implementation(libs.log4j.slf4j.impl)
    implementation(libs.log4j.core)
    implementation(libs.javax.annotations)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Xlint:deprecation"))
}

// Create a minimal MultiMC bootstrap class
tasks.register("createMinimalClasses") {
    doLast {
        val sourceDir = file("src/main/java/org/jackhuang/hmcl/multimc")
        sourceDir.mkdirs()
        
        val bootstrapFile = file("$sourceDir/MultiMCBootstrap.java")
        bootstrapFile.writeText("""
package org.jackhuang.hmcl.multimc;

import java.util.logging.Logger;

/**
 * Minimal MultiMC Bootstrap implementation
 * This is a placeholder for MultiMC compatibility
 */
public class MultiMCBootstrap {
    private static final Logger LOGGER = Logger.getLogger(MultiMCBootstrap.class.getName());
    
    public static void main(String[] args) {
        LOGGER.info("MultiMC Bootstrap initialized");
        // Bootstrap logic will be implemented here
    }
    
    public static String getVersion() {
        return "1.0.0";
    }
    
    public static boolean isCompatible(String minecraftVersion) {
        // Basic compatibility check
        return minecraftVersion != null && !minecraftVersion.isEmpty();
    }
}
""".trimIndent())
        
        // Create manifest file
        val manifestDir = file("src/main/resources/META-INF")
        manifestDir.mkdirs()
        
        val manifestFile = file("$manifestDir/MANIFEST.MF")
        manifestFile.writeText("""
Manifest-Version: 1.0
Implementation-Title: HMCL MultiMC Bootstrap
Implementation-Version: 1.0.0
Implementation-Vendor: MadalyonMC
Built-By: ${System.getProperty("user.name")}
Built-Date: ${LocalDate.now()}
Created-By: Gradle
Build-Jdk: ${System.getProperty("java.version")}
""".trimIndent())
    }
}

tasks.compileJava {
    dependsOn("createMinimalClasses")
}