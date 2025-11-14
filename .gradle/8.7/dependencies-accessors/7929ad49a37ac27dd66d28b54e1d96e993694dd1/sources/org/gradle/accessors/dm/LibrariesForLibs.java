package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the {@code libs} extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final AsmLibraryAccessors laccForAsmLibraryAccessors = new AsmLibraryAccessors(owner);
    private final AuthlibLibraryAccessors laccForAuthlibLibraryAccessors = new AuthlibLibraryAccessors(owner);
    private final CommonsLibraryAccessors laccForCommonsLibraryAccessors = new CommonsLibraryAccessors(owner);
    private final JacksonLibraryAccessors laccForJacksonLibraryAccessors = new JacksonLibraryAccessors(owner);
    private final JavaLibraryAccessors laccForJavaLibraryAccessors = new JavaLibraryAccessors(owner);
    private final JavafxLibraryAccessors laccForJavafxLibraryAccessors = new JavafxLibraryAccessors(owner);
    private final JavaxLibraryAccessors laccForJavaxLibraryAccessors = new JavaxLibraryAccessors(owner);
    private final JunitLibraryAccessors laccForJunitLibraryAccessors = new JunitLibraryAccessors(owner);
    private final Log4jLibraryAccessors laccForLog4jLibraryAccessors = new Log4jLibraryAccessors(owner);
    private final OkhttpLibraryAccessors laccForOkhttpLibraryAccessors = new OkhttpLibraryAccessors(owner);
    private final TwelvemonkeysLibraryAccessors laccForTwelvemonkeysLibraryAccessors = new TwelvemonkeysLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Dependency provider for <b>bcpkix</b> with <b>org.bouncycastle:bcpkix-jdk15on</b> coordinates and
     * with version reference <b>bcprov</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getBcpkix() {
        return create("bcpkix");
    }

    /**
     * Dependency provider for <b>bcprov</b> with <b>org.bouncycastle:bcprov-jdk15on</b> coordinates and
     * with version reference <b>bcprov</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getBcprov() {
        return create("bcprov");
    }

    /**
     * Dependency provider for <b>gson</b> with <b>com.google.code.gson:gson</b> coordinates and
     * with version reference <b>gson</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getGson() {
        return create("gson");
    }

    /**
     * Dependency provider for <b>hmclauncher</b> with <b>org.glavo:hmclauncher</b> coordinates and
     * with version reference <b>hmclauncher</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getHmclauncher() {
        return create("hmclauncher");
    }

    /**
     * Dependency provider for <b>httpclient</b> with <b>org.apache.httpcomponents:httpclient</b> coordinates and
     * with version reference <b>httpclient</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getHttpclient() {
        return create("httpclient");
    }

    /**
     * Dependency provider for <b>httpcore</b> with <b>org.apache.httpcomponents:httpcore</b> coordinates and
     * with version reference <b>httpcore</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getHttpcore() {
        return create("httpcore");
    }

    /**
     * Dependency provider for <b>httpmime</b> with <b>org.apache.httpcomponents:httpmime</b> coordinates and
     * with version reference <b>httpmime</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getHttpmime() {
        return create("httpmime");
    }

    /**
     * Dependency provider for <b>jfoenix</b> with <b>com.jfoenix:jfoenix</b> coordinates and
     * with version reference <b>jfoenix</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getJfoenix() {
        return create("jfoenix");
    }

    /**
     * Dependency provider for <b>nashorn</b> with <b>org.openjdk.nashorn:nashorn-core</b> coordinates and
     * with version reference <b>nashorn</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getNashorn() {
        return create("nashorn");
    }

    /**
     * Dependency provider for <b>slf4j</b> with <b>org.slf4j:slf4j-api</b> coordinates and
     * with version reference <b>slf4j</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getSlf4j() {
        return create("slf4j");
    }

    /**
     * Dependency provider for <b>xz</b> with <b>org.tukaani:xz</b> coordinates and
     * with version reference <b>xz</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getXz() {
        return create("xz");
    }

    /**
     * Group of libraries at <b>asm</b>
     */
    public AsmLibraryAccessors getAsm() {
        return laccForAsmLibraryAccessors;
    }

    /**
     * Group of libraries at <b>authlib</b>
     */
    public AuthlibLibraryAccessors getAuthlib() {
        return laccForAuthlibLibraryAccessors;
    }

    /**
     * Group of libraries at <b>commons</b>
     */
    public CommonsLibraryAccessors getCommons() {
        return laccForCommonsLibraryAccessors;
    }

    /**
     * Group of libraries at <b>jackson</b>
     */
    public JacksonLibraryAccessors getJackson() {
        return laccForJacksonLibraryAccessors;
    }

    /**
     * Group of libraries at <b>java</b>
     */
    public JavaLibraryAccessors getJava() {
        return laccForJavaLibraryAccessors;
    }

    /**
     * Group of libraries at <b>javafx</b>
     */
    public JavafxLibraryAccessors getJavafx() {
        return laccForJavafxLibraryAccessors;
    }

    /**
     * Group of libraries at <b>javax</b>
     */
    public JavaxLibraryAccessors getJavax() {
        return laccForJavaxLibraryAccessors;
    }

    /**
     * Group of libraries at <b>junit</b>
     */
    public JunitLibraryAccessors getJunit() {
        return laccForJunitLibraryAccessors;
    }

    /**
     * Group of libraries at <b>log4j</b>
     */
    public Log4jLibraryAccessors getLog4j() {
        return laccForLog4jLibraryAccessors;
    }

    /**
     * Group of libraries at <b>okhttp</b>
     */
    public OkhttpLibraryAccessors getOkhttp() {
        return laccForOkhttpLibraryAccessors;
    }

    /**
     * Group of libraries at <b>twelvemonkeys</b>
     */
    public TwelvemonkeysLibraryAccessors getTwelvemonkeys() {
        return laccForTwelvemonkeysLibraryAccessors;
    }

    /**
     * Group of versions at <b>versions</b>
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Group of bundles at <b>bundles</b>
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Group of plugins at <b>plugins</b>
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class AsmLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public AsmLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>asm</b> with <b>org.ow2.asm:asm</b> coordinates and
         * with version reference <b>asm</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("asm");
        }

        /**
         * Dependency provider for <b>commons</b> with <b>org.ow2.asm:asm-commons</b> coordinates and
         * with version reference <b>asm</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCommons() {
            return create("asm.commons");
        }

        /**
         * Dependency provider for <b>tree</b> with <b>org.ow2.asm:asm-tree</b> coordinates and
         * with version reference <b>asm</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getTree() {
            return create("asm.tree");
        }

        /**
         * Dependency provider for <b>util</b> with <b>org.ow2.asm:asm-util</b> coordinates and
         * with version reference <b>asm</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getUtil() {
            return create("asm.util");
        }

    }

    public static class AuthlibLibraryAccessors extends SubDependencyFactory {

        public AuthlibLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>injector</b> with <b>org.glavo:authlib-injector</b> coordinates and
         * with version reference <b>authlib.injector</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getInjector() {
            return create("authlib.injector");
        }

    }

    public static class CommonsLibraryAccessors extends SubDependencyFactory {

        public CommonsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>codec</b> with <b>commons-codec:commons-codec</b> coordinates and
         * with version reference <b>commons.codec</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCodec() {
            return create("commons.codec");
        }

        /**
         * Dependency provider for <b>compress</b> with <b>org.apache.commons:commons-compress</b> coordinates and
         * with version reference <b>commons.compress</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCompress() {
            return create("commons.compress");
        }

        /**
         * Dependency provider for <b>io</b> with <b>commons-io:commons-io</b> coordinates and
         * with version reference <b>commons.io</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getIo() {
            return create("commons.io");
        }

        /**
         * Dependency provider for <b>lang3</b> with <b>org.apache.commons:commons-lang3</b> coordinates and
         * with version reference <b>commons.lang3</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLang3() {
            return create("commons.lang3");
        }

        /**
         * Dependency provider for <b>logging</b> with <b>commons-logging:commons-logging</b> coordinates and
         * with version reference <b>commons.logging</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLogging() {
            return create("commons.logging");
        }

    }

    public static class JacksonLibraryAccessors extends SubDependencyFactory {
        private final JacksonDataformatLibraryAccessors laccForJacksonDataformatLibraryAccessors = new JacksonDataformatLibraryAccessors(owner);

        public JacksonLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>databind</b> with <b>com.fasterxml.jackson.core:jackson-databind</b> coordinates and
         * with version reference <b>jackson</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getDatabind() {
            return create("jackson.databind");
        }

        /**
         * Group of libraries at <b>jackson.dataformat</b>
         */
        public JacksonDataformatLibraryAccessors getDataformat() {
            return laccForJacksonDataformatLibraryAccessors;
        }

    }

    public static class JacksonDataformatLibraryAccessors extends SubDependencyFactory {

        public JacksonDataformatLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>xml</b> with <b>com.fasterxml.jackson.dataformat:jackson-dataformat-xml</b> coordinates and
         * with version reference <b>jackson</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getXml() {
            return create("jackson.dataformat.xml");
        }

    }

    public static class JavaLibraryAccessors extends SubDependencyFactory {

        public JavaLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>info</b> with <b>org.glavo:java-info</b> coordinates and
         * with version reference <b>java.info</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getInfo() {
            return create("java.info");
        }

    }

    public static class JavafxLibraryAccessors extends SubDependencyFactory {

        public JavafxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>base</b> with <b>org.openjfx:javafx-base</b> coordinates and
         * with version reference <b>javafx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getBase() {
            return create("javafx.base");
        }

        /**
         * Dependency provider for <b>controls</b> with <b>org.openjfx:javafx-controls</b> coordinates and
         * with version reference <b>javafx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getControls() {
            return create("javafx.controls");
        }

        /**
         * Dependency provider for <b>fxml</b> with <b>org.openjfx:javafx-fxml</b> coordinates and
         * with version reference <b>javafx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getFxml() {
            return create("javafx.fxml");
        }

        /**
         * Dependency provider for <b>swing</b> with <b>org.openjfx:javafx-swing</b> coordinates and
         * with version reference <b>javafx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSwing() {
            return create("javafx.swing");
        }

        /**
         * Dependency provider for <b>web</b> with <b>org.openjfx:javafx-web</b> coordinates and
         * with version reference <b>javafx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getWeb() {
            return create("javafx.web");
        }

    }

    public static class JavaxLibraryAccessors extends SubDependencyFactory {

        public JavaxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>annotations</b> with <b>javax.annotation:javax.annotation-api</b> coordinates and
         * with version reference <b>javax.annotations</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAnnotations() {
            return create("javax.annotations");
        }

    }

    public static class JunitLibraryAccessors extends SubDependencyFactory {

        public JunitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>jupiter</b> with <b>org.junit.jupiter:junit-jupiter</b> coordinates and
         * with version reference <b>junit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJupiter() {
            return create("junit.jupiter");
        }

    }

    public static class Log4jLibraryAccessors extends SubDependencyFactory {
        private final Log4jSlf4jLibraryAccessors laccForLog4jSlf4jLibraryAccessors = new Log4jSlf4jLibraryAccessors(owner);

        public Log4jLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>org.apache.logging.log4j:log4j-core</b> coordinates and
         * with version reference <b>log4j</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("log4j.core");
        }

        /**
         * Group of libraries at <b>log4j.slf4j</b>
         */
        public Log4jSlf4jLibraryAccessors getSlf4j() {
            return laccForLog4jSlf4jLibraryAccessors;
        }

    }

    public static class Log4jSlf4jLibraryAccessors extends SubDependencyFactory {

        public Log4jSlf4jLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>impl</b> with <b>org.apache.logging.log4j:log4j-slf4j-impl</b> coordinates and
         * with version reference <b>log4j</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getImpl() {
            return create("log4j.slf4j.impl");
        }

    }

    public static class OkhttpLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {
        private final OkhttpLoggingLibraryAccessors laccForOkhttpLoggingLibraryAccessors = new OkhttpLoggingLibraryAccessors(owner);

        public OkhttpLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>okhttp</b> with <b>com.squareup.okhttp3:okhttp</b> coordinates and
         * with version reference <b>okhttp</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("okhttp");
        }

        /**
         * Group of libraries at <b>okhttp.logging</b>
         */
        public OkhttpLoggingLibraryAccessors getLogging() {
            return laccForOkhttpLoggingLibraryAccessors;
        }

    }

    public static class OkhttpLoggingLibraryAccessors extends SubDependencyFactory {

        public OkhttpLoggingLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>interceptor</b> with <b>com.squareup.okhttp3:logging-interceptor</b> coordinates and
         * with version reference <b>okhttp</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getInterceptor() {
            return create("okhttp.logging.interceptor");
        }

    }

    public static class TwelvemonkeysLibraryAccessors extends SubDependencyFactory {
        private final TwelvemonkeysImageioLibraryAccessors laccForTwelvemonkeysImageioLibraryAccessors = new TwelvemonkeysImageioLibraryAccessors(owner);

        public TwelvemonkeysLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>twelvemonkeys.imageio</b>
         */
        public TwelvemonkeysImageioLibraryAccessors getImageio() {
            return laccForTwelvemonkeysImageioLibraryAccessors;
        }

    }

    public static class TwelvemonkeysImageioLibraryAccessors extends SubDependencyFactory {

        public TwelvemonkeysImageioLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>webp</b> with <b>com.twelvemonkeys.imageio:imageio-webp</b> coordinates and
         * with version reference <b>twelvemonkeys</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getWebp() {
            return create("twelvemonkeys.imageio.webp");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        private final AuthlibVersionAccessors vaccForAuthlibVersionAccessors = new AuthlibVersionAccessors(providers, config);
        private final CommonsVersionAccessors vaccForCommonsVersionAccessors = new CommonsVersionAccessors(providers, config);
        private final JavaVersionAccessors vaccForJavaVersionAccessors = new JavaVersionAccessors(providers, config);
        private final JavafxVersionAccessors vaccForJavafxVersionAccessors = new JavafxVersionAccessors(providers, config);
        private final JavaxVersionAccessors vaccForJavaxVersionAccessors = new JavaxVersionAccessors(providers, config);
        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>asm</b> with value <b>9.6</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAsm() { return getVersion("asm"); }

        /**
         * Version alias <b>bcprov</b> with value <b>1.77</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getBcprov() { return getVersion("bcprov"); }

        /**
         * Version alias <b>gson</b> with value <b>2.10.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getGson() { return getVersion("gson"); }

        /**
         * Version alias <b>hmclauncher</b> with value <b>1.0.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getHmclauncher() { return getVersion("hmclauncher"); }

        /**
         * Version alias <b>httpclient</b> with value <b>4.5.14</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getHttpclient() { return getVersion("httpclient"); }

        /**
         * Version alias <b>httpcore</b> with value <b>4.4.16</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getHttpcore() { return getVersion("httpcore"); }

        /**
         * Version alias <b>httpmime</b> with value <b>4.5.14</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getHttpmime() { return getVersion("httpmime"); }

        /**
         * Version alias <b>jackson</b> with value <b>2.15.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJackson() { return getVersion("jackson"); }

        /**
         * Version alias <b>jfoenix</b> with value <b>9.0.10</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJfoenix() { return getVersion("jfoenix"); }

        /**
         * Version alias <b>junit</b> with value <b>5.10.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJunit() { return getVersion("junit"); }

        /**
         * Version alias <b>log4j</b> with value <b>2.21.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getLog4j() { return getVersion("log4j"); }

        /**
         * Version alias <b>nashorn</b> with value <b>15.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getNashorn() { return getVersion("nashorn"); }

        /**
         * Version alias <b>okhttp</b> with value <b>4.12.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getOkhttp() { return getVersion("okhttp"); }

        /**
         * Version alias <b>shadow</b> with value <b>8.1.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getShadow() { return getVersion("shadow"); }

        /**
         * Version alias <b>slf4j</b> with value <b>2.0.9</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSlf4j() { return getVersion("slf4j"); }

        /**
         * Version alias <b>twelvemonkeys</b> with value <b>3.10.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getTwelvemonkeys() { return getVersion("twelvemonkeys"); }

        /**
         * Version alias <b>xz</b> with value <b>1.9</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getXz() { return getVersion("xz"); }

        /**
         * Group of versions at <b>versions.authlib</b>
         */
        public AuthlibVersionAccessors getAuthlib() {
            return vaccForAuthlibVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.commons</b>
         */
        public CommonsVersionAccessors getCommons() {
            return vaccForCommonsVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.java</b>
         */
        public JavaVersionAccessors getJava() {
            return vaccForJavaVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.javafx</b>
         */
        public JavafxVersionAccessors getJavafx() {
            return vaccForJavafxVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.javax</b>
         */
        public JavaxVersionAccessors getJavax() {
            return vaccForJavaxVersionAccessors;
        }

    }

    public static class AuthlibVersionAccessors extends VersionFactory  {

        public AuthlibVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>authlib.injector</b> with value <b>1.2.5</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getInjector() { return getVersion("authlib.injector"); }

    }

    public static class CommonsVersionAccessors extends VersionFactory  {

        public CommonsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>commons.codec</b> with value <b>1.16.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCodec() { return getVersion("commons.codec"); }

        /**
         * Version alias <b>commons.compress</b> with value <b>1.24.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCompress() { return getVersion("commons.compress"); }

        /**
         * Version alias <b>commons.io</b> with value <b>2.15.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getIo() { return getVersion("commons.io"); }

        /**
         * Version alias <b>commons.lang3</b> with value <b>3.13.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getLang3() { return getVersion("commons.lang3"); }

        /**
         * Version alias <b>commons.logging</b> with value <b>1.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getLogging() { return getVersion("commons.logging"); }

    }

    public static class JavaVersionAccessors extends VersionFactory  {

        public JavaVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>java.info</b> with value <b>1.0.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getInfo() { return getVersion("java.info"); }

    }

    public static class JavafxVersionAccessors extends VersionFactory  implements VersionNotationSupplier {

        public JavafxVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>javafx</b> with value <b>17</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> asProvider() { return getVersion("javafx"); }

        /**
         * Version alias <b>javafx.plugin</b> with value <b>0.0.13</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getPlugin() { return getVersion("javafx.plugin"); }

    }

    public static class JavaxVersionAccessors extends VersionFactory  {

        public JavaxVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>javax.annotations</b> with value <b>1.3.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAnnotations() { return getVersion("javax.annotations"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

        /**
         * Dependency bundle provider for <b>asm</b> which contains the following dependencies:
         * <ul>
         *    <li>org.ow2.asm:asm</li>
         *    <li>org.ow2.asm:asm-commons</li>
         *    <li>org.ow2.asm:asm-tree</li>
         *    <li>org.ow2.asm:asm-util</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getAsm() {
            return createBundle("asm");
        }

        /**
         * Dependency bundle provider for <b>bouncycastle</b> which contains the following dependencies:
         * <ul>
         *    <li>org.bouncycastle:bcprov-jdk15on</li>
         *    <li>org.bouncycastle:bcpkix-jdk15on</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getBouncycastle() {
            return createBundle("bouncycastle");
        }

        /**
         * Dependency bundle provider for <b>commons</b> which contains the following dependencies:
         * <ul>
         *    <li>org.apache.commons:commons-lang3</li>
         *    <li>commons-io:commons-io</li>
         *    <li>commons-codec:commons-codec</li>
         *    <li>commons-logging:commons-logging</li>
         *    <li>org.apache.commons:commons-compress</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getCommons() {
            return createBundle("commons");
        }

        /**
         * Dependency bundle provider for <b>http</b> which contains the following dependencies:
         * <ul>
         *    <li>org.apache.httpcomponents:httpclient</li>
         *    <li>org.apache.httpcomponents:httpmime</li>
         *    <li>org.apache.httpcomponents:httpcore</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getHttp() {
            return createBundle("http");
        }

        /**
         * Dependency bundle provider for <b>jackson</b> which contains the following dependencies:
         * <ul>
         *    <li>com.fasterxml.jackson.core:jackson-databind</li>
         *    <li>com.fasterxml.jackson.dataformat:jackson-dataformat-xml</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getJackson() {
            return createBundle("jackson");
        }

        /**
         * Dependency bundle provider for <b>javafx</b> which contains the following dependencies:
         * <ul>
         *    <li>org.openjfx:javafx-base</li>
         *    <li>org.openjfx:javafx-controls</li>
         *    <li>org.openjfx:javafx-fxml</li>
         *    <li>org.openjfx:javafx-swing</li>
         *    <li>org.openjfx:javafx-web</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getJavafx() {
            return createBundle("javafx");
        }

        /**
         * Dependency bundle provider for <b>logging</b> which contains the following dependencies:
         * <ul>
         *    <li>org.slf4j:slf4j-api</li>
         *    <li>org.apache.logging.log4j:log4j-slf4j-impl</li>
         *    <li>org.apache.logging.log4j:log4j-core</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getLogging() {
            return createBundle("logging");
        }

        /**
         * Dependency bundle provider for <b>okhttp</b> which contains the following dependencies:
         * <ul>
         *    <li>com.squareup.okhttp3:okhttp</li>
         *    <li>com.squareup.okhttp3:logging-interceptor</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getOkhttp() {
            return createBundle("okhttp");
        }

    }

    public static class PluginAccessors extends PluginFactory {
        private final JavafxPluginAccessors paccForJavafxPluginAccessors = new JavafxPluginAccessors(providers, config);

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>shadow</b> with plugin id <b>com.github.johnrengelman.shadow</b> and
         * with version reference <b>shadow</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getShadow() { return createPlugin("shadow"); }

        /**
         * Group of plugins at <b>plugins.javafx</b>
         */
        public JavafxPluginAccessors getJavafx() {
            return paccForJavafxPluginAccessors;
        }

    }

    public static class JavafxPluginAccessors extends PluginFactory {

        public JavafxPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>javafx.plugin</b> with plugin id <b>org.openjfx.javafxplugin</b> and
         * with version reference <b>javafx.plugin</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getPlugin() { return createPlugin("javafx.plugin"); }

    }

}
