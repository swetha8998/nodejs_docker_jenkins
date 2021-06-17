def STACKNAME= 'Hello-World-node'
def ACCOUNT= '993745358053'
def ECR_REGISTRY='993745358053.dkr.ecr.us-east-1.amazonaws.com'
def version = currentBuild.number
pipeline{
agent any

stages{
stage('git'){
  steps{
git branch: 'master', url: 'https://github.com/swetha8998/nodejs_docker_jenkins.git'
  }
}
stage('Build'){
  steps{
   script{
     sh 'docker build -f . -t Hello-World-node'
     sh 'aws ecr-public create-repository --repository-name ${STACKNAME}'
     sh 'docker tag ${STACKNAME}:latest ${ECR_REGISTRY}/${STACKNAME}'
     sh 'docker push ${ECR_REGISTRY}/${STACKNAME}:${version}'
     sh 'echo "image is pushed" '
   }
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
