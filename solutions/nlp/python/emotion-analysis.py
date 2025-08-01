import os
import requests

# py-76
# Use roberta-base-go-emotions, see https://endpoints.ai.cloud.ovh.net/models/roberta-base-go-emotions
url = "https://roberta-base-go-emotions.endpoints.kepler.ai.cloud.ovh.net/api/text2emotions"

# py-77
# Set the bearer token and the header to access to AI Endpoint, see https://endpoints.ai.cloud.ovh.net/ 
headers = {
    "Content-Type": "application/json",
    "Authorization": f"Bearer {os.getenv('OVH_AI_ENDPOINTS_ACCESS_TOKEN')}",
}

# py-78
# Call the model and display the result
response = requests.post(url, data="I love Python", headers=headers)
if response.status_code == 200:
    response_data = response.json()
    max_key = max(response_data, key=response_data.get)
    print(f"Emotion: {max_key}")
else:
    print("❌ Error:", response.status_code)