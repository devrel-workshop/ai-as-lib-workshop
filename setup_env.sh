# Get the attendee configuration parameters
if ! [ -f "./attendee-conf.json" ];then
  curl -u attendee http://162.19.64.158/workshop-ai-endpoints/configurations -o attendee-conf.json
  #curl -u attendee http://localhost:8080/workshop-ai-endpoints/configurations -o attendee-conf.json
fi

echo "Downloaded parameters:"
cat attendee-conf.json|jq

# Call script to create environment variables
source ./bin/set-env-variables.sh

echo "Your environment variables:"
echo "   - OVH_AI_ENDPOINTS_ACCESS_TOKEN: " $OVH_AI_ENDPOINTS_ACCESS_TOKEN
echo "   - OVH_AI_ENDPOINTS_MODEL_URL: " $OVH_AI_ENDPOINTS_MODEL_URL
echo "   - OVH_AI_ENDPOINTS_MODEL_NAME: " $OVH_AI_ENDPOINTS_MODEL_NAME
echo "   - OVH_AI_ENDPOINTS_EMBEDDING_MODEL: " $OVH_AI_ENDPOINTS_EMBEDDING_MODEL
echo "   - OVH_AI_ENDPOINTS_SD_URL: " $OVH_AI_ENDPOINTS_SD_URL
echo "   - MCP_SERVER_URL: " $MCP_SERVER_URL

# create and activate a python venv
python3 -m venv .venv
source .venv/bin/activate

# copy VSCode snippets to activate them
cp -r .vscode ../.vscode
