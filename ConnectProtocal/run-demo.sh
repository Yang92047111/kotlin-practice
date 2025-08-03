#!/bin/bash

# ConnectProtocal Demo Runner Script
# This script demonstrates the multi-protocol capabilities of the ConnectProtocal module

set -e

echo "🚀 ConnectProtocal Demo Runner"
echo "=============================="
echo ""

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed or not in PATH"
    echo "Please install Maven to run this demo"
    exit 1
fi

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed or not in PATH"
    echo "Please install Java 17+ to run this demo"
    exit 1
fi

echo "🔧 Environment Check:"
echo "Java Version:"
java -version 2>&1 | head -1
echo ""
echo "Maven Version:"
mvn -version 2>&1 | head -1
echo ""

# Verify build artifacts exist
if [ ! -d "target/classes" ]; then
    echo "❌ Build artifacts not found. Please run 'make build-protocol' first."
    exit 1
fi

echo "✅ Build artifacts verified!"
echo ""

# Run the demo
echo "🎬 Starting Protocol Demo..."
echo ""

# Set up classpath
CLASSPATH="target/classes"
CLASSPATH="$CLASSPATH:target/generated-sources/protobuf/java"
CLASSPATH="$CLASSPATH:target/generated-sources/protobuf/grpc-java"

# Add Maven dependencies to classpath
for jar in $(mvn dependency:build-classpath -q -Dmdep.outputFile=/dev/stdout); do
    CLASSPATH="$CLASSPATH:$jar"
done

# Run the demo
java -cp "$CLASSPATH" com.example.spec.demo.ProtocolDemoKt

echo ""
echo "🎯 Demo Features Showcased:"
echo "• HTTP/REST DTOs with validation"
echo "• WebSocket events with polymorphism"
echo "• AMQP messages with routing"
echo "• gRPC service definitions"
echo "• Cross-protocol data consistency"
echo ""
echo "📚 For more examples, see:"
echo "• USAGE_EXAMPLES.md - Detailed code examples"
echo "• README.md - Module documentation"
echo "• src/test/ - Comprehensive unit tests"
echo ""
echo "✅ Demo completed successfully!"