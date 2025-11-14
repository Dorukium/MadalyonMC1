plugins {
    alias(libs.plugins.shadow)
    alias(libs.plugins.javafx.plugin)
}

val projectConfig = java.util.Properties().apply {
    val cfg = rootProject.file("config/project.properties")
    if (cfg.exists()) cfg.inputStream().use { load(it) }
}

// Download authlib-injector jar from GitHub releases and embed into assets
val authlibVersion = libs.versions.authlib.injector.get()
val authlibInjectorJar = layout.buildDirectory.file("embed/authlib-injector-$authlibVersion.jar")
val downloadAuthlibInjector by tasks.registering {
    outputs.file(authlibInjectorJar)
    doLast {
        val url = "https://github.com/yushijinhun/authlib-injector/releases/download/v$authlibVersion/authlib-injector-$authlibVersion.jar"
        val target = authlibInjectorJar.get().asFile
        target.parentFile.mkdirs()
        logger.lifecycle("Downloading authlib-injector from $url")
        java.net.URL(url).openStream().use { input: java.io.InputStream ->
            target.outputStream().use { output: java.io.OutputStream -> input.copyTo(output) }
        }
    }
}

val isOfficial = (System.getenv("CI") ?: System.getenv("GITHUB_ACTIONS"))?.equals("true", ignoreCase = true) == true

val versionType = System.getenv("VERSION_TYPE") ?: if (isOfficial) "nightly" else "unofficial"
val versionRoot = System.getenv("VERSION_ROOT") ?: projectConfig.getProperty("versionRoot") ?: "3"

val microsoftAuthId = System.getenv("MICROSOFT_AUTH_ID") ?: ""
val microsoftAuthSecret = System.getenv("MICROSOFT_AUTH_SECRET") ?: ""
val curseForgeApiKey = System.getenv("CURSEFORGE_API_KEY") ?: ""

val launcherExe = System.getenv("HMCL_LAUNCHER_EXE") ?: ""

val buildNumber = System.getenv("BUILD_NUMBER")?.toInt()
if (buildNumber != null) {
    version = if (isOfficial && versionType == "dev") {
        "$versionRoot.0.$buildNumber"
    } else {
        "$versionRoot.$buildNumber"
    }
} else {
    val shortCommit = System.getenv("GITHUB_SHA")?.lowercase()?.substring(0, 7)
    version = if (shortCommit.isNullOrBlank()) {
        "$versionRoot.SNAPSHOT"
    } else if (isOfficial) {
        "$versionRoot.dev-$shortCommit"
    } else {
        "$versionRoot.unofficial-$shortCommit"
    }
}

javafx {
    version = "17"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.swing", "javafx.web")
}

// Removed embedResources configuration; we now download and embed authlib-injector explicitly

dependencies {
    implementation(project(":HMCLCore"))
    implementation(project(":HMCLBoot"))
    implementation("libs:JFoenix")
    implementation(libs.twelvemonkeys.imageio.webp)
    implementation(libs.java.info)

    // Optional dependencies removed to avoid resolution issues
}

fun digest(algorithm: String, bytes: ByteArray): ByteArray = java.security.MessageDigest.getInstance(algorithm).digest(bytes)

fun createChecksum(file: java.io.File) {
    val algorithms = linkedMapOf(
        "SHA-1" to "sha1",
        "SHA-256" to "sha256",
        "SHA-512" to "sha512"
    )

    algorithms.forEach { (algorithm, ext) ->
        java.io.File(file.parentFile, "${file.name}.$ext").writeText(
            digest(algorithm, file.readBytes()).joinToString(separator = "", postfix = "\n") { "%02x".format(it) }
        )
    }
}

