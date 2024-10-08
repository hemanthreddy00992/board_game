pipeline {
    agent any
    tools{
        jdk 'jdk17'
        maven 'maven3'
    }
    
    environment {
        SCANNER_HOME=tool 'sonar-scanner'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/hemanthreddy00992/board_game.git'
            }
        }
        stage('compile') {
            steps {
                sh 'mvn clean compile'
            }
        }
        
        stage('Sonar analysys'){
            steps {
            withSonarQubeEnv('sonar-server') {
                        sh ''' $SCANNER_HOME/bin/sonar-scanner -X \
                        -Dsonar.projectName=board-game \
                        -Dsonar.projectKey=board-game \
                        -Dsonar.java.binaries=target  '''
                }
            }
        }
        
        stage('Quality Check'){
            steps {
            script {
                waitForQualityGate abortPipeline: false, credentialsId: 'sonar-token'
            }
            }
        }
        
        stage('trivy file scan'){
            steps {
                sh 'trivy fs . > trivyfs.txt'
            }
        }
        
        
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        
      stage('deploy to nexus') {
            steps {
               configFileProvider([configFile(fileId: 'af4ce2bf-ea6f-493a-9b3c-e56d17f9a037', variable: 'mavensettings')]) {
                        sh "mvn -s $mavensettings clean deploy -DskipTests=true"
                    }
            }
        }
        
         stage('Docker build and tag') {
            steps {
                script {
                 sh "docker build -t hemanthreddy00992/game:latest ."
                }
            }
        } 
        
        stage('Docker push') {
            steps {
                script {
                withDockerRegistry(credentialsId: 'docker-cred', toolName: 'docker') {
                 sh "docker push hemanthreddy00992/game:latest"
                    }
                }
            }
        }
        
        stage('trivy image scan'){
            steps{
                sh 'trivy image hemanthreddy00992/game:latest > trivyimage.txt'
            }
        }
      
/*
      ################ Docker Deploy ############################## 
        stage('Docker deploy') {
            steps {
                script {
                withDockerRegistry(credentialsId: 'docker-cred', toolName: 'docker') {
                 sh "docker run -it -d -p 8082:8080 hemanthreddy00992/game:latest"
                    }
                }
            }
        }
        */

        stage('Checkout K8S manifest SCM'){
            steps {
                git credentialsId: 'f87a34a8-0e09-45e7-b9cf-6dc68feac670', 
                url: 'https://github.com/hemanthreddy00992/board_game.git',
                branch: 'main'
            }
        }

        stage('Update k8s deployement file'){
            steps {
        script {
            withCredentials([usernamePassword(credentialsId: 'f87a34a8-0e09-45e7-b9cf-6dc68feac670', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                sh '''
                # Display the original contents of the deployment file
                echo "Original deployement.yaml contents:"
                cat deployement.yaml
                
                # Use sed to replace the image tag with the Jenkins BUILD_NUMBER
                sed -i.bak "s|image: .*:.*|image: hemanthreddy00992/game:${BUILD_NUMBER}|g" k8s/manifests/deployement.yaml
                
                # Show updated deployment file
                echo "Updated deployement.yaml contents:"
                cat deployement.yaml

                # Configure Git
                git config user.email "hemanthreddy00992@gmail.com"
                git config user.name "hemanthreddy00992"

                # Stage and commit the changes
                git add deployement.yaml
                git commit -m "Updated the deployement.yaml | Jenkins Pipeline"

                # Push changes using the GitHub credentials
                git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/hemanthreddy00992/board_game.git HEAD:main
                '''
                }
              }
            }
        }

      

    }
}
