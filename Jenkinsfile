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
                        credentialsId: 'dev123'
                    ]]
                ])
            }
        }

        stage('Build Gradle project') {
            steps {
                dir('h') {  // ✅ checkout된 디렉토리에서 빌드
                    sh 'chmod +x ./gradlew'
                    sh './gradlew clean build'
                }
                echo 'Jar build success'
            }
        }

        stage('Docker image create & Push to ECR') {
            steps {
                dir('h') {
                    script {
                        sh 'rm build/libs/h-0.0.1-SNAPSHOT-plain.jar'
                        sh "docker build -t ${IMAGE_TAG} -f Dockerfile ${WORKSPACE}/"
                        echo "Docker image created: ${IMAGE_TAG}"

                        sh "aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 047719624346.dkr.ecr.ap-northeast-2.amazonaws.com"
                        echo "AWS ECR Login success"

                        sh "docker push ${IMAGE_TAG}"
                        echo "Docker image pushed: ${IMAGE_TAG}"
                    }
                }
            }
        }

        stage('Update Deployment in Git for ArgoCD') {
            steps {
                script {
                    echo 'Updating Kubernetes deployment file'
                    sh "sed -i 's|image: 047719624346.dkr.ecr.ap-northeast-2.amazonaws.com/app:.*|image: 047719624346.dkr.ecr.ap-northeast-2.amazonaws.com/app:${BUILD_NUMBER}|' ${WORKSPACE}/k8s/deployment.yaml"

                    sh "git config --global user.email 'hyunho3445@gmail.com'"
                    sh "git config --global user.name 'dev123'"

                    sh "git add /var/lib/jenkins/workspace/app_deploy/k8s/deployment.yaml"  // ✅ 경로 수정
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