fun attachSignature(jar: java.io.File) {
    val keyLocation = System.getenv("HMCL_SIGNATURE_KEY")
    if (keyLocation == null) {
        logger.warn("Missing signature key")
        return
    }

    val privatekey = java.security.KeyFactory.getInstance("RSA")
        .generatePrivate(java.security.spec.PKCS8EncodedKeySpec(java.io.File(keyLocation).readBytes()))
    val signer = java.security.Signature.getInstance("SHA512withRSA")
    signer.initSign(privatekey)
    java.util.zip.ZipFile(jar).use { zip ->
        zip.stream()
            .sorted(java.util.Comparator.comparing<java.util.zip.ZipEntry, String> { entry -> entry.name })
            .filter { entry -> entry.name != "META-INF/hmcl_signature" }
            .forEach { entry ->
                signer.update(digest("SHA-512", entry.name.toByteArray()))
                zip.getInputStream(entry).use { inputStream ->
                    signer.update(digest("SHA-512", inputStream.readBytes()))
                }
            }
    }
    val signature = signer.sign()
    java.nio.file.FileSystems.newFileSystem(java.net.URI.create("jar:" + jar.toURI()), emptyMap<String, Any>()).use { zipfs ->
        java.nio.file.Files.newOutputStream(zipfs.getPath("META-INF/hmcl_signature")).use { outputStream -> outputStream.write(signature) }
    }
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "17"
    targetCompatibility = "17"
}

val addOpens = listOf(
    "java.base/java.lang",
    "java.base/java.lang.reflect",
    "java.base/jdk.internal.loader",
    "javafx.base/com.sun.javafx.binding",
    "javafx.base/com.sun.javafx.event",
    "javafx.base/com.sun.javafx.runtime",
    "javafx.graphics/javafx.css",
    "javafx.graphics/com.sun.javafx.stage",
    "javafx.graphics/com.sun.prism",
    "javafx.controls/com.sun.javafx.scene.control",
    "javafx.controls/com.sun.javafx.scene.control.behavior",
    "javafx.controls/javafx.scene.control.skin",
    "jdk.attach/sun.tools.attach",
)

val hmclProperties = buildList {
    add("hmcl.version" to project.version.toString())
    add("hmcl.add-opens" to addOpens.joinToString(" "))
    System.getenv("GITHUB_SHA")?.let {
        add("hmcl.version.hash" to it)
    }
    add("hmcl.version.type" to versionType)
    add("hmcl.microsoft.auth.id" to microsoftAuthId)
    add("hmcl.microsoft.auth.secret" to microsoftAuthSecret)
    add("hmcl.curseforge.apikey" to curseForgeApiKey)
    add("hmcl.authlib-injector.version" to libs.versions.authlib.injector.get())
}

val hmclPropertiesFile = layout.buildDirectory.file("hmcl.properties")
val createPropertiesFile by tasks.registering {
    outputs.file(hmclPropertiesFile)
    hmclProperties.forEach { (k, v) -> inputs.property(k, v) }

    doLast {
        val targetFile = hmclPropertiesFile.get().asFile
        targetFile.parentFile.mkdir()
        targetFile.bufferedWriter().use {
            for ((k, v) in hmclProperties) {
                it.write("$k=$v\n")
            }
        }
    }
}

tasks.jar {
    enabled = false
    dependsOn(tasks["shadowJar"])
}

val jarPath = tasks.jar.get().archiveFile.get().asFile

tasks.shadowJar {
    dependsOn(createPropertiesFile)

    archiveClassifier.set(null as String?)

    exclude("**/package-info.class")
    exclude("META-INF/maven/**")

    exclude("META-INF/services/javax.imageio.spi.ImageReaderSpi")
    exclude("META-INF/services/javax.imageio.spi.ImageInputStreamSpi")

    listOf(
        "aix-*", "sunos-*", "openbsd-*", "dragonflybsd-*", "freebsd-*", "linux-*", "darwin-*",
        "*-ppc", "*-ppc64le", "*-s390x", "*-armel",
    ).forEach { exclude("com/sun/jna/$it/**") }

    minimize {
        exclude(dependency("com.google.code.gson:.*:.*"))
        exclude(dependency("net.java.dev.jna:jna:.*"))
        exclude(dependency("libs:JFoenix:.*"))
        exclude(project(":HMCLBoot"))
    }

    manifest.attributes(
        "Created-By" to "Copyright(c) 2013-2025 huangyuhui.",
        "Implementation-Version" to project.version.toString(),
        "Main-Class" to "com.madalyonmc.launcher.EntryPoint",
        "Multi-Release" to "true",
        "Add-Opens" to addOpens.joinToString(" "),
        "Enable-Native-Access" to "ALL-UNNAMED"
    )

    if (launcherExe.isNotBlank()) {
        into("assets") {
            from(file(launcherExe))
        }
    }

    doLast {
        attachSignature(jarPath)
        createChecksum(jarPath)
    }
}

