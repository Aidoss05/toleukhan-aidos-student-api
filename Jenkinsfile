pipeline {
    agent any

    triggers {
        pollSCM('H/2 * * * *')
    }

    tools {
        jdk 'jdk21'
    }

    environment {
        STUDENT_NAME = 'Toleukhan Aidos'
        IMAGE_NAME   = 'toleukhan-aidos-student-api'
        IMAGE_TAG    = "v1.${BUILD_NUMBER}-toleukhan-aidos"
    }

    stages {
        stage('Checkout') {
            steps {
                echo "=== Pipeline started by student: ${STUDENT_NAME} ==="
                checkout scm
                sh 'git log --oneline -3'
            }
        }

        stage('Build') {
            steps {
                echo "Building Spring Boot application of ${STUDENT_NAME}"
                sh 'chmod +x gradlew'
                sh './gradlew clean bootJar -x test --no-daemon'
            }
        }

        stage('Test') {
            steps {
                echo "Running automated tests for ${STUDENT_NAME}"
                sh './gradlew test --no-daemon'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                echo "Building Docker image ${IMAGE_NAME}:${IMAGE_TAG}"
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('Verify') {
            steps {
                sh "docker image inspect ${IMAGE_NAME}:${IMAGE_TAG} --format 'Image exists: {{.Id}} Size: {{.Size}} bytes'"
                sh "docker images ${IMAGE_NAME}"
                echo "Image ${IMAGE_NAME}:${IMAGE_TAG} verified for ${STUDENT_NAME}"
            }
        }
    }

    post {
        success {
            echo "SUCCESS: pipeline of ${STUDENT_NAME} finished"
        }
        failure {
            echo "FAILED: pipeline of ${STUDENT_NAME} stopped, check the logs"
        }
    }
}
