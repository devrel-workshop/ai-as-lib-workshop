#!/bin/bash

clear

# Test deployed application
echo "💬: Can you tell me what OVHcloud is and what kind of products it offers?"
echo "🤖: "

curl  -N http://localhost:8080/chatbot/simple \
      -X POST -d '{"question":"Can you tell me what OVHcloud is and what kind of products it offers?"}' \
      -H 'Content-Type: application/json'