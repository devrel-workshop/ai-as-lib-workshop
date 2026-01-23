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
    echo "🚀 Run Javelit application from local computer"
    javelit run $1
else
    echo "🚀 Run Javelit application from CDE"
    # Get the VSCode local base path for when using Coder

    # 🚀 Run Javelit application with a base path
    #echo "javelit run $1 --base-path=/@$CODER_WORKSPACE_OWNER_NAME/$CODER_WORKSPACE_NAME.main/apps/code-server/proxy/8080"
    echo "###########################################################"
    echo "### 🔗 External URL: $CODER_AGENT_URL/@$CODER_WORKSPACE_OWNER_NAME/$CODER_WORKSPACE_NAME.main/apps/code-server/proxy/8080"
    echo "###########################################################"
    javelit run $1 --base-path=/@$CODER_WORKSPACE_OWNER_NAME/$CODER_WORKSPACE_NAME.main/apps/code-server/proxy/8080
fi
