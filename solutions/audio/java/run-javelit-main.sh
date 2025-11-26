#!/bin/bash

# 🛠️ Configurations 🛠️
source ../../../bin/set-env-variables.sh

# Run Java class giving as parameter
echo "☕️ Running Java class: $1" 🚀 using models $OVH_AI_ENDPOINTS_WHISPER_MODEL / $OVH_AI_ENDPOINTS_TTS_MODEL 🧠
echo ""
read -n 1 -p "Press any key to continue"
echo ""

if [ -z "$VSCODE_PROXY_URI" ]; then
    # 🚀 Run Javelit application
    javelit run $1
else
    # Get the VSCode local base path for when using Coder
    CODER_BASE_PATH=${VSCODE_PROXY_URI#https://workshop.labdevrel.ovh}
    CODER_BASE_PATH=${CODER_BASE_PATH%%proxy/*}proxy

    # 🚀 Run Javelit application with a base path
    javelit run $1 --base-path=$CODER_BASE_PATH/8080/
fi
