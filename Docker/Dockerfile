FROM ubuntu:24.04
ARG PYTHON_VERSION=3.12

RUN apt-get update && apt-get install -yq \
    git \
    git-lfs \
    sudo \
    python${PYTHON_VERSION} \
    python3-venv \
    python3-pip \
    zip \
    unzip \
    curl \
    docker.io \
    libgl1 \
    libglib2.0-0 \
    jq \
    openjdk-21-jdk \
    maven \
    bat \
    ffmpeg \
    #    docker.io \
# thoses steps reduce the size of the image but users nees to do apt update to install anything
    && apt-get clean \
    && rm -rf /var/cache/debconf/* /var/lib/apt/lists/* /tmp/* /var/tmp/* \
    && apt-get clean -y

RUN curl https://cli.gra.ai.cloud.ovh.net/install.sh | bash
RUN cp /root/bin/ovhai /usr/local/bin/

# Create the coder user. UID must be 33333.
#RUN useradd -l -u 33333 -G sudo -md /home/coder -s /bin/bash -p coder coder

RUN rm /bin/sh && ln -s /bin/bash /bin/sh && ln -s /usr/bin/batcat /usr/local/bin/bat

USER ubuntu

ENV VIRTUAL_ENV=/home/ubuntu/venv
RUN python${PYTHON_VERSION} -m venv $VIRTUAL_ENV

RUN curl -s "https://get.sdkman.io" | bash
#ENV BASH_ENV="\$HOME/.sdkman/bin/sdkman-init.sh"
RUN chmod +x ${HOME}/.sdkman/bin/sdkman-init.sh \
    && source ${HOME}/.sdkman/bin/sdkman-init.sh \
    && sdk install java 21.0.5-tem  \
    && sdk install quarkus  \
    && sdk flush 

RUN curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.1/install.sh | bash
ENV NVM_DIR="/home/ubuntu/.nvm"
RUN chmod +x ${HOME}/.nvm/nvm.sh \
    && source ${HOME}/.nvm/nvm.sh \
    && nvm install v20.18.0 \
    && nvm use v20.18.0
