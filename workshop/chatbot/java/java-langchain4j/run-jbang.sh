#!/bin/bash

clear

# 🛠️ Configurations 🛠️
source ../../../../bin/set-env-variables.sh

# Run Java class giving as parameter
echo "☕️ Running Java class: $1" 🚀 using model $OVH_AI_ENDPOINTS_MODEL_NAME 🧠
echo ""
read -n 1 -p "Press any key to continue"
echo ""

jbang $1