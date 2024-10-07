Hi In this project we will do CICD pipeline for Java based Game board selector application.

# Ci pipeline ->
1. Using terraform to create a ec2 instances for Jenkins, sonarqube, jfrog.
2. Setup nexus using terraform
3. Install dependent plugins in Jenkins
4. Setup sonarqube , Jenkins, jfrog
5. Write Jenkins pipeline as checkout -> compile -> trivy fs scan ->  sonarqube integration -> build code -> publish to nexus -> docker build -> docker tag -> trivy docker image scan -> docker push

## Steps :

1. Install Jenkins
2. Login to Jenkins -> credentials -> docker cred (name) -> docker username and password
3. Plugins -> eclipse , docker, 
4. Tools -> idk using eclipse, maven , docker
5. Install sonarqube : take ec2 instance > install docker > sudo docker run -d --name sonarqube -p 9000:9000 sonarqube:lts-community
6. Login to sonarqube : <ip>:9000 -> admin, admin -> goto administration create a token -> goto projects create a project manually by giving details locally and give the token to create project.
7. Install trivy to do trivy scanning on Jenkins server -> https://aquasecurity.github.io/trivy/v0.18.3/installation/
8. Pipeline  :: 
	1. Publish builded binary to nexus repo from Jenkins: install “config file provider” plugin in Jenkins
	2. Manage Jenkins -> managed files -> add new config -> global maven setting -> ( In the file, at server change repo name, username, password)
Login to nexus - > repository -> repositories -> maven releases -> ( change layout policy to permissive, deployment policy to allow redeploy ) -> save


# CD pipeline ->
1. Write manifest files like deployment, service for backend and frontend.
2. Check these files using online k8s clusters
3. Create EKS cluster, ArgoCD
4. Configure ARGO for deployment

## Steps :

### Create EKS Cluster From UI
1. Create Role for EKS Cluster:
	Go to AWS Management Console.->  IAM -> Roles -> Create role-> AWS Service -> EKS-cluster -> Click "Next" and provide a name for the role.
2. Create Role for EC2 Instances :
   1. Go to AWS Management Console.->  IAM -> Roles -> Create role-> AWS Service -> EC2 -> Click "Next" and  add policies
   2. Add policies: [AmazonEC2ContainerRegistryReadOnly, AmazonEKS_CNI_Policy, AmazonEBSCSIDriverPolicy, AmazonEKSWorkerNodePolicy].
   3. provide a name for the role.
3. Create EKS Cluster:
	Navigate to Amazon EKS service.-> Click on "Create cluster".-> Enter the desired name, select version, and specify the role created in step 1.-> Configure Security Group, Cluster Endpoint, etc. -> create the cluster.
4. Create Compute Resources:
	Navigate to Amazon EKS service. -> Click on "Compute" or "Node groups".-> Provide a name for the compute resource.-> Select the role created in step 2.->Select Node Type & Size.-> Click "Next" and proceed to create the compute resource.
5. Configure Cloud Shell:
   1. Open AWS Cloud Shell or AWS CLI.
	 2. Execute the command:
          aws eks update-kubeconfig --name devops-eks --region ap-south-1

### Create EKS Cluster From Terraform
Go to EKS_Terraform folder and give terraform init, terraform validate, terraform plan, terraform apply --auto-approve -> Your EKS cluster is created.


## Install ArgoCD:
1. Create Namespace for ArgoCD:
      kubectl create namespace argocd
2. Apply ArgoCD Manifests:
      kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/v2.4.7/manifests/install.yaml
		  kubectl get all -n argocd
3. Patch Service Type to LoadBalancer:
      kubectl patch svc argocd-server -n argocd -p '{"spec": {"type": "LoadBalancer"}}'
		  kubectl get svc -n argocd
4. Retrieve Admin Password:
		  Username : admin
      kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d
These commands will install ArgoCD into the specified namespace, set up the service as a LoadBalancer, and retrieve the admin password for you to access the ArgoCD UI.


 # Some Screenshots :: 
![image](https://github.com/user-attachments/assets/b1c26de3-0d4f-4e9a-b127-afe9061b456e)


![image](https://github.com/user-attachments/assets/46ed3a65-84ac-4662-a27f-9ccbae289c24)



## Basic Feature Overview
1. Filter and search/sort within the game database
2. Add/Edit/Delete user accounts (with authentication)
3. Add/Edit/Delete user-added games
4. Add/Edit/Delete comments to games
5. Users can upload their own avatar images
6. Can filter games by category/mechanic
