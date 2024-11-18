The 101 AI endpoints hands on can be used with a CDE like [Coder](https://coder.com/).
This folder contains the docker file used.

As an example, here is a process to create and use an image.

    login into your registry  
    docker login --username <your docker ID>  

    run the command to build the image from this folder  
    docker build --tag [YOUR DOCKER HANDLE]/[YOUR IMAGE NAME]:[YOUR VERSION] .
    
    push this image to a registry
    docker push [YOUR DOCKER HANDLE]/[YOUR IMAGE NAME]:[YOUR VERSION]