tasks.processResources {
    dependsOn(createPropertiesFile, downloadAuthlibInjector)

    into("assets/") {
        from(hmclPropertiesFile)
        from(authlibInjectorJar)
    }
}

val makeExecutables by tasks.registering {
    val extensions = listOf("sh")

    dependsOn(tasks.jar)

    inputs.file(jarPath)
    outputs.files(extensions.map { java.io.File(jarPath.parentFile, jarPath.nameWithoutExtension + '.' + it) })

    doLast {
        val jarContent = jarPath.readBytes()

        java.util.zip.ZipFile(jarPath).use { zipFile ->
            for (extension in extensions) {
                val output = java.io.File(jarPath.parentFile, jarPath.nameWithoutExtension + '.' + extension)
                val entry = zipFile.getEntry("assets/HMCLauncher.$extension")
                    ?: throw GradleException("HMCLauncher.$extension not found")

                output.outputStream().use { outputStream ->
                    zipFile.getInputStream(entry).use { input -> input.copyTo(outputStream) }
                    outputStream.write(jarContent)
                }

                createChecksum(output)
            }
        }
    }
}

tasks.build {
    dependsOn(makeExecutables)
}

fun parseToolOptions(options: String?): MutableList<String> {
    if (options == null)
        return mutableListOf()

    val builder = StringBuilder()
    val result = mutableListOf<String>()

    var offset = 0

    loop@ while (offset < options.length) {
        val ch = options[offset]
        if (Character.isWhitespace(ch)) {
            if (builder.isNotEmpty()) {
                result += builder.toString()
                builder.clear()
            }

            while (offset < options.length && Character.isWhitespace(options[offset])) {
                offset++
            }

            continue@loop
        }

        if (ch == '\'' || ch == '"') {
            offset++

            while (offset < options.length) {
                val ch2 = options[offset++]
                if (ch2 != ch) {
                    builder.append(ch2)
                } else {
                    continue@loop
                }
            }

            throw GradleException("Unmatched quote in $options")
        }

        builder.append(ch)
        offset++
    }

    if (builder.isNotEmpty()) {
        result += builder.toString()
    }

    return result
}

tasks.register<JavaExec>("run") {
    dependsOn(tasks.jar)

    group = "application"

    classpath = files(jarPath)
    workingDir = rootProject.rootDir

    val vmOptions = parseToolOptions(System.getenv("HMCL_JAVA_OPTS") ?: "-Xmx1g")
    if (vmOptions.none { it.startsWith("-Dhmcl.offline.auth.restricted=") })
        vmOptions += "-Dhmcl.offline.auth.restricted=false"

    jvmArgs(vmOptions)

    val hmclJavaHome = System.getenv("HMCL_JAVA_HOME")
    if (hmclJavaHome != null) {
        this.executable(
            file(hmclJavaHome).resolve("bin")
                .resolve(if (System.getProperty("os.name").lowercase().startsWith("windows")) "java.exe" else "java")
        )
    }

    doFirst {
        logger.quiet("HMCL_JAVA_OPTS: {}", vmOptions)
        logger.quiet("HMCL_JAVA_HOME: {}", hmclJavaHome ?: System.getProperty("java.home"))
    }
}
