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

# Check AI Endpoints token
if [ -z "$OVH_AI_ENDPOINTS_ACCESS_TOKEN" ]; then
  echo
  echo -e "\e[31m   ⚠️WARNING⚠️: The OVH_AI_ENDPOINTS_ACCESS_TOKEN is not set!\e[0m"
  echo
  echo -e "\e[31m   Without a valid token: API calls will be rate-limited.\e[0m"
  echo
  echo -e "\e[31m   Solution steps:\e[0m"
  echo -e "\e[31m   1. Delete the conf file: \e[0m$(echo -e "\e[35;1mrm attendee-conf.json\e[0m")"
  echo -e "\e[31m   2. Rerun the script: \e[0m$(echo -e "\e[35;1msource ./setup_env.sh\e[0m")"
  echo -e "\e[31m   3. Enter the correct password\e[0m"
else
  echo
  echo -e "\e[32m✅ Your variables are set successfully. You can now use them during the workshop.\e[0m"
  echo
fi

# create and activate a python venv
python3 -m venv .venv
source .venv/bin/activate

# Install Javelit
jbang app install javelit@javelit
source ~/.bashrc

# copy VSCode snippets to activate them
cp -r .vscode/ ../.vscode
