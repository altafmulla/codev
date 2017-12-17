pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        git(url: 'https://github.com/altafmulla/codev.git', branch: 'master')
      }
    }
    stage('clean') {
      steps {
        sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore clean package"
      }
    }
    stage('results') {
      steps {
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
      }
    }
  }
}