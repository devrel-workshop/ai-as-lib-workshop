FROM ubuntu:24.04

# Build arguments
ARG USERNAME=vscode
ARG USER_UID=1000
ARG USER_GID=$USER_UID
ARG JAVA_VERSION=25-tem
ARG MAVEN_VERSION=3.9.12
ARG PYTHON_VERSION=3.12
ARG NODE_VERSION=20

# Avoid prompts from apt
ENV DEBIAN_FRONTEND=noninteractive

# Install system dependencies
RUN apt-get update && apt-get install -yq \
    build-essential \
    curl \
    wget \
    git \
    git-lfs \
    zip \
    unzip \
    jq \
    bat \
    sudo \
    locales \
    ca-certificates \
    gnupg \
    openssh-client \
    python${PYTHON_VERSION} \
    python${PYTHON_VERSION}-venv \
    python3-pip \
    ffmpeg \
    libgl1 \
    libglib2.0-0 \
    zsh \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Generate locale
RUN locale-gen en_US.UTF-8
ENV LANG=en_US.UTF-8 \
    LANGUAGE=en_US:en \
    LC_ALL=en_US.UTF-8

# Create symlinks
RUN ln -sf /usr/bin/batcat /usr/local/bin/bat && \
    ln -sf /usr/bin/python${PYTHON_VERSION} /usr/local/bin/python3 && \
    ln -sf /usr/bin/python${PYTHON_VERSION} /usr/local/bin/python

# Create non-root user
RUN groupadd --gid $USER_GID $USERNAME \
    && useradd --uid $USER_UID --gid $USER_GID -m $USERNAME -s /bin/zsh \
    && echo $USERNAME ALL=\(root\) NOPASSWD:ALL > /etc/sudoers.d/$USERNAME \
    && chmod 0440 /etc/sudoers.d/$USERNAME

# Install Node.js
RUN curl -fsSL https://deb.nodesource.com/setup_${NODE_VERSION}.x | bash - \
    && apt-get install -y nodejs \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/* \
    && npm install -g npm@latest

# Switch to non-root user for SDKMAN and user-level installations
USER $USERNAME
WORKDIR /home/$USERNAME

# Install SDKMAN
ENV SDKMAN_DIR="/home/$USERNAME/.sdkman"
RUN curl -s "https://get.sdkman.io" | bash

# Install Java, Maven, JBang, and Quarkus CLI via SDKMAN
RUN bash -c "source $SDKMAN_DIR/bin/sdkman-init.sh && \
    sdk install java $JAVA_VERSION && \
    sdk install maven $MAVEN_VERSION && \
    sdk install jbang && \
    sdk install quarkus && \
    sdk flush"

# Set up shell configurations
RUN echo 'export SDKMAN_DIR="$HOME/.sdkman"' >> ~/.bashrc && \
    echo '[[ -s "$SDKMAN_DIR/bin/sdkman-init.sh" ]] && source "$SDKMAN_DIR/bin/sdkman-init.sh"' >> ~/.bashrc && \
    echo 'export SDKMAN_DIR="$HOME/.sdkman"' >> ~/.zshrc && \
    echo '[[ -s "$SDKMAN_DIR/bin/sdkman-init.sh" ]] && source "$SDKMAN_DIR/bin/sdkman-init.sh"' >> ~/.zshrc

# Set environment variables for Java/Maven
ENV JAVA_HOME="/home/$USERNAME/.sdkman/candidates/java/current"
ENV MAVEN_HOME="/home/$USERNAME/.sdkman/candidates/maven/current"
ENV PATH="$JAVA_HOME/bin:$MAVEN_HOME/bin:$SDKMAN_DIR/candidates/jbang/current/bin:$SDKMAN_DIR/candidates/quarkus/current/bin:$PATH"

# Configure oh-my-zsh
RUN sh -c "$(curl -fsSL https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh)" "" --unattended || true && \
    echo 'export SDKMAN_DIR="$HOME/.sdkman"' >> ~/.zshrc && \
    echo '[[ -s "$SDKMAN_DIR/bin/sdkman-init.sh" ]] && source "$SDKMAN_DIR/bin/sdkman-init.sh"' >> ~/.zshrc

# Install Javelit via JBang
RUN bash -c "source $SDKMAN_DIR/bin/sdkman-init.sh && jbang app install javelit@javelit"

# Set working directory
WORKDIR /workspaces

# Default command
#CMD ["sleep", "infinity"]
