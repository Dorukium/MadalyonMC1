plugins {
    id("java-library")
}

dependencies {
    // Core HMCL dependencies
    api(libs.gson)
    api(libs.xz)
    api(libs.httpclient)
    api(libs.httpmime)
    api(libs.commons.compress)
    api(libs.commons.lang3)
    api(libs.commons.io)
    api(libs.commons.codec)
    api(libs.commons.logging)
    api(libs.nashorn)
    api(libs.slf4j)
    api(libs.log4j.slf4j.impl)
    api(libs.log4j.core)
    api(libs.javax.annotations)
    
    // Additional core dependencies
    implementation("org.apache.httpcomponents:httpcore:4.4.16")
    implementation("org.apache.httpcomponents:httpclient-cache:4.5.14")
    implementation("org.apache.httpcomponents:httpmime:4.5.14")
    implementation("org.apache.commons:commons-collections4:4.4")
    implementation("org.apache.commons:commons-configuration2:2.9.0")
    implementation("commons-beanutils:commons-beanutils:1.9.4")
    
    // JSON and XML processing
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.15.2")
    implementation("org.json:json:20231013")
    
    // Compression and archive
    implementation("org.tukaani:xz:1.9")
    implementation("org.apache.commons:commons-compress:1.24.0")
    implementation("org.zeroturnaround:zt-zip:1.15")
    
    // Networking
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    
    // Security
    implementation("org.bouncycastle:bcprov-jdk15on:1.70")
    implementation("org.bouncycastle:bcpkix-jdk15on:1.70")
    
    // Testing
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf(
        "-Xlint:unchecked",
        "-Xlint:deprecation",
        "-parameters"
    ))
}

tasks.withType<Javadoc> {
    options {
        encoding = "UTF-8"
        charset("UTF-8")
    }
}

// Create minimal core classes
tasks.register("createMinimalCoreClasses") {
    doLast {
        val sourceDir = file("src/main/java/org/jackhuang/hmcl/core")
        sourceDir.mkdirs()
        
        // Create core launcher class
        val launcherCoreFile = file("$sourceDir/LauncherCore.java")
        launcherCoreFile.writeText("""
package org.jackhuang.hmcl.core;

import java.util.logging.Logger;
import java.util.Properties;

/**
 * HMCL Core Launcher Implementation
 * Core functionality for the Minecraft launcher
 */
public class LauncherCore {
    private static final Logger LOGGER = Logger.getLogger(LauncherCore.class.getName());
    private static final String VERSION = "3.0.0";
    
    private final Properties properties;
    
    public LauncherCore() {
        this.properties = new Properties();
        initializeCore();
    }
    
    private void initializeCore() {
        LOGGER.info("Initializing HMCL Core v" + VERSION);
        properties.setProperty("launcher.version", VERSION);
        properties.setProperty("launcher.build", System.getProperty("user.name") + "-" + System.currentTimeMillis());
    }
    
    public String getVersion() {
        return VERSION;
    }
    
    public Properties getProperties() {
        return new Properties(properties);
    }
    
    public static boolean isCompatibleJavaVersion() {
        String version = System.getProperty("java.version");
        return version != null && (version.startsWith("17") || version.startsWith("18") || version.startsWith("19") || version.startsWith("20") || version.startsWith("21"));
    }
    
    public void shutdown() {
        LOGGER.info("Shutting down HMCL Core");
        properties.clear();
    }
}
""".trimIndent())
        
        // Create configuration manager
        val configFile = file("$sourceDir/Configuration.java")
        configFile.writeText("""
package org.jackhuang.hmcl.core;

import java.io.*;
import java.nio.file.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Configuration management for HMCL
 */
public class Configuration {
    private static final Logger LOGGER = Logger.getLogger(Configuration.class.getName());
    private static final String CONFIG_FILE = "hmcl.properties";
    
    private final Properties properties;
    private final Path configPath;
    
    public Configuration(Path configDir) {
        this.configPath = configDir.resolve(CONFIG_FILE);
        this.properties = new Properties();
        loadConfiguration();
    }
    
    private void loadConfiguration() {
        try {
            if (Files.exists(configPath)) {
                try (InputStream input = Files.newInputStream(configPath)) {
                    properties.load(input);
                    LOGGER.info("Configuration loaded from: " + configPath);
                }
            } else {
                LOGGER.info("Configuration file not found, using defaults");
                setDefaults();
            }
        } catch (IOException e) {
            LOGGER.warning("Failed to load configuration: " + e.getMessage());
            setDefaults();
        }
    }
    
    private void setDefaults() {
        properties.setProperty("java.max.memory", "4G");
        properties.setProperty("java.min.memory", "2G");
        properties.setProperty("java.gc.type", "G1");
        properties.setProperty("game.window.width", "854");
        properties.setProperty("game.window.height", "480");
        properties.setProperty("game.fullscreen", "false");
        properties.setProperty("download.source", "official");
    }
    
    public void saveConfiguration() {
        try {
            Files.createDirectories(configPath.getParent());
            try (OutputStream output = Files.newOutputStream(configPath)) {
                properties.store(output, "HMCL Configuration");
                LOGGER.info("Configuration saved to: " + configPath);
            }
        } catch (IOException e) {
            LOGGER.warning("Failed to save configuration: " + e.getMessage());
        }
    }
    
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
    
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }
    
    public int getInteger(String key, int defaultValue) {
        String value = properties.getProperty(key);
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
""".trimIndent())
        
        // Create version manager
        val versionFile = file("$sourceDir/VersionManager.java")
        versionFile.writeText("""
package org.jackhuang.hmcl.core;

import java.util.logging.Logger;

/**
 * Version management for Minecraft and launcher
 */
public class VersionManager {
    private static final Logger LOGGER = Logger.getLogger(VersionManager.class.getName());
    
    public static final String LAUNCHER_VERSION = "3.0.0";
    public static final String MINECRAFT_MIN_VERSION = "1.7.10";
    public static final String MINECRAFT_MAX_VERSION = "1.20.4";
    
    public static boolean isSupportedMinecraftVersion(String version) {
        if (version == null || version.isEmpty()) {
            return false;
        }
        
        // Basic version checking
        try {
            String[] parts = version.split("\\.");
            if (parts.length >= 2) {
                int major = Integer.parseInt(parts[0]);
                int minor = Integer.parseInt(parts[1]);
                
                // Support versions from 1.7.10 onwards
                if (major > 1) return true;
                if (major == 1 && minor >= 7) return true;
            }
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid version format: " + version);
        }
        
        return false;
    }
    
    public static boolean isCompatibleJavaVersion(String minecraftVersion) {
        // Minecraft 1.17+ requires Java 16+
        if (minecraftVersion.startsWith("1.17") || minecraftVersion.startsWith("1.18") || 
            minecraftVersion.startsWith("1.19") || minecraftVersion.startsWith("1.20")) {
            return LauncherCore.isCompatibleJavaVersion();
        }
        return true; // Older versions work with Java 8+
    }
    
    public static String getRecommendedJavaVersion(String minecraftVersion) {
        if (minecraftVersion.startsWith("1.20")) return "21";
        if (minecraftVersion.startsWith("1.19")) return "17";
        if (minecraftVersion.startsWith("1.18")) return "17";
        if (minecraftVersion.startsWith("1.17")) return "17";
        return "8";
    }
}
""".trimIndent())
    }
}

tasks.compileJava {
    dependsOn("createMinimalCoreClasses")
}