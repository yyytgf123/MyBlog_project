pipeline {
    agent any
    environment {
        IMAGE_TAG = "047719624346.dkr.ecr.ap-northeast-2.amazonaws.com/my-spring-app:${BUILD_NUMBER}"
    }
    stages {
        stage('Prepare') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[
                        url: 'https://github.com/yyytgf123/MyBlog_project.git',
                        credentialsId: 'jenkins_deploy'
                    ]]
                ])
            }
        }

        stage('Build Gradle project') {
            steps {
                sh './gradlew clean'
                echo 'Jar clean success'

                sh './gradlew build'
                echo 'Jar build success'
            }
        }

        stage('Docker image create & Push to ECR') {
            steps {
                script {
                    sh "docker build -t ${IMAGE_TAG} ."
                    echo "Docker image created: ${IMAGE_TAG}"

                    sh "aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 047719624346.dkr.ecr.ap-northeast-2.amazonaws.com"
                    echo "AWS ECR Login success"

                    sh "docker push ${IMAGE_TAG}"
                    echo "Docker image pushed: ${IMAGE_TAG}"
                }
            }
        }

        stage('Deploy to K8s') {
            steps {
                echo 'Deploying to Kubernetes'

                // deployment.yaml의 이미지 태그를 최신 빌드 버전으로 변경
                sh "sed -i 's|image: 047719624346.dkr.ecr.ap-northeast-2.amazonaws.com/my-spring-app:.*|image: 047719624346.dkr.ecr.ap-northeast-2.amazonaws.com/my-spring-app:${BUILD_NUMBER}|' deployment.yaml"

                echo 'Deploy stage - Kubernetes Deployment'
                sh 'kubectl apply -f deployment.yaml'
                sh 'kubectl rollout status deployment my-app -n my-namespace'
            }
        }
    }
}
