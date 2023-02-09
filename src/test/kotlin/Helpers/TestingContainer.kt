package Helpers

import Services.javalinConfig

/**
 * Singleton class for testing
 */
object TestingContainer {

    val instance by lazy {
        startServerContainer()
    }

    private fun startServerContainer() = javalinConfig().startJavalinService()
}