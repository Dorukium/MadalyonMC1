plugins {
    id("java-library")
}

dependencies {
    implementation(project(":HMCLCore"))
    
    // Boot specific dependencies
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
    
    // JavaFX for bootstrap UI (if needed)
    implementation("org.openjfx:javafx-base:17")
    implementation("org.openjfx:javafx-controls:17")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Xlint:deprecation"))
}

// Create minimal boot classes
tasks.register("createMinimalBootClasses") {
    doLast {
        val sourceDir = file("src/main/java/org/jackhuang/hmcl/boot")
        sourceDir.mkdirs()
        
        // Create bootstrap launcher
        val bootFile = file("$sourceDir/Bootstrap.java")
        bootFile.writeText("""
package org.jackhuang.hmcl.boot;

import org.jackhuang.hmcl.core.LauncherCore;
import org.jackhuang.hmcl.core.Configuration;
import org.jackhuang.hmcl.core.VersionManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * HMCL Bootstrap - Entry point for the launcher
 * Handles initialization and main launcher logic
 */
public class Bootstrap {
    private static final Logger LOGGER = Logger.getLogger(Bootstrap.class.getName());
    private static final String LAUNCHER_NAME = "MadalyonMC Launcher";
    
    private LauncherCore core;
    private Configuration config;
    
    public static void main(String[] args) {
        LOGGER.info("Starting " + LAUNCHER_NAME);
        
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.initialize();
            bootstrap.start();
        } catch (Exception e) {
            LOGGER.severe("Failed to start launcher: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private void initialize() {
        LOGGER.info("Initializing launcher components...");
        
        // Initialize core launcher
        core = new LauncherCore();
        
        // Initialize configuration
        Path configDir = Paths.get(System.getProperty("user.home"), ".hmcl");
        config = new Configuration(configDir);
        
        LOGGER.info("Launcher initialized successfully");
        LOGGER.info("Version: " + core.getVersion());
        LOGGER.info("Java Version: " + System.getProperty("java.version"));
    }
    
    private void start() {
        LOGGER.info("Starting launcher...");
        
        // Check Java compatibility
        if (!core.isCompatibleJavaVersion()) {
            LOGGER.warning("Java version may not be compatible with all Minecraft versions");
        }
        
        // Load saved configuration
        config.saveConfiguration();
        
        // Here you would normally start the JavaFX UI
        // For now, we'll just log that the bootstrap is working
        LOGGER.info("Bootstrap completed successfully");
        LOGGER.info("Launcher is ready to start the main UI");
        
        // Keep the bootstrap running
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Shutting down launcher...");
            if (core != null) {
                core.shutdown();
            }
        }));
    }
    
    public LauncherCore getCore() {
        return core;
    }
    
    public Configuration getConfig() {
        return config;
    }
    
    public static String getVersion() {
        return "1.0.0";
    }
}
""".trimIndent())
        
        // Create boot configuration
        val bootConfigFile = file("$sourceDir/BootConfiguration.java")
        bootConfigFile.writeText("""
package org.jackhuang.hmcl.boot;

import java.util.Properties;
import java.util.logging.Logger;

/**
 * Bootstrap configuration management
 */
public class BootConfiguration {
    private static final Logger LOGGER = Logger.getLogger(BootConfiguration.class.getName());
    
    private final Properties properties;
    
    public BootConfiguration() {
        this.properties = new Properties();
        loadDefaults();
    }
    
    private void loadDefaults() {
        // JVM arguments
        properties.setProperty("jvm.args.memory.max", "4G");
        properties.setProperty("jvm.args.memory.min", "2G");
        properties.setProperty("jvm.args.gc", "-XX:+UseG1GC");
        properties.setProperty("jvm.args.gc.pause", "-XX:MaxGCPauseMillis=100");
        
        // Game arguments
        properties.setProperty("game.args.width", "854");
        properties.setProperty("game.args.height", "480");
        properties.setProperty("game.args.fullscreen", "false");
        
        // Performance
        properties.setProperty("performance.optimizations", "true");
        properties.setProperty("performance.hardware.acceleration", "true");
        properties.setProperty("performance.memory.optimize", "true");
        
        LOGGER.info("Boot configuration loaded with defaults");
    }
    
    public String getJvmArgs() {
        StringBuilder args = new StringBuilder();
        args.append("-Xmx").append(properties.getProperty("jvm.args.memory.max")).append(" ");
        args.append("-Xms").append(properties.getProperty("jvm.args.memory.min")).append(" ");
        args.append(properties.getProperty("jvm.args.gc")).append(" ");
        args.append(properties.getProperty("jvm.args.gc.pause"));
        return args.toString();
    }
    
    public String getGameArgs() {
        StringBuilder args = new StringBuilder();
        args.append("--width ").append(properties.getProperty("game.args.width")).append(" ");
        args.append("--height ").append(properties.getProperty("game.args.height"));
        
        if (Boolean.parseBoolean(properties.getProperty("game.args.fullscreen"))) {
            args.append(" --fullscreen");
        }
        
        return args.toString();
    }
    
    public Properties getProperties() {
        return new Properties(properties);
    }
}
""".trimIndent())
    }
}

tasks.compileJava {
    dependsOn("createMinimalBootClasses")
}