#!/bin/bash

# ==========================================
# Infrastructure Shutdown Script
# ==========================================
# This script stops all infrastructure services
# ==========================================

set -e

echo "🛑 Stopping Microservices Infrastructure..."
echo ""

# Check if docker-compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "❌ docker-compose is not installed"
    exit 1
fi

# Ask user if they want to remove volumes
echo "⚠️  Do you want to remove data volumes? (This will DELETE all data)"
echo "   1) No - Keep data (default)"
echo "   2) Yes - Remove all data"
echo ""
read -p "Enter choice [1]: " REMOVE_VOLUMES
REMOVE_VOLUMES=${REMOVE_VOLUMES:-1}

if [ "$REMOVE_VOLUMES" == "2" ]; then
    echo ""
    echo "🗑️  Stopping services and removing volumes..."
    docker-compose down -v
    echo "✅ Services stopped and volumes removed"
else
    echo ""
    echo "📦 Stopping services (data will be preserved)..."
    docker-compose down
    echo "✅ Services stopped (data volumes preserved)"
fi

echo ""
echo "💡 To start services again, run:"
echo "   ./start-infrastructure.sh"
echo "   or"
echo "   docker-compose up -d"
echo ""
