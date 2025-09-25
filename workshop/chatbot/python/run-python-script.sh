#!/bin/bash

# 🛠️ Configurations 🛠️
source ../../../bin/set-env-variables.sh
source ../../../.venv/bin/activate

# Run script giving as parameter
echo "🐍 Running python script: $1" 🚀 using model $OVH_AI_ENDPOINTS_MODEL_NAME 🧠
echo ""
read -n 1 -p "Press any key to continue"
echo ""

python $1