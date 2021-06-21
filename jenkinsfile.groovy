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
  //  sh "aws ecr create-repository --repository-name ${STACKNAME}"
      sh "docker tag ${STACKNAME}:latest ${ECR_REGISTRY}/${STACKNAME}"
     sh "docker push ${ECR_REGISTRY}/${STACKNAME}"
     sh 'echo "image is pushed"'
   //  sh "docker rmi ${ECR_REGISTRY} ${STACKNAME}"
   }
  }
}
stage('Approval'){
  steps{
sh 'echo "in approval stage" '
  timeout(time: 15, unit: "MINUTES") {
    input message: 'Do you want to  deploy it ?', ok: 'Yes'

  }
}
}
stage('Deploy'){
  steps{
    script{
sh 'echo "in deployment stage" '
    sh "aws ecs create-cluster --cluster-name fargate-cluster"
    sh "aws ecs register-task-definition --cli-input-json file://taskdef.json"
   sh "aws ecs list-task-definitions"
      taskdefcount=sh(script:"aws ecs list-task-definitions --family-prefix sample-fargate",returnStdout:true)
   //println taskdefcount
    def slurper = new groovy.json.JsonSlurperClassic()
    taskdefcount= slurper.parseText(taskdefcount)
    taskdefcount=taskdefcount.taskDefinitionArns.size()
    //println "taskdefcount parsed:${taskdefcount}"
    sh " aws ecs create-service --cluster fargate-cluster --service-name fargate-service --task-definition sample-fargate:${taskdefcount} --desired-count 1 --launch-type \"FARGATE\" --network-configuration \"awsvpcConfiguration={subnets=[subnet-4c6fb07d],securityGroups=[sg-1579811d],assignPublicIp=ENABLED}\""
 sh "aws ecs list-services --cluster fargate-cluster"
     timeout(time: 15, unit: "MINUTES") {
    input message: 'Do you want to  remove the cluster it ?', ok: 'Yes'
  sh "aws ecs delete-cluster --cluster fargate-cluster"
  }
    }
  
  }
}
 }
 

}
