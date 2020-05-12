def IMAGENAME = "wms-report"
def IMAGETAG = 'latest'
def gitCommit() {
    return sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
}
def getLatestTag(){
    return sh(returnStdout: true, script: 'git describe --tags --always').trim()
}
pipeline {
    agent any
    stages {
        stage ('Clone') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: 'master']], extensions: [[$class: 'WipeWorkspace']], userRemoteConfigs: [[credentialsId: 'bff11b86-4d33-4fb2-b02c-38e042b1b0b7', refspec: '+refs/tags/*:refs/remotes/origin/tag/*', url: 'https://github.com/leevydanomalik/WMS-REPORT.git']]])
                script{
                    IMAGETAG = getLatestTag()
                }
            }
        }
        
        stage ('build maven') {
            steps {
                configFileProvider([configFile(fileId: '35487557-94df-4be8-b04e-feaac076ccfe', variable: 'MAVEN_SETTINGS')]){
                    sh "git checkout -b deploy/${IMAGETAG} ${IMAGETAG}"
                    sh "mvn -s $MAVEN_SETTINGS clean install -U -DskipTests"
                }
            }
        }
        
        stage('Docker Build - ${IMAGENAME}'){
            steps {
                echo 'Building Command..'
                sh "cd wms.trx.command.svc && mvn package docker:build -DskipTests"
                sh "docker tag leevy/${IMAGENAMECMD}:latest leevy/${IMAGENAMECMD}:dev-${IMAGETAG}"
            }
        }
        
        
                stage('Push docker ${IMAGENAME}){
            steps {
                withDockerRegistry([ credentialsId: "ae1b0e71-0d8a-4bad-9093-7ffcb42595ab", url: "https://index.docker.io/v1/" ]){
                    echo 'Push Docker image..'
                    sh "docker push leevy/${IMAGENAME}:dev-${IMAGETAG}"   
                }
            }
        }
    }
}
