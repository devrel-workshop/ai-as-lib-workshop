import os
import requests

# py-82
# Use Bert-​base-​multilingual-​uncased-​sentiment, see https://endpoints.ai.cloud.ovh.net/models/bert-base-multilingual-uncased-sentiment
url = "https://bert-base-multilingual-uncased-sentiment.endpoints.kepler.ai.cloud.ovh.net/api/text2sentiments"

# py-83
# Set the bearer token and the header to access to AI Endpoint, see https://endpoints.ai.cloud.ovh.net/  
headers = {
    "Content-Type": "application/json",
    "Authorization": f"Bearer {os.getenv('OVH_AI_ENDPOINTS_ACCESS_TOKEN')}",
}

# py-84
# Call the model and display the result
response = requests.post(url, data="Good product, delivered on time. Improved after-sales service.", headers=headers)
if response.status_code == 200:
    response_data = response.json()
    max_key = max(response_data, key=response_data.get)
    num_stars = int(max_key.split()[0])
    star_emoji = '⭐️' * num_stars
    print(f"Review : {star_emoji}")
else:
    print("❌ Error:", response.status_code)