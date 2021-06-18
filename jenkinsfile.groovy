final STACKNAME= 'hello-world-node'
final ACCOUNT= '993745358053'
final ECR_REGISTRY='993745358053.dkr.ecr.us-east-1.amazonaws.com'
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
  // def STACKNAME= 'hello-world-node'
     //def var='hi'
     sh 'docker build . -f Dockerfile -t hello-world-node'
     sh 'aws --version'
    sh 'aws configure set region us-east-1'
   //  sh ' aws configure '
   //  sh "echo ${var}"
     sh " aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin ${ECR_REGISTRY} "
   // sh "aws ecr-public create-repository --repository-name ${STACKNAME}"
     sh "docker tag hello-world-node:latest public.ecr.aws/e6i8t7v2/hello-world-node:latest"
     sh "docker push public.ecr.aws/e6i8t7v2/hello-world-node:latest"
     sh 'echo "image is pushed"'
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
