#!/bin/bash

# Update package index and install dependencies
sudo apt-get update -y
sudo apt-get install -y apt-transport-https ca-certificates curl

# ---------------------
# Install Docker
# ---------------------
# Add Docker’s official GPG key and repository
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"

# Update package index again and install Docker
sudo apt-get update -y
sudo apt-get install -y docker-ce

# Add the current user to the docker group to allow non-root Docker use
sudo usermod -aG docker $USER
sudo chmod 777 /var/run/docker.sock

# Enable and start Docker service
sudo systemctl enable docker
sudo systemctl start docker


######## Nexus running #####################
docker run --name nexus -d -p 8081:8081 sonatype/nexus

