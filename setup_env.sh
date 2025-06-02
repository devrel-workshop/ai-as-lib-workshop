# Get the attendee configuration parameters
if ! [ -f "./attendee-conf.json" ];then
  curl -u attendee http://162.19.64.158/workshop-ai-endpoints/configurations -o attendee-conf.json
  #curl -u attendee http://localhost:8080/workshop-ai-endpoints/configurations -o attendee-conf.json
fi

export OVH_AI_ENDPOINTS_MODEL_URL=https://mistral-7b-instruct-v0-3.endpoints.kepler.ai.cloud.ovh.net/api/openai_compat/v1
export OVH_AI_ENDPOINTS_MODEL_NAME=Mistral-7B-Instruct-v0.3
export OVH_AI_ENDPOINTS_EMBEDDING_MODEL=https://bge-multilingual-gemma2.endpoints.kepler.ai.cloud.ovh.net

echo "Downloaded parameters:"
cat attendee-conf.json|jq

export OVH_AI_ENDPOINTS_ACCESS_TOKEN=$(jq -r '.aiEndpointsToken' "attendee-conf.json")

echo "Your environment variables:"
echo "   - OVH_AI_ENDPOINTS_ACCESS_TOKEN: " $OVH_AI_ENDPOINTS_ACCESS_TOKEN
echo "   - OVH_AI_ENDPOINTS_MODEL_URL: " $OVH_AI_ENDPOINTS_MODEL_URL
echo "   - OVH_AI_ENDPOINTS_MODEL_NAME: " $OVH_AI_ENDPOINTS_MODEL_NAME
echo "   - OVH_AI_ENDPOINTS_EMBEDDING_MODEL: " $OVH_AI_ENDPOINTS_EMBEDDING_MODEL

# create and activate a python venv
python3 -m venv .venv
source .venv/bin/activate