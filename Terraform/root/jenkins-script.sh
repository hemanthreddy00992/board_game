#!/bin/bash

############ docker install ########################

# Update package index and install dependencies
sudo apt-get update -y
sudo apt-get install -y openjdk-11-jdk apt-transport-https ca-certificates curl software-properties-common

# Add Docker’s official GPG key and repository
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"

# Update package index again and install Docker
sudo apt-get update -y
sudo apt-get install -y docker-ce

# Add the current user to the docker group to allow non-root Docker use
sudo usermod -aG docker $USER

# Enable and start Docker service
sudo systemctl enable docker
sudo systemctl start docker

# Optional sleep (for Docker to fully initialize)
sleep 5


############ Jenkins install ########################

# Add Jenkins repository and import the GPG key
wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo apt-key add -
sudo sh -c 'echo deb http://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'

# Update the package index again and install Jenkins
sudo apt-get update -y
sudo apt-get install -y jenkins

# Enable and start Jenkins service
sudo systemctl enable jenkins
sudo systemctl start jenkins

sleep 5

################### Trivy install #######################
sudo apt-get update -y

sudo apt-get install -y snapd

# Install Trivy using Snap
sudo snap install trivy --classic

# Optional: Perform a test scan to verify Trivy installation
# This command scans the local Docker image 'alpine' (if available)
sudo trivy image alpine
