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

      

    }
}
