package com.daimler.diacon.vdc

def boolean checkMap(String mapName, Map checkMap, List<String> mandatoryElements, boolean mandatory){
    mandatoryElements.each{
        element=checkMap.get(it)
        if (! element?.trim()){
            if(mandatory){
                echo "${mapName} does not contain mandatory entry: $it. Please correct and start again."
                quiet_sh "exit 1" 
            }else{
                return false
            }
        }
    }
}