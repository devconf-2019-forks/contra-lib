import org.centos.contra.pipeline.Utils

/**
 * Defines the system content of a message
 * This will merge parameters with the defaults and will validate each parameter
 * @param parameters
 * @return HashMap
 */
def call(Map parameters = [:]) {

    def utils = new Utils()

    def defaults = readJSON text: libraryResource('msgBusSystemContent.json')

    return { Map runtimeArgs = [:] ->
        parameters = utils.mapMergeQuotes([parameters, runtimeArgs])
        try {
            mergedMessage = utils.mergeBusMessage(parameters, defaults)
        } catch(e) {
            throw new Exception("Creating message for system array failed!")
        }

        // sendCIMessage expects String arguments
        return utils.getMapStringColon(mergedMessage)
    }
}
