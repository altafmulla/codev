pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        git(url: 'https://github.com/altafmulla/codev.git', branch: 'master')
        mvnHome= tool 'M3'
      }
    }
    stage('clean') {
      steps {
        sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore clean package"
      }
    }
    stage('results') {
      steps {
        jenkins '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
      }
    }
  }
}
