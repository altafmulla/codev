pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        git(url: 'https://github.com/altafmulla/codev.git', branch: 'master')
        tool(name: 'mvnHome', type: 'tool \'M3\'')
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