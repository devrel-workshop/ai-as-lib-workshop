#!/bin/bash

# 🛠️ Configurations 🛠️
source ../../../bin/set-env-variables.sh

# Run Java class giving as parameter
echo "☕️ Running Java class: $1" 🚀 using models $OVH_AI_ENDPOINTS_WHISPER_MODEL / $OVH_AI_ENDPOINTS_TTS_MODEL 🧠
echo ""
read -n 1 -p "Press any key to continue"
echo ""

javelit run $1