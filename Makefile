# Kotlin Practice Projects Makefile
# This Makefile provides convenient commands to build, test, and run all modules

.PHONY: help clean build test run-all run-coroutines run-oracle run-testcontainers install package

# Default target
help:
	@echo "Kotlin Practice Projects - Available Commands:"
	@echo ""
	@echo "Build Commands:"
	@echo "  make build          - Build all modules"
	@echo "  make clean          - Clean all modules"
	@echo "  make install        - Install dependencies for all modules"
	@echo "  make package        - Package all modules"
	@echo ""
	@echo "Test Commands:"
	@echo "  make test           - Run all unit tests"
	@echo "  make test-coroutines - Run withcontext-coroutines tests"
	@echo "  make test-oracle    - Run OracleTrigger tests"
	@echo "  make test-testcontainers - Run Testcontainers tests"
	@echo "  make test-protocol  - Run ConnectProtocal tests"
	@echo ""
	@echo "Run Commands:"
	@echo "  make run-coroutines - Run the coroutines demo"
	@echo "  make run-oracle     - Run the Oracle CRUD API (requires Oracle DB)"
	@echo "  make run-testcontainers - Run the Testcontainers Notes API"
	@echo "  make run-protocol-demo - Run ConnectProtocal interactive demo"
	@echo "  make build-protocol - Build ConnectProtocal and generate gRPC stubs"
	@echo "  make show-generated - Show generated gRPC files structure"
	@echo ""
	@echo "Development Commands:"
	@echo "  make dev-setup      - Set up development environment"
	@echo "  make lint           - Run code quality checks"
	@echo "  make format         - Format code (if formatter is available)"
	@echo "  make check-workflows - Validate GitHub Actions workflows"
	@echo "  make setup-github-actions - Setup local GitHub Actions testing"
	@echo ""
	@echo "Demo Commands:"
	@echo "  make demo           - Run all available demos"
	@echo "  make demo-protocol  - Run ConnectProtocal protocol demo"
	@echo "  make demo-quick     - Quick demo of all modules"
	@echo ""

# Build all modules
build:
	@echo "🔨 Building all modules..."
	mvn clean compile

# Clean all modules
clean:
	@echo "🧹 Cleaning all modules..."
	mvn clean

# Install dependencies
install:
	@echo "📦 Installing dependencies for all modules..."
	mvn clean install -DskipTests

# Package all modules
package:
	@echo "📦 Packaging all modules..."
	mvn clean package

# Run all tests
test:
	@echo "🧪 Running all unit tests..."
	MAVEN_OPTS="-Dnet.bytebuddy.experimental=true -XX:+EnableDynamicAgentLoading" mvn test

# Test individual modules
test-coroutines:
	@echo "🧪 Running withcontext-coroutines tests..."
	cd withcontext-coroutines && MAVEN_OPTS="-Dnet.bytebuddy.experimental=true -XX:+EnableDynamicAgentLoading" mvn test

test-oracle:
	@echo "🧪 Running OracleTrigger tests..."
	cd OracleTrigger && MAVEN_OPTS="-Dnet.bytebuddy.experimental=true -XX:+EnableDynamicAgentLoading" mvn test

test-testcontainers:
	@echo "🧪 Running Testcontainers tests..."
	cd Testcontainers && MAVEN_OPTS="-Dnet.bytebuddy.experimental=true -XX:+EnableDynamicAgentLoading" mvn test

test-protocol:
	@echo "🧪 Running ConnectProtocal tests..."
	cd ConnectProtocal && MAVEN_OPTS="-Dnet.bytebuddy.experimental=true -XX:+EnableDynamicAgentLoading" mvn test

# Run individual modules
run-coroutines:
	@echo "🚀 Running Kotlin Coroutines Demo..."
	@echo "Expected output: Thread switching demonstration"
	cd withcontext-coroutines && mvn exec:java -Dexec.mainClass=WithContextDemoKt

run-oracle:
	@echo "🚀 Starting Oracle CRUD API..."
	@echo "⚠️  Make sure Oracle Database ismake running and configured in application.properties"
	@echo "🌐 API will be available at: http://localhost:8080/api/users"
	cd OracleTrigger && mvn spring-boot:run

run-testcontainers:
	@echo "🚀 Starting Testcontainers Notes API..."
	@echo "🐳 Docker containers will be managed automatically"
	@echo "🌐 API will be available at: http://localhost:8080/api/notes"
	cd Testcontainers && mvn spring-boot:run

