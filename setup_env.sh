export OVH_AI_ENDPOINTS_MODEL_URL=https://mistral-7b-instruct-v0-3.endpoints.kepler.ai.cloud.ovh.net/api/openai_compat/v1
export OVH_AI_ENDPOINTS_MODEL_NAME=Mistral-7B-Instruct-v0.3
export OVH_AI_ENDPOINTS_ACCESS_TOKEN=<put your token here>


echo "Your environment variables:"
echo "   - OVH_AI_ENDPOINTS_ACCESS_TOKEN: " $OVH_AI_ENDPOINTS_ACCESS_TOKEN
echo "   - OVH_AI_ENDPOINTS_MODEL_URL: " $OVH_AI_ENDPOINTS_MODEL_URL
echo "   - OVH_AI_ENDPOINTS_MODEL_NAME: " $OVH_AI_ENDPOINTS_MODEL_NAME

# create and activate a python venv
python3 -m venv .venv
source .venv/bin/activate