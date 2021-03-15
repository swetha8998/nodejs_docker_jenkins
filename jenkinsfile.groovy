pipeline{
 agent {
    dockerfile true
  }
 environment{
  def dockerImage =''
 }
stages{
stage('git'){
  steps{
git branch: 'master', url: 'https://github.com/swetha8998/nodejs_docker_jenkins.git'
  }
}
stage('Build'){
  steps{
   script{
dockerImage=docker.build nodeapp
   }
   //sh 'docker build  . -f Dockerfile -t swet/nodeapp'
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
