#!/bin/bash
# Update system and install necessary packages
sudo apt update -y
sudo apt install -y unzip curl tar docker.io

# Start and enable Docker
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker $USER

# Install AWS CLI
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install

# Install kubectl
curl -o kubectl -LO "https://dl.k8s.io/release/v1.31.0/bin/linux/amd64/kubectl"
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl

# Install eksctl
ARCH=amd64
PLATFORM=$(uname -s)_$ARCH
curl -sLO "https://github.com/eksctl-io/eksctl/releases/latest/download/eksctl_$PLATFORM.tar.gz"
tar -xzf eksctl_$PLATFORM.tar.gz -C /tmp && rm eksctl_$PLATFORM.tar.gz
sudo mv /tmp/eksctl /usr/local/bin

# Create EKS cluster using eksctl
eksctl create cluster \
 --name demo-eks \
 --region ap-northeast-2 \
 --with-oidc \
 --nodegroup-name demo-ng \
 --zones ap-northeast-2a,ap-northeast-2b \
 --nodes 2 \
 --node-type t2.medium \
 --node-volume-size 20 \
 --managed

# Wait for the cluster to be ready
echo "EKS cluster creation is in progress. Please check the AWS Management Console for the cluster status."
