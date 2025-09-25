#!/bin/bash

# 🛠️ Configurations 🛠️
source ../../../../bin/set-env-variables.sh

# Run Quarkus in dev mode
echo "⚡️ Running Quarkus in dev mode ⚡️"
echo ""
read -n 1 -p "Press any key to continue"
echo ""

quarkus dev