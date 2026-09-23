#!/bin/bash

# 🛠️ Configurations 🛠️
source ../../../../bin/set-env-variables.sh

# Run Quarkus in dev mode
echo "⚡️ Running Quarkus in dev mode ⚡️"
echo ""
read -n 1 -p "Press any key to continue"
echo ""

export JDK_JAVA_OPTIONS='--add-opens java.base/java.lang=ALL-UNNAMED --enable-native-access=ALL-UNNAMED'

# code-server: the Dev UI only accepts localhost, allow the workspace proxy host too,
# and serve /q under code-server's path-preserving /absproxy/8080 so the Dev UI assets resolve
if [ -n "$VSCODE_PROXY_URI" ]; then
  WORKSPACE_HOST=$(echo "$VSCODE_PROXY_URI" | sed -E 's#^https?://([^/:]+).*#\1#')
  export QUARKUS_DEV_UI_HOSTS="$WORKSPACE_HOST"
  export QUARKUS_HTTP_NON_APPLICATION_ROOT_PATH=/absproxy/8080/q
  echo "🌐 Dev UI: https://$WORKSPACE_HOST/absproxy/8080/q/dev-ui/"
fi

quarkus dev