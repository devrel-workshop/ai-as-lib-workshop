#!/bin/bash

# 🛠️ Configurations 🛠️
source ../../../bin/set-env-variables.sh
source ../../../.venv/bin/activate

# Run script giving as parameter
echo "🐍 Running python script: $1" 🚀 using models $OVH_AI_ENDPOINT_MODEL_EMOTION_URL / $OVH_AI_ENDPOINT_MODEL_SENTIMENT_URL / $OVH_AI_ENDPOINT_MODEL_CLASSIFICATION_URL 🧠
echo ""
read -n 1 -p "Press any key to continue"
echo ""

python $1