#!/bin/bash

clear

# Test deployed application
echo "💬: My name is Stéphane"
echo "🤖: "

curl  -N http://localhost:8080/chatbot/memory \
      -X POST -d '{"question":"My name is Stéphane"}' \
      -H 'Content-Type: application/json'

echo ""
echo ""

read -n 1 -p "Press any key to continue"

echo ""
echo "💬: Do you remember my name?"
echo "🤖: "

curl  -N http://localhost:8080/chatbot/memory \
      -X POST -d '{"question":"Do you remember my name?"}' \
      -H 'Content-Type: application/json'