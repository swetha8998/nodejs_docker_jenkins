pipeline{
 agent {
    dockerfile true
  }
stages{
stage('git'){
  steps{
git branch: 'master', url: 'https://github.com/swetha8998/nodejs_docker_jenkins.git'
  }
}
stage('Build'){
  steps{
sh 'docker build  . -f Dockerfile -t swet/nodeapp'
  }
}
stage('Approval'){
  steps{
sh 'echo "in approval stage" '

  }
}
stage('Deploy'){
  steps{
sh 'echo "in deployment stage" '
 }
}
 }
 
}
