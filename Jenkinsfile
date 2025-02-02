pipeline {
    agent any
    environment {
        IMAGE_TAG = "047719624346.dkr.ecr.ap-northeast-2.amazonaws.com/app:${BUILD_NUMBER}"
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
                dir('h') {  // JAR 빌드 경로 지정
                    sh 'chmod +x ./gradlew'
                    sh './gradlew clean build'
                }
                echo 'Jar build success'
            }
        }

        stage('Docker image create & Push to ECR') {
            steps {
                script {
                    sh "docker build -t ${IMAGE_TAG} -f Dockerfile /home/ubuntu/MyBlog_project/h/"
                    echo "Docker image created: ${IMAGE_TAG}"

                    sh "aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 047719624346.dkr.ecr.ap-northeast-2.amazonaws.com"
                    echo "AWS ECR Login success"

                    sh "docker push ${IMAGE_TAG}"
                    echo "Docker image pushed: ${IMAGE_TAG}"
                }
            }
        }

        stage('Update Deployment in Git for ArgoCD') {
            steps {
                script {
                    echo 'Updating Kubernetes deployment file'
                    sh "sed -i 's|image: 047719624346.dkr.ecr.ap-northeast-2.amazonaws.com/app:.*|image: 047719624346.dkr.ecr.ap-northeast-2.amazonaws.com/app:${BUILD_NUMBER}|' k8s/deployment.yaml"

                    sh "git config --global user.email 'jenkins@yourdomain.com'"
                    sh "git config --global user.name 'Jenkins CI'"
                    sh "git add deployment.yaml"
                    sh "git commit -m 'Update deployment image to ${IMAGE_TAG}'"
                    sh "git push origin main"
                }
            }
        }
        stage('Trigger ArgoCD Sync') {
            steps {
                script {
                    sh "argocd app sync my-spring-app"
                }
            }
        }
    }
}
