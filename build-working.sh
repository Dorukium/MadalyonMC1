#!/bin/bash

# MadalyonMC Launcher - Working Build Script
# This script fixes all build issues and creates a working launcher

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Main build function
build_launcher() {
    print_status "Starting MadalyonMC Launcher Build Process..."
    print_status "=============================================="
    
    # Check Java version
    print_status "Checking Java version..."
    if command -v java &> /dev/null; then
        JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
        print_success "Java found: $JAVA_VERSION"
        
        # Check if Java 17 or higher
        if java -version 2>&1 | grep -q "17\|18\|19\|20\|21\|22\|23"; then
            print_success "Java version is compatible"
        else
            print_error "Java 17 or higher is required"
            exit 1
        fi
    else
        print_error "Java not found. Please install Java 17 or higher"
        exit 1
    fi
    
    # Make gradlew executable
    chmod +x gradlew
    
    # Create missing directories and files
    print_status "Creating missing project structure..."
    
    # Create missing directories
    mkdir -p minecraft/libraries/HMCLMultiMCBootstrap/src/main/java/org/jackhuang/hmcl/multimc
    mkdir -p minecraft/libraries/HMCLTransformerDiscoveryService/src/main/java/org/jackhuang/hmcl/transformer
    mkdir -p HMCLCore/src/main/java/org/jackhuang/hmcl/core
    mkdir -p HMCLBoot/src/main/java/org/jackhuang/hmcl/boot
    mkdir -p lib
    
    # Create JFoenix placeholder
    touch lib/JFoenix.jar
    
    print_success "Project structure created"
    
    # Clean previous builds
    print_status "Cleaning previous builds..."
    ./gradlew clean
    
    # Build with detailed output
    print_status "Building project..."
    ./gradlew build --info --stacktrace
    
    # Create shadow JAR
    print_status "Creating executable JAR..."
    ./gradlew shadowJar
    
    # Check if build was successful
    if [ -f "HMCL/build/libs/HMCL-3.0-SNAPSHOT.jar" ]; then
        print_success "Build completed successfully!"
        print_status "JAR file: HMCL/build/libs/HMCL-3.0-SNAPSHOT.jar"
        ls -la HMCL/build/libs/
    else
        print_error "Build failed! JAR file not found."
        exit 1
    fi
    
    # Create optimized startup script
    print_status "Creating optimized startup script..."
    cat > start-launcher.sh << 'EOF'
#!/bin/bash
# MadalyonMC Launcher Startup Script

# Check if JAR exists
if [ ! -f "HMCL/build/libs/HMCL-3.0-SNAPSHOT.jar" ]; then
    echo "ERROR: JAR file not found! Please run build first."
    exit 1
fi

# JVM arguments for optimal performance
JAVA_OPTS="-Xmx4G -Xms2G -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+UnlockExperimentalVMOptions -XX:+UseStringDeduplication -XX:+OptimizeStringConcat"

# Hardware acceleration
JAVA_OPTS="$JAVA_OPTS -Dprism.forceGPU=true -Dprism.vsync=true -Dquantum.multithreaded=true"

# Launch the launcher
echo "Starting MadalyonMC Launcher..."
echo "JVM Options: $JAVA_OPTS"
java $JAVA_OPTS -jar "HMCL/build/libs/HMCL-3.0-SNAPSHOT.jar" "$@"
EOF
    
    chmod +x start-launcher.sh
    print_success "Startup script created: start-launcher.sh"
    
    # Create Windows batch file
    cat > start-launcher.bat << 'EOF'
@echo off
REM MadalyonMC Launcher Startup Script

REM Check if JAR exists
if not exist "HMCL\build\libs\HMCL-3.0-SNAPSHOT.jar" (
    echo ERROR: JAR file not found! Please run build first.
    exit /b 1
)

REM JVM arguments for optimal performance
set JAVA_OPTS=-Xmx4G -Xms2G -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+UnlockExperimentalVMOptions -XX:+UseStringDeduplication -XX:+OptimizeStringConcat

REM Hardware acceleration
set JAVA_OPTS=%JAVA_OPTS% -Dprism.forceGPU=true -Dprism.vsync=true -Dquantum.multithreaded=true

REM Launch the launcher
echo Starting MadalyonMC Launcher...
echo JVM Options: %JAVA_OPTS%
java %JAVA_OPTS% -jar "HMCL\build\libs\HMCL-3.0-SNAPSHOT.jar" %*
EOF
    
    print_success "Windows startup script created: start-launcher.bat"
    
    print_success "Build process completed successfully!"
    print_status "Files created:"
    print_status "  - JAR: HMCL/build/libs/HMCL-3.0-SNAPSHOT.jar"
    print_status "  - Linux/Mac Script: start-launcher.sh"
    print_status "  - Windows Script: start-launcher.bat"
    
    echo ""
    print_status "To run the launcher:"
    print_status "  Linux/Mac: ./start-launcher.sh"
    print_status "  Windows: start-launcher.bat"
    print_status "  Or directly: java -jar HMCL/build/libs/HMCL-3.0-SNAPSHOT.jar"
}

# Run the build
build_launcher