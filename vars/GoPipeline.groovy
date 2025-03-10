def call(Map pipelineParams, Map optionsMap) {
    
    def language = ""
    def settings = new com.daimler.diacon.vdc.Settings()
    switch(pipelineParams["PROG_LANGUAGE"]){
        case "GO":
            echo "Programming language is GO"
            language = "GO"
            break;
        default:
            echo "unknown PROG_LANGUAGE in pipelineParams: ${pipelineParams.PROG_LANGUAGE}. exiting."
            sh "exit 1"
    }

    settings.checkMap("optionsMap",optionsMap,["COMPANY","PROJECTGROUP","REGION"],true)

    if (language == "GO") {  
        pipeline{
            agent {
                node {
                    label pipelineParams["PROG_LANGUAGE"]
                }
            }
            stages {
                stage("Import libraries"){
                    steps{
                        sh "go mod download"
                    }
                }
                stage("Test"){
                    steps{
                        sh "go test ."
                    }
                }
                stage("Build"){
                    steps{
                        sh "go build ."
                    }
                }
            }
        }
    } else { 
        echo "Language is not supported"
        sh "exit 1"
    }
}