# Build ConnectProtocal and generate gRPC stubs
build-protocol:
	@echo "📦 Building ConnectProtocal module..."
	@echo "🔧 Generating gRPC stubs from protobuf definitions..."
	cd ConnectProtocal && mvn clean compile protobuf:compile protobuf:compile-custom
	@echo "✅ ConnectProtocal build complete with generated gRPC stubs"

# Run ConnectProtocal interactive demo
run-protocol-demo:
	@echo "🎬 Starting ConnectProtocal Interactive Demo..."
	@echo "This demo showcases multi-protocol API contracts:"
	@echo "• HTTP/REST DTOs with validation"
	@echo "• WebSocket events with real-time messaging"
	@echo "• AMQP messages for pub/sub patterns"
	@echo "• gRPC services with streaming support"
	@echo "• Cross-protocol data consistency"
	@echo ""
	@make build-protocol
	@echo ""
	cd ConnectProtocal && ./run-demo.sh

# Development setup
dev-setup:
	@echo "🛠️  Setting up development environment..."
	@echo "Checking Java version..."
	@java -version
	@echo ""
	@echo "Checking Maven version..."
	@mvn -version
	@echo ""
	@echo "Checking Docker (for Testcontainers)..."
	@docker --version || echo "⚠️  Docker not found - Testcontainers module may not work"
	@echo ""
	@echo "Installing all dependencies..."
	@make install
	@echo "✅ Development environment setup complete!"

# Code quality checks (basic)
lint:
	@echo "🔍 Running basic code quality checks..."
	@echo "Checking for compilation errors..."
	mvn compile
	@echo "✅ Basic lint checks complete"

# Format code (placeholder - would need ktlint or similar)
format:
	@echo "💅 Code formatting..."
	@echo "⚠️  No formatter configured. Consider adding ktlint or similar."
	@echo "You can add ktlint with: mvn com.github.gantsign.maven:ktlint-maven-plugin:format"

# Quick development cycle
dev: clean build test
	@echo "🔄 Development cycle complete: clean → build → test"

# Full CI/CD simulation
ci: clean install test package
	@echo "🚀 CI/CD pipeline complete: clean → install → test → package"

# Show project structure
structure:
	@echo "📁 Project Structure:"
	@echo ""
	@tree -I 'target|.git|.idea|*.iml' -L 3 || find . -type d -name target -prune -o -type d -name .git -prune -o -type d -name .idea -prune -o -type f -name "*.iml" -prune -o -type d -print | head -20

# Show module information
info:
	@echo "ℹ️  Module Information:"
	@echo ""
	@echo "📦 withcontext-coroutines:"
	@echo "   Purpose: Demonstrates Kotlin Coroutines with withContext"
	@echo "   Main class: WithContextDemoKt"
	@echo "   Test command: make test-coroutines"
	@echo "   Run command: make run-coroutines"
	@echo ""
	@echo "📦 OracleTrigger:"
	@echo "   Purpose: Oracle Database CRUD API with triggers"
	@echo "   Framework: Spring Boot"
	@echo "   Database: Oracle"
	@echo "   Test command: make test-oracle"
	@echo "   Run command: make run-oracle"
	@echo ""
	@echo "📦 Testcontainers:"
	@echo "   Purpose: Notes CRUD API with Testcontainers integration"
	@echo "   Framework: Spring Boot"
	@echo "   Database: MySQL (via Testcontainers)"
	@echo "   Test command: make test-testcontainers"
	@echo "   Run command: make run-testcontainers"
	@echo ""
	@echo "📦 ConnectProtocal:"
	@echo "   Purpose: Multi-protocol API contracts (HTTP, WebSocket, AMQP, gRPC)"
	@echo "   Protocols: REST DTOs, WebSocket events, AMQP messages, gRPC services"
	@echo "   Test command: make test-protocol"
	@echo "   Build command: make build-protocol"
	@echo "   Demo command: make run-protocol-demo"

