import os
import requests

# py-79
# Use Bert-​base-​NER, see https://endpoints.ai.cloud.ovh.net/models/bert-base-ner
url = "https://bert-base-ner.endpoints.kepler.ai.cloud.ovh.net/api/text2ner"

# py-80
# Set the bearer token and the header to access to AI Endpoint, see https://endpoints.ai.cloud.ovh.net/  
headers = {
    "Content-Type": "text/plain",
    "Authorization": f"Bearer {os.getenv('OVH_AI_ENDPOINTS_ACCESS_TOKEN')}",
}

# py-81
# Call the model and display the result
response = requests.post(url, data={"text": "My name is Clara and I live in Berkeley, California."}, headers=headers)
if response.status_code == 200:
    response_data = response.json()
    res_data = response.json()
    
    output = []
    for entity in res_data:
        description = ""
        if entity['entity'] == "B-PER":
            description = "is a person"
        elif entity['entity'] == "B-LOC":
            description = "a location"
        elif entity['entity'] == "B-ORG":
            description = "an organization"
        elif entity['entity'] == "B-MISC":
            description = "other"
        else:
            description = "undefined"
        
        output.append(f"{entity['word']} {description}")
    
    print(", ".join(output))
else:
    print("Error:", response.status_code)