FROM mcr.microsoft.com/devcontainers/base:ubuntu

# Install bat via apt
RUN apt-get update \
    && apt-get install -y --no-install-recommends bat \
    && rm -rf /var/lib/apt/lists/*

# Guard /etc/bash.bashrc against being sourced from zsh
# This prevents "shopt: command not found" errors when zsh sources bash files
RUN sed -i '1i# Skip bash-specific content when sourced from zsh\n[ -n "$ZSH_VERSION" ] && return\n' /etc/bash.bashrc

# Switch to vscode user for SDKMAN installation
USER vscode
ENV SDKMAN_DIR=/home/vscode/.sdkman

# Install SDKMAN and tools: Java 27 Temurin, Maven 3.10.0, JBang, Quarkus CLI
RUN curl -s "https://get.sdkman.io" | bash \
    && bash -c "\
        source $SDKMAN_DIR/bin/sdkman-init.sh \
        && sdk install java 27.0.0.0+35-tem \
        && sdk install maven 3.10.0 \
        && sdk install jbang \
        && sdk install quarkus \
        && sdk flush tmp"
