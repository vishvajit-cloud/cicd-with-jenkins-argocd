pipeline {
    agent none

    environment {
        JAVA_HOME = "/usr/lib/jvm/java-21-openjdk-amd64"
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-cred')
        IMAGE_NAME            = "yourdockerhubuser/simple-app"
        IMAGE_TAG              = "${env.BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            agent { label 'maven-node' }
            steps {
                git branch: 'main', url: 'https://github.com/vishvajit-cloud/cicd-with-jenkins-argocd.git'
            }
        }

        stage('Build') {
            agent { label 'maven-node' }
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            agent { label 'maven-node' }
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            agent { label 'maven-node' }
            steps {
                sh 'mvn package -DskipTests'
                stash includes: 'target/simple-app.jar', name: 'app-jar'
            }
        }

        stage('SonarQube Scan') {
            agent { label 'maven-node' }
            steps {
                withSonarQubeEnv('sonar-server') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Quality Gate') {
            agent { label 'maven-node' }
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    script {
                        def qg = waitForQualityGate()
                        env.QUALITY_GATE_STATUS = qg.status
                        if (qg.status != 'OK') {
                            error "Quality Gate failed: ${qg.status}"
                        }
                    }
                }
            }
        }

        stage('Docker Build & Push') {
            agent { label 'docker-node' }
            steps {
                unstash 'app-jar'
                checkout scm   // pulls the Dockerfile onto the docker-node workspace
                sh """
                    docker build -t ${IMAGE_NAME}:${IMAGE_TAG} -t ${IMAGE_NAME}:latest .
                    echo "${DOCKERHUB_CREDENTIALS_PSW}" | docker login -u "${DOCKERHUB_CREDENTIALS_USR}" --password-stdin
                    docker push ${IMAGE_NAME}:${IMAGE_TAG}
                    docker push ${IMAGE_NAME}:latest
                    docker logout
                """
            }
        }
    }

    post {
        success {
            emailext(
                to: 'your-team@example.com',
                subject: "SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Quality Gate passed (${env.QUALITY_GATE_STATUS}). Image pushed: ${env.IMAGE_NAME}:${env.IMAGE_TAG}"
            )
        }
        failure {
            emailext(
                to: 'your-team@example.com',
                subject: "FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Pipeline failed. Quality Gate status: ${env.QUALITY_GATE_STATUS ?: 'not reached'}. Check console: ${env.BUILD_URL}console"
            )
        }
    }
}
