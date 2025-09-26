#!/bin/bash

# 🛠️ Configurations 🛠️
source ../../../bin/set-env-variables.sh
source ../../../.venv/bin/activate

clear

# Run script giving as parameter
echo "🐍 Running python script: $1" 🚀 using models $OVH_AI_ENDPOINTS_WHISPER_MODEL / $OVH_AI_ENDPOINTS_TTS_MODEL 🧠
echo ""
read -n 1 -p "Press any key to continue"
echo ""

python $1