pipeline {
    agent any
    stages {
        stage('Pull') {
            agent any
            steps {
                echo 'Pulling Code...'
                echo "Running ${env.BUILD_ID} ${env.BUILD_DISPLAY_NAME} on ${env.NODE_NAME} and JOB ${env.JOB_NAME}"
                git 'https://github.com/BeingBright/zolder-app-backend.git'
            }
        }
        stage('Build') {
            agent any
            steps {
                echo 'Building...'  
                sh "mvn clean package"
            }
        }
        stage('Saving Results') {
            steps {
                // junit '**/target/surefire-reports/TEST-*.xml'
                archiveArtifacts 'target/*.jar'
            }
        }
        stage('deploy') {
            steps {
                echo 'Deploy...'  
                sh 'docker stop --time=1 zolder-app-backend-container || true'
                sh 'dokcer rm zolder-app-backend-container || true'
                sh 'docker build -t java:zolder-app-backend -f Dockerfile .'
            }
        }
    }
} 