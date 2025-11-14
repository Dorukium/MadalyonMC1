plugins {
    id("java-library")
}

dependencies {
    implementation(project(":HMCLCore"))
    
    // Transformer specific dependencies
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
    
    // ASM for bytecode transformation
    implementation("org.ow2.asm:asm:9.5")
    implementation("org.ow2.asm:asm-commons:9.5")
    implementation("org.ow2.asm:asm-tree:9.5")
    implementation("org.ow2.asm:asm-util:9.5")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Xlint:deprecation"))
}

// Create minimal transformer classes
tasks.register("createMinimalClasses") {
    doLast {
        val sourceDir = file("src/main/java/org/jackhuang/hmcl/transformer")
        sourceDir.mkdirs()
        
        val transformerFile = file("$sourceDir/TransformerDiscoveryService.java")
        transformerFile.writeText("""
package org.jackhuang.hmcl.transformer;

import java.util.logging.Logger;
import java.util.ServiceLoader;
import java.util.List;
import java.util.ArrayList;

/**
 * Minimal Transformer Discovery Service
 * Discovers and loads transformers for Minecraft modding
 */
public class TransformerDiscoveryService {
    private static final Logger LOGGER = Logger.getLogger(TransformerDiscoveryService.class.getName());
    
    private final List<Transformer> transformers = new ArrayList<>();
    
    public TransformerDiscoveryService() {
        discoverTransformers();
    }
    
    private void discoverTransformers() {
        LOGGER.info("Discovering transformers...");
        
        // ServiceLoader to discover transformers
        ServiceLoader<Transformer> loader = ServiceLoader.load(Transformer.class);
        for (Transformer transformer : loader) {
            transformers.add(transformer);
            LOGGER.info("Discovered transformer: " + transformer.getName());
        }
        
        if (transformers.isEmpty()) {
            LOGGER.warning("No transformers found");
        }
    }
    
    public List<Transformer> getTransformers() {
        return new ArrayList<>(transformers);
    }
    
    public boolean hasTransformers() {
        return !transformers.isEmpty();
    }
    
    public static String getVersion() {
        return "1.0.0";
    }
}

/**
 * Transformer interface for bytecode transformation
 */
interface Transformer {
    String getName();
    byte[] transform(String className, byte[] classBytes);
    boolean shouldTransform(String className);
}
""".trimIndent())
        
        // Create a basic transformer implementation
        val basicTransformerFile = file("$sourceDir/BasicTransformer.java")
        basicTransformerFile.writeText("""
package org.jackhuang.hmcl.transformer;

/**
 * Basic transformer implementation
 */
public class BasicTransformer implements Transformer {
    
    @Override
    public String getName() {
        return "BasicTransformer";
    }
    
    @Override
    public byte[] transform(String className, byte[] classBytes) {
        // Basic transformation - return original bytes
        return classBytes;
    }
    
    @Override
    public boolean shouldTransform(String className) {
        // Transform all classes by default
        return true;
    }
}
""".trimIndent())
    }
}

tasks.compileJava {
    dependsOn("createMinimalClasses")
}