package Services

import Core.API.API.Companion.APIFactory
import Core.Model.errorResponse
import Utils.JSONUtils.jsonObjectMapper
import cc.vileda.openapi.dsl.info
import cc.vileda.openapi.dsl.openapiDsl
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.plugin.json.JavalinJackson
import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.OpenApiPlugin
import io.javalin.plugin.openapi.ui.ReDocOptions
import io.javalin.plugin.openapi.ui.SwaggerOptions
import io.javalin.plugin.rendering.vue.VueComponent


/**
 * This class handles configuration for the Javalin service
 */
class javalinConfig {

    /**
     * This method is used to start and configure the Javalin service
     * @return Javalin instance
     */
    fun startJavalinService(): Javalin {
        val app = Javalin.create {
            it.registerPlugin(configureOpenApi())
            it.defaultContentType = "application/json"
            it.jsonMapper(configureMapper())
            it.enableWebjars()
        }.apply {
            registerExceptionHandlers(this)
        }.start(getRemoteAssignedPort())
        registerRoutes(app)
        return app
    }

    /**
     * Setting up JSON mapping
     */
    private fun configureMapper(): JavalinJackson {
        return JavalinJackson(jsonObjectMapper())
    }

    /**
     * Setting up error handler
     */
    private fun registerExceptionHandlers(app: Javalin) {
        app.exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        app.error(404) { ctx -> ctx.json("404 - Not Found") }
    }

    /**
     * Provides a port to run an instance on
     */
    private fun getRemoteAssignedPort() = System.getenv("PORT")?.toInt() ?: 7000

    /**
     * This method is used to register the routes
     */
    private fun registerRoutes(app: Javalin) {
        app.routes {
            get("/", VueComponent("<home-view></home-view>"))
            get("/users/id/{user-id}/messages", VueComponent("<messenger-view></messenger-view>"))
            APIFactory.forEach { path("/api", it.endpoints) }
        }
    }
}

/**
 * This method is used to configure the OpenApiPlugin
 */
fun configureOpenApi() = OpenApiPlugin(
    OpenApiOptions {
        openapiDsl {
            info {
                title = "Apple App"
                description = "integrated API"
                version = "1.2.0"
            }
        }
    }.apply {
        path("/swagger-docs")
        swagger(SwaggerOptions("/swagger-ui"))
        reDoc(ReDocOptions("/redoc"))
        defaultDocumentation { doc ->
            doc.json("500", errorResponse::class.java)
            doc.json("503", errorResponse::class.java)
        }
    }
)