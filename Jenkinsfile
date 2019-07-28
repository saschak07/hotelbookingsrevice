node {
    def app

    stage('Clone repository') {
        /* Cloning the Repository to our Workspace */

        checkout scm
    }
    stage ('Compile Stage') {            
                withMaven(maven : 'M3') {
                    sh 'mvn clean install'
                }
		}

    stage('Build image') {
        /* This builds the actual image */

        app = docker.build("quickstart-1556004401507/hotelluxury")
    }
	stage('Push image') {
        /* 
			You would need to first register with DockerHub before you can push images to your account
		*/
        docker.withRegistry('https://gcr.io', 'gcr:quickstart-1556004401507') {
            app.push("${env.BUILD_NUMBER}")
            } 
                echo "Trying to Push Docker Build to DockerHub"
    }
    

  /* stage('Push image') {
        /* 
			You would need to first register with DockerHub before you can push images to your account
		*/
       //	GOOGLE_CREDENTIALS = credentials('GCP_CREDS')
       //  repository = 'gcr.io/quickstart-1556004401507/hotelluxury'
    	// COMMIT_ID = sh(returnStdout:true, script: 'git rev-parse HEAD')
    	// IMAGE_TAG = "JENKINS-${env.BUILD_ID}_${BRANCH_NAME}_${COMMIT_ID}"
    	// sh "echo ${GOOGLE_CREDENTIALS} > keyfile.json"
    	// sh 'docker login -u _json_key -p "$(cat keyfile.json)" https://grc.io'
    	// sh "docker build -t ${repository}:${IMAGE_TAG}"
    	// sh "docker push ${repository}:${IMAGE_TAG}"
        // echo "Trying to Push Docker Build to DockerHub"
   // }
    
    
}