pipeline {
    agent any
   
    
    tools
    {
        maven 'maven 3.6.0' 
    }
    stages 
    {
        stage('Pull') 
        {
            steps {
                echo 'Pulling Code...'
                echo "Running ${env.BUILD_ID} ${env.BUILD_DISPLAY_NAME} on ${env.NODE_NAME} and JOB ${env.JOB_NAME}"
              git branch: 'development', url: 'https://github.com/BeingBright/zolder-app-backend.git'
            }
        }
        stage('Build') 
        {
            steps {
                echo 'Building...'  
                sh "mvn clean package"
            }
        }
        stage('Saving Results') 
        {
            steps {
                // junit 'target/surefire-reports/TEST-*.xml'
                archiveArtifacts 'target/*.jar'
            }
        }
        stage('Deploy') 
        {
            steps {
                echo 'Deploy...'  
                echo 'Stopping current container...'  
                sh 'docker stop --time=1 zolder-app-backend-container || true'
                echo 'Removing current container...'  
                sh 'docker rm zolder-app-backend-container || true'
                echo 'Rebuilding image...'
                sh 'docker build -t java:zolder-app-backend -f Dockerfile .'
                echo 'Running container...'
                sh 'docker run --network="zolder-app" --name="zolder-app-backend-container" java:zolder-app-backend .'
            }
        }
       
    }

} 