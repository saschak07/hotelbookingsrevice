pipeline {
	 agent any
	 environment {
       	 GOOGLE_CREDENTIALS = credentials('GCP_CREDS')
         repository = 'gcr.io/quickstart-1556004401507/hotelluxury'
   			}
	    stages {
        stage('Build and Test Application') {
        	agent {
        	    docker {
    	            image 'openjdk:8-jdk-alpine'
    	        }
        	}
        	steps {
	            sh 'chmod +x mvnw'
	            sh './ mvnw package'
	        }
	      }
	      
	    stage('Docker build and Push Image') {
	    agent any
	    steps {
    	    script {
    	        COMMIT_ID = sh(returnStdout:true, script: 'git rev-parse HEAD')
    	        IMAGE_TAG = "JENKINS-${env.BUILD_ID}_${BRANCH_NAME}_${COMMIT_ID}"
    	        sh 'echo $GOOGLE_CREDENTIALS > keyfile.json'
    	        sh 'docker login -u _json_key -p "$(cat keyfile.json)" https://grc.io'
    	        sh "docker build -t ${repository}:${IMAGE_TAG}"

    	    }
    	}

    	   
    	}  
    }
}