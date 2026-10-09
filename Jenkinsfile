pipeline{
    agent any

    tools{
        jdk 'jdk21'
        maven 'maven3'
    }
    environment{
        IMAGE_NAME = 'apurva2318/shopping-app'
        IMAGE_TAG = "${BUILD_NUMBER}"
    }
    stages{
        stage('checkout'){
            steps
            {
                checkout scm
            }

        }
        stage('build & test'){
            steps{
                bat 'mvn clean test'
            }

        }
        stage('Docker build'){
            steps{
                bat 'docker build -t %IMAGE_NAME%:%IMAGE_TAG% -t %IMAGE_NAME%:latest .'
            }
        }
        stage('Docker Push'){
            steps{
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds',
                                                  usernameVariable: 'DOCKER_USER',
                                                  passwordVariable: 'DOCKER_PASS')]) {
                    bat 'echo %DOCKER_PASS%| docker login -u %DOCKER_USER% --password-stdin'
                    bat 'docker push %IMAGE_NAME%:%IMAGE_TAG%'
                    bat 'docker push %IMAGE_NAME%:latest'

            }
        }
        }
        stage('Deploy'){
            steps {
                bat 'docker rm -f shop || exit 0 '
                bat 'docker run -d --name shop -p 8081:8080 %IMAGE_NAME%:%IMAGE_TAG%'
            }
        }
    }
    post{
        always{
             bat 'docker logout'
        }
        success {
            echo 'Pipeline succeeded. App: http://localhost:8081'
        }
        failure {
            echo 'Pipeline failed. Check the console output.'
        }
    }
    
}