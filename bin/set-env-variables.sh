#!/bin/bash

# 🛠️ Get the attendee configuration parameters 🛠️
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
if [ "$(basename $SCRIPT_DIR)" = "ai-as-lib-workshop" ]; then
    ATTENDEE_CONF_PATH="./attendee-conf.json"
  else
    ATTENDEE_CONF_PATH="$SCRIPT_DIR/../attendee-conf.json"
fi

# Always read from the JSON when the file exists
if [ -f "$ATTENDEE_CONF_PATH" ]; then
  echo "🛠️ Set environment variables 🛠️"
      export OVH_AI_ENDPOINTS_MODEL_URL=https://mistral-7b-instruct-v0-3.endpoints.kepler.ai.cloud.ovh.net/api/openai_compat/v1
      export OVH_AI_ENDPOINTS_MODEL_NAME=Mistral-7B-Instruct-v0.3
      export OVH_AI_ENDPOINTS_EMBEDDING_MODEL=https://bge-multilingual-gemma2.endpoints.kepler.ai.cloud.ovh.net
      export OVH_AI_ENDPOINTS_SD_URL=https://stable-diffusion-xl.endpoints.kepler.ai.cloud.ovh.net/api/text2image
      export OVH_AI_ENDPOINTS_WHISPER_URL=https://whisper-large-v3.endpoints.kepler.ai.cloud.ovh.net/api/openai_compat/v1
      export OVH_AI_ENDPOINTS_WHISPER_MODEL=whisper-large-v3
      export OVH_AI_ENDPOINTS_TTS_MODEL=https://nvr-tts-es-es.endpoints.kepler.ai.cloud.ovh.net/api/v1/tts/text_to_audio
      export MCP_SERVER_URL=http://localhost:8080/mcp/
      export OVH_AI_ENDPOINTS_ACCESS_TOKEN=$(jq -r '.aiEndpointsToken' "$ATTENDEE_CONF_PATH")
else
  echo "❌ No attendee-conf.json found, please run source ./setup_env.sh in the ai-as-lib-workshop folder ❌"
fi