# Health check for all modules
health:
	@echo "🏥 Health check for all modules..."
	@echo ""
	@echo "Checking withcontext-coroutines..."
	@cd withcontext-coroutines && mvn compile -q && echo "✅ withcontext-coroutines: OK" || echo "❌ withcontext-coroutines: FAILED"
	@echo ""
	@echo "Checking OracleTrigger..."
	@cd OracleTrigger && mvn compile -q && echo "✅ OracleTrigger: OK" || echo "❌ OracleTrigger: FAILED"
	@echo ""
	@echo "Checking Testcontainers..."
	@cd Testcontainers && mvn compile -q && echo "✅ Testcontainers: OK" || echo "❌ Testcontainers: FAILED"
	@echo ""
	@echo "Checking ConnectProtocal..."
	@cd ConnectProtocal && mvn compile -q && echo "✅ ConnectProtocal: OK" || echo "❌ ConnectProtocal: FAILED"

# Watch for changes (requires entr or similar)
watch:
	@echo "👀 Watching for changes... (requires 'entr' to be installed)"
	@echo "Install entr: brew install entr (macOS) or apt-get install entr (Ubuntu)"
	@find . -name "*.kt" -o -name "*.xml" | entr -c make build test

# Generate test reports
test-report:
	@echo "📊 Generating test reports..."
	mvn surefire-report:report
	@echo "Test reports generated in target/site/surefire-report.html for each module"

# Clean and rebuild everything
rebuild: clean build
	@echo "🔄 Complete rebuild finished"

# Show Maven dependency tree
deps:
	@echo "🌳 Maven dependency tree:"
	mvn dependency:tree

# Show outdated dependencies
deps-check:
	@echo "🔍 Checking for outdated dependencies..."
	mvn versions:display-dependency-updates

# Check GitHub Actions workflows
check-workflows:
	@echo "🔍 Validating GitHub Actions workflows..."
	@./scripts/check-workflows.sh

# Setup GitHub Actions locally (install act if available)
setup-github-actions:
	@echo "🔧 Setting up GitHub Actions local testing..."
	@if command -v act >/dev/null 2>&1; then \
		echo "✅ act is already installed"; \
		act --list; \
	else \
		echo "📦 Installing act (GitHub Actions local runner)..."; \
		echo "Visit: https://github.com/nektos/act#installation"; \
		echo "Or run: brew install act (macOS)"; \
	fi

# Demo Commands
demo: demo-quick demo-protocol
	@echo "🎉 All demos completed!"

demo-quick:
	@echo "⚡ Quick Demo of All Modules"
	@echo "============================"
	@echo ""
	@echo "📦 Module Overview:"
	@make info
	@echo ""
	@echo "🏥 Health Check:"
	@make health
	@echo ""
	@echo "✅ Quick demo completed!"

demo-protocol: run-protocol-demo

# Show generated gRPC files
show-generated:
	@echo "📁 Showing generated gRPC files structure..."
	cd ConnectProtocal && ./show-generated-files.sh

# Interactive demo menu
demo-interactive:
	@echo "🎯 Interactive Demo Menu"
	@echo "========================"
	@echo ""
	@echo "Available demos:"
	@echo "1. ConnectProtocal Multi-Protocol Demo"
	@echo "2. Kotlin Coroutines Demo"
	@echo "3. Quick Module Overview"
	@echo "4. Health Check All Modules"
	@echo ""
	@read -p "Select demo (1-4): " choice; \
	case $$choice in \
		1) make run-protocol-demo ;; \
		2) make run-coroutines ;; \
		3) make demo-quick ;; \
		4) make health ;; \
		*) echo "Invalid choice" ;; \
	esac

# Show demo information
demo-info:
	@echo "🎬 Available Demos"
	@echo "=================="
	@echo ""
	@echo "🔄 ConnectProtocal Multi-Protocol Demo:"
	@echo "   Command: make run-protocol-demo"
	@echo "   Shows: HTTP, WebSocket, AMQP, gRPC protocols"
	@echo "   Duration: ~2 minutes"
	@echo ""
	@echo "⚡ Kotlin Coroutines Demo:"
	@echo "   Command: make run-coroutines"
	@echo "   Shows: Context switching with withContext"
	@echo "   Duration: ~30 seconds"
	@echo ""
	@echo "📊 Quick Module Overview:"
	@echo "   Command: make demo-quick"
	@echo "   Shows: All modules info and health check"
	@echo "   Duration: ~1 minute"
	@echo ""
	@echo "🎯 Interactive Menu:"
	@echo "   Command: make demo-interactive"
	@echo "   Shows: Interactive demo selection"