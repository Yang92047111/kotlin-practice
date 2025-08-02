#!/bin/bash

# GitHub Actions Workflow Checker
# This script validates GitHub Actions workflow files

set -e

echo "🔍 Checking GitHub Actions Workflows..."
echo "=================================="

WORKFLOW_DIR=".github/workflows"
ERRORS=0

if [ ! -d "$WORKFLOW_DIR" ]; then
    echo "❌ Workflow directory not found: $WORKFLOW_DIR"
    exit 1
fi

echo "📁 Found workflow directory: $WORKFLOW_DIR"
echo ""

# Check each workflow file
for workflow in "$WORKFLOW_DIR"/*.yml; do
    if [ -f "$workflow" ]; then
        filename=$(basename "$workflow")
        echo "🔍 Checking: $filename"
        
        # Basic YAML syntax check
        if command -v yamllint >/dev/null 2>&1; then
            if yamllint "$workflow" >/dev/null 2>&1; then
                echo "  ✅ YAML syntax: OK"
            else
                echo "  ❌ YAML syntax: FAILED"
                ERRORS=$((ERRORS + 1))
            fi
        else
            echo "  ⚠️  yamllint not available, skipping syntax check"
        fi
        
        # Check for required fields
        if grep -q "^name:" "$workflow"; then
            echo "  ✅ Has name field"
        else
            echo "  ❌ Missing name field"
            ERRORS=$((ERRORS + 1))
        fi
        
        if grep -q "^on:" "$workflow"; then
            echo "  ✅ Has trigger configuration"
        else
            echo "  ❌ Missing trigger configuration"
            ERRORS=$((ERRORS + 1))
        fi
        
        if grep -q "^jobs:" "$workflow"; then
            echo "  ✅ Has jobs configuration"
        else
            echo "  ❌ Missing jobs configuration"
            ERRORS=$((ERRORS + 1))
        fi
        
        # Check for common issues
        if grep -q "uses: actions/checkout@v4" "$workflow"; then
            echo "  ✅ Uses latest checkout action"
        elif grep -q "uses: actions/checkout@" "$workflow"; then
            echo "  ⚠️  Uses older checkout action version"
        fi
        
        if grep -q "uses: actions/setup-java@v4" "$workflow"; then
            echo "  ✅ Uses latest setup-java action"
        elif grep -q "uses: actions/setup-java@" "$workflow"; then
            echo "  ⚠️  Uses older setup-java action version"
        fi
        
        echo ""
    fi
done

# Summary
echo "=================================="
if [ $ERRORS -eq 0 ]; then
    echo "✅ All workflow files passed validation!"
    echo ""
    echo "📋 Available workflows:"
    for workflow in "$WORKFLOW_DIR"/*.yml; do
        if [ -f "$workflow" ]; then
            filename=$(basename "$workflow" .yml)
            name=$(grep "^name:" "$workflow" | sed 's/name: *//' | tr -d '"')
            echo "  • $filename: $name"
        fi
    done
    echo ""
    echo "🚀 To trigger workflows:"
    echo "  • Push to main/master/develop branch (triggers CI)"
    echo "  • Create a pull request (triggers PR check)"
    echo "  • Create a git tag starting with 'v' (triggers release)"
    echo "  • Use GitHub Actions UI for manual triggers"
else
    echo "❌ Found $ERRORS error(s) in workflow files"
    exit 1
fi