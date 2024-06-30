pipeline {
    agent any

    environment {
        NEXUS_REPO = 'maven-releases' 
        NEXUS_URL = 'http://192.168.33.10:8081/repository'  
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: 'main']], userRemoteConfigs: [[url: 'https://github.com/mhaziz18/Devops.git']]])
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
                sh 'mvn compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                sh 'mvn clean verify sonar:sonar \
                    -Dsonar.projectKey=event \
                    -Dsonar.projectName="event" \
                    -Dsonar.host.url=http://192.168.33.10:9000 \
                    -Dsonar.login=sqp_3f4acbe98d7c91125bbbdc02df38031ceb8d1381'
            }
        }

        stage('Publish Artifacts to Nexus') {
            steps {
                script {
                    nexusArtifactUploader artifacts: [[
                        artifactId: 'eventsProject',
                        classifier: '',
                        file: 'target/eventsProject-1.0.jar',
                        type: 'jar']],
                        credentialsId: 'nexus-server',
                        groupId: 'tn.esprit.rh',
                        nexusUrl: '192.168.33.10:8081',
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        repository: 'maven-releases/',
                        version: '1.0'

                }
            }
        }

        stage('Collect Prometheus Metrics') {
            steps {
                script {
                    def prometheusMetrics = sh(script: "curl -s http://192.168.33.10:9090/metrics", returnStatus: true)

                    if (prometheusMetrics == 0) {
                        echo "Successfully collected Prometheus metrics from http://192.168.33.10:9090/metrics."
                    } else {
                        error "Failed to collect Prometheus metrics. The curl exit code was: $prometheusMetrics"
                    }
                }
            }
        }

        stage('Collect Jenkins Metrics') {
            steps {
                script {
                    def jenkinsMetrics = sh(script: "curl -s http://192.168.33.10:8080/prometheus/", returnStatus: true)

                    if (jenkinsMetrics == 0) {
                        echo "Successfully collected Jenkins metrics from http://192.168.33.10:8080/prometheus/."
                    } else {
                        error "Failed to collect Jenkins metrics. The curl exit code was: $jenkinsMetrics"
                    }
                }
            }
        }

        stage('Curl Grafana Dashboard') {
            steps {
                script {
                    def grafanaDashboardURL = 'http://192.168.33.10:3000/d/haryan-jenkins/jenkins3a-performance-and-health-overview?orgId=1'

                    // Run curl command and capture the HTTP status code
                    def curlOutput = sh(script: "curl -o /dev/null -s -w '%{http_code}' -L $grafanaDashboardURL", returnStdout: true).trim()
                    def curlExitCode = curlOutput.toInteger()

                    // Check the HTTP status code and print appropriate message
                    if (curlExitCode == 200) {
                        echo "Successfully curled Grafana dashboard."
                    } else {
                        echo "Failed to curl Grafana dashboard. HTTP status code: $curlExitCode"
                        currentBuild.result = 'FAILURE'  // Mark the build as failed
                        error "Failed to curl Grafana dashboard. HTTP status code: $curlExitCode"
                    }
                }
            }
        }


        stage('Start MySQL Container') {
            steps {
                script {
                    def dockerComposeFilePath = 'docker-compose.yml'
                    sh "docker-compose -f ${dockerComposeFilePath} up -d"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def dockerImageName = 'lassouedaziz/event:latest'
                    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                        def dockerImage = docker.build dockerImageName
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    def dockerImageName = 'lassouedaziz/event:latest'
                    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                        sh "docker login -u $DOCKER_HUB_USERNAME -p $DOCKER_HUB_PASSWORD"
                        sh "docker push $dockerImageName"
                    }
                }
            }
        }


        }
    }
}
