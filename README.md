## Welcome to the 101 workshop source to discover AI Endpoints.

## 🔗 Resources 🔗
 - https://www.ovhcloud.com/en/public-cloud/ai-endpoints/catalog/
 - https://blog.ovhcloud.com/tag/ai-endpoints/
 - https://github.com/ovh/public-cloud-examples/tree/main/ai/ai-endpoints

## 🏁 Getting started 🏁

To use this workshop you just have to clone this repository.

### 🧑‍💻 Note on the use of [Coder](https://coder.com/) CDE ☁️

To help people with their IDE configuration we use Coder during the workshop, this is not a mandatory step, but it will help you to have a better experience.  
Ask to have the URL where you can start a Coder workspace giving the AI workshop template.  
You can open your VSCode instance in your browser, choose _code-server_ button or with a ssh tunnel on your local VSCode, choose _VS Code Desktop_.

### 🐳 Dev Containers usage 🐳 ###

If you don't want or can't use Coder, you can use the Dev Container configuration file in [.devcontainer](./.devcontainer/).  
You can find more information about Dev Container in the [VSCode documentation](https://code.visualstudio.com/docs/devcontainers/containers).

> 💡 You can pre-pull the image to avoid network latency on the D Day 💡.   
> `mcr.microsoft.com/devcontainers/base:ubuntu`

### 🧰 Pre-requisites 🧰

Here are all the needed stuff to use it on your device witout Coder or Dev Containers.

 - [Bat](https://github.com/sharkdp/bat) utility
 - Java 25
 - [Maven 3.9.x](https://maven.apache.org/download.cgi)
 - [Quarkus CLI](https://quarkus.io/guides/cli-tooling)
 - [JBang](https://www.jbang.dev/)
 - Python 3.x
 - NodeJS 20.x

### ⚡️ Workshop initialisation ⚡️

**Go to the ai-as-lib-workshop folder**, execute the script [`setup_env.sh`](./setup_env.sh) to setup your environment: `source ./setup_env.sh`.

If you need to open a new terminal, source again, **at the root of the ai-as-lib-workshop folder**, the `setup_env.sh` script.

⚠️ Ask the speaker for the password ⚠️

## 🗃️ Folder organisation 🗃️

**⚠️ Your location to create your own files to do the workshop is [workshop](./workshop/) ⚠️**
> You can also, just, run the workshop solution in the dedicated folders in the [solutions](./solutions/) folder.

