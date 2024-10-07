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


sudo usermod -aG docker $USER
sudo chmod 777 /var/run/docker.sock

# Enable and start Docker service
sudo systemctl enable docker
sudo systemctl start docker

# ---------------------
# Run SonarQube using Docker
# ---------------------
# Pull the SonarQube Docker image
sudo docker pull sonarqube

# Run SonarQube container
sudo docker run -d --name sonarqube -p 9000:9000 sonarqube:lts-community

# Optional: Wait for a few seconds to allow SonarQube to start
sleep 30

# Inform about the SonarQube setup
echo "SonarQube is running at http://<YOUR_INSTANCE_IP>:9000"
