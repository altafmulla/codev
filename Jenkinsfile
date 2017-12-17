pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        echo 'this is minimal pipeline'
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
  environment {
    mvnHome = 'tool \'M3\''
  }
}