pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    stages {

        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Code Review') {
            steps {
                sh 'mvn pmd:pmd'
            }
        }

        stage('Unit Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Security Scan') {
            steps {
                sh 'mvn spotbugs:spotbugs'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Deploy') {
            steps {
                sh 'mvn spring-boot:run &'
            }
        }
    }
}