package Core.API

import io.javalin.apibuilder.EndpointGroup

/**
 * This interface handles API endpoints merging
 */
interface API {

    val endpoints: EndpointGroup

    companion object {
        val APIFactory = arrayListOf(
            UserAPI,
            MessageAPI
        )
    }
}