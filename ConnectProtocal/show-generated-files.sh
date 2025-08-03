#!/bin/bash

# Script to show generated gRPC files and their structure

echo "📁 Generated gRPC Files Structure"
echo "=================================="
echo ""

if [ ! -d "target/generated-sources" ]; then
    echo "❌ No generated sources found. Please run 'make build-protocol' first."
    exit 1
fi

echo "🔍 Generated Protobuf Java Classes:"
echo "-----------------------------------"
if [ -d "target/generated-sources/protobuf/java" ]; then
    find target/generated-sources/protobuf/java -name "*.java" | while read file; do
        echo "📄 $file"
        echo "   Size: $(wc -l < "$file") lines"
        echo "   Contains: $(grep -c "class\|interface\|enum" "$file") types"
        echo ""
    done
else
    echo "❌ No Java protobuf files found"
fi

echo "🔍 Generated gRPC Service Stubs:"
echo "--------------------------------"
if [ -d "target/generated-sources/protobuf/grpc-java" ]; then
    find target/generated-sources/protobuf/grpc-java -name "*.java" | while read file; do
        echo "📄 $file"
        echo "   Size: $(wc -l < "$file") lines"
        echo "   Service methods: $(grep -c "public.*Stub" "$file") stubs"
        echo ""
    done
else
    echo "❌ No gRPC service files found"
fi

echo "📊 Summary:"
echo "----------"
echo "Total Java files: $(find target/generated-sources -name "*.java" | wc -l)"
echo "Total lines of generated code: $(find target/generated-sources -name "*.java" -exec wc -l {} + | tail -1 | awk '{print $1}')"
echo ""

echo "🎯 Key Generated Components:"
echo "---------------------------"
echo "• UserProto.java - Protocol buffer message definitions"
echo "• UserServiceGrpc.java - gRPC service stubs and interfaces"
echo "• All message builders and serialization code"
echo "• Type-safe method signatures for all RPC calls"
echo ""

echo "✅ Generated files ready for use in gRPC clients and servers!"