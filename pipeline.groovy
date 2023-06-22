pipeline {
    agent {label 'master' }
    environment {
        CLOUD_FLARE_API_KEY = "w0NENSLxArIUU4D80m7cJzAVSyDeBgFJZTSkLSZ4"
        CLOUD_FLARE_API_KEY_TO_UPDATE_RULESET = "kN0MY1HBSkEeQMOkJ7A7zhOuWLYTe6SlYSN3MMfL"
        ZONE_IDENTIFIER = "fe72f30aec947d76892b4afef8f6ecf9"
    }
    stages{
        stage("clone repository"){
            git branch: 'main',
                credentialsId: 'build automation repo private key',
                url: 'git@github.com:RickyChang0989/JenkinsCloudflareAPIToolbox.git'
            sh (
                script: "pip --default-timeout=1000 install -r ./requirements.txt > /dev/null",
                returnStdout: false
            )
        }
        stage("Purge cloudfalre cache"){
            steps{
                script{
                    sh (
                        script: "pip install -r ./requirements.txt > /dev/null",
                        returnStdout: false
                    )
                    sh(script: "python3 ./clear_cloud_flare_cache.py -k $CLOUD_FLARE_API_KEY -p https://gs-api.gzqyjt.com/storage/v1/b/h365-landing-page/o/config%2Fgame-channel-config.json > /dev/null")
                }
            }
        }

    }
    post{
        always {
           cleanWs deleteDirs: true
        }
    }
}