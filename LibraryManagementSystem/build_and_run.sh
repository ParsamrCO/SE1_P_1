#!/bin/bash

# Library Management System - Build and Run Script
# Author: Library Management System
# Version: 1.0

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Print colored output
print_info() {
    echo -e "${BLUE}ℹ️  $1${NC}"
}

print_success() {
    echo -e "${GREEN}✅ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

print_error() {
    echo -e "${RED}❌ $1${NC}"
}

# Check if Java is installed
check_java() {
    if ! command -v java &> /dev/null; then
        print_error "Java is not installed or not in PATH"
        echo "Please install Java JDK 8 or higher"
        exit 1
    fi

    if ! command -v javac &> /dev/null; then
        print_error "Java compiler (javac) is not installed"
        echo "Please install Java JDK 8 or higher"
        exit 1
    fi

    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d '"' -f 2)
    print_success "Java version: $JAVA_VERSION"
}

# Clean previous build
clean_build() {
    if [ -d "bin" ]; then
        print_info "Cleaning previous build..."
        rm -rf bin/*
    else
        mkdir -p bin
    fi
}

# Find all Java files and compile
compile_project() {
    print_info "Finding Java source files..."
    
    # Create sources list
    find src -name "*.java" > sources.txt 2>/dev/null
    
    if [ ! -s "sources.txt" ]; then
        print_error "No Java source files found in src directory"
        rm -f sources.txt
        exit 1
    fi

    SOURCE_COUNT=$(wc -l < sources.txt)
    print_info "Found $SOURCE_COUNT Java source files"

    print_info "Compiling project..."
    javac -d bin @sources.txt

    if [ $? -eq 0 ]; then
        print_success "Compilation completed successfully!"
    else
        print_error "Compilation failed!"
        rm -f sources.txt
        exit 1
    fi

    # Clean up
    rm -f sources.txt
}

# Check if main class exists
check_main_class() {
    if [ ! -f "bin/Main.class" ]; then
        print_error "Main class not found. Compilation may have failed."
        exit 1
    fi
}

# Run the application
run_application() {
    print_info "Starting Library Management System..."
    echo -e "${BLUE}========================================${NC}"
    java -cp bin Main
    EXIT_CODE=$?
    echo -e "${BLUE}========================================${NC}"
    
    if [ $EXIT_CODE -eq 0 ]; then
        print_success "Application exited successfully"
    else
        print_error "Application exited with code: $EXIT_CODE"
    fi
}

# Build only (without running)
build_only() {
    print_info "Building project only..."
    check_java
    clean_build
    compile_project
    check_main_class
    print_success "Build completed. Run with: java -cp bin Main"
}

# Main function
main() {
    echo -e "${BLUE}📚 Library Management System Build Script${NC}"
    echo -e "${BLUE}========================================${NC}"
    
    # Parse command line arguments
    case "${1:-run}" in
        "run")
            check_java
            clean_build
            compile_project
            check_main_class
            run_application
            ;;
        "build")
            build_only
            ;;
        "clean")
            print_info "Cleaning build directory..."
            rm -rf bin
            print_success "Clean completed"
            ;;
        "help"|"-h"|"--help")
            echo "Usage: $0 [command]"
            echo ""
            echo "Commands:"
            echo "  run     - Build and run the application (default)"
            echo "  build   - Build the project without running"
            echo "  clean   - Clean the build directory"
            echo "  help    - Show this help message"
            ;;
        *)
            print_error "Unknown command: $1"
            echo "Use '$0 help' for usage information"
            exit 1
            ;;
    esac
}

# Run main function with all arguments
main "$@"
