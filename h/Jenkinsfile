pipeline {
    agent any
    stages {
        stage('Prepare'){
            steps {
                git credentialsId : 'jenkins_deploy',
                    branch : 'main',
                    url : 'https://github.com/yyytgf123/MyBlog_project.git'
            }
        }
        stage('test') {
            steps {
                echo 'test stage'
            }
        }
        stage('build') {
            steps {
                echo 'build stage'
            }
        }
        stage('docker build') {
            steps {
                echo 'docker build stage'
            }
        }
    }
}