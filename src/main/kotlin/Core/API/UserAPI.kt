package Core.API

import Core.Controller.MessageController.deleteMessages
import Core.Controller.MessageController.getMessages
import Core.Controller.UserController
import Core.Model.User
import Utils.Params.USER_ID
import Utils.Params.USER_MAIL
import Utils.Params.USER_NAME

import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.apibuilder.EndpointGroup
import io.javalin.http.Context
import io.javalin.plugin.openapi.annotations.*

/**
 * This class handles swagger documentation and configures user API endpoints.
 */
object UserAPI : API {

    private const val tag = "User"

    override val endpoints = EndpointGroup {
        path("/users") {
            post(::addUser)
            get(::getUsers)
            path("/id") {
                path("/{$USER_ID}") {
                    get(::getUserById)
                    path("/messages") {
                        get(::getMessages)
                        delete(::deleteMessages)
                    }
                }
            }
            path("/mail") {
                path("/{$USER_MAIL}") {
                    delete(::deleteUser)
                    get(::getUser)
                }
            }
            path("/name") {
                path("/{$USER_NAME}") {
                    patch(::updateUser)
                }
            }
        }
    }

    @OpenApi(
        summary = "add new user",
        operationId = "addUser",
        tags = [tag],
        path = "/api/users",
        method = HttpMethod.POST,
        responses  = [OpenApiResponse("201")]
    )
    private fun addUser(ctx: Context) = UserController.addUser(ctx)

    @OpenApi(
        summary = "delete user",
        operationId = "deleteUser",
        tags = [tag],
        path = "/api/users/mail/{user-mail}",
        method = HttpMethod.DELETE,
        pathParams = [OpenApiParam("user-mail", Int::class, "user email")],
        responses  = [OpenApiResponse("204")]
    )
    private fun deleteUser(ctx: Context) = UserController.deleteUser(ctx)

    @OpenApi(
        summary = "update user via name",
        operationId = "updateUser",
        tags = [tag],
        path = "/api/users/name/{user-name}",
        method = HttpMethod.PATCH,
        pathParams = [OpenApiParam("user-name", Int::class, "user name")],
        responses  = [OpenApiResponse("204")]
    )
    private fun updateUser(ctx: Context) = UserController.updateUser(ctx)

    @OpenApi(
        summary = "get users",
        operationId = "getUsers",
        tags = [tag],
        path = "/api/users",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Array<User>::class)])]
    )
    private fun getUsers(ctx: Context) = UserController.getUsers(ctx)

    @OpenApi(
        summary = "get users by email",
        operationId = "getUser",
        tags = [tag],
        path = "/api/users/mail/{user-mail}",
        method = HttpMethod.GET,
        pathParams = [OpenApiParam("user-mail", Int::class, "user mail")],
        responses  = [OpenApiResponse("200", [OpenApiContent(User::class)])]
    )
    private fun getUser(ctx: Context) = UserController.getUser(ctx)

    @OpenApi(
        summary = "get users by Id",
        operationId = "getUserById",
        tags = [tag],
        path = "/api/users/id/{user-id}",
        method = HttpMethod.GET,
        pathParams = [OpenApiParam("user-id", Int::class, "User Id")],
        responses  = [OpenApiResponse("200", [OpenApiContent(User::class)])]
    )
    private fun getUserById(ctx: Context) = UserController.getUserById(ctx)
}