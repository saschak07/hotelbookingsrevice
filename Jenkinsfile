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

        app = docker.build("saschak07/hotelapp01")
    }

    

    stage('Push image') {
        /* 
			You would need to first register with DockerHub before you can push images to your account
		*/
        docker.withRegistry('https://registry.hub.docker.com', 'docker_hub') {
            app.push("${env.BUILD_NUMBER}")
            app.push("latest")
            } 
                echo "Trying to Push Docker Build to DockerHub"
    }
    
    stage('deploy') {
   		sh 'docker pull saschak07/hotelapp01'
    	sh 'docker rm -f hotel || true'
    	sh 'docker run --name hotel -p 9000:9000 --link some-postgres:postgres -d saschak07/hotelapp01'
    }
}