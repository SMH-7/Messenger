package Core.API

import Core.Controller.MessageController
import Core.Model.Message
import Utils.Params.MSG_ID

import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.apibuilder.EndpointGroup
import io.javalin.http.Context
import io.javalin.plugin.openapi.annotations.*

/**
 * This class handles swagger documentation and configures message API endpoints.
 */
object MessageAPI : API {

    private const val tag = "Message"

    override val endpoints = EndpointGroup {
        path("/messages") {
            post(::saveMessage)
            path("/{$MSG_ID}") {
                delete(::deleteMessage)
                get(::getMessage)
                patch(::updateMessage)
            }
        }
    }

    @OpenApi(
        summary = "get messages for a user",
        operationId = "getMessages",
        tags = [tag],
        path = "/api/users/id/{user-id}/messages",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Array<Message>::class)])]
    )
    private fun getMessages(ctx: Context) = MessageController.getMessages(ctx)

    @OpenApi(
        summary = "get individual message",
        operationId = "getMessage",
        tags = [tag],
        path = "/api/messages/{msg-id}",
        method = HttpMethod.GET,
        responses = [OpenApiResponse("200", [OpenApiContent(Message::class)])]
    )
    private fun getMessage(ctx: Context) = MessageController.getMessage(ctx)

    @OpenApi(
        summary = "delete messages for a user",
        operationId = "deleteMessages",
        tags = [tag],
        path = "/api/users/id/{user-id}/messages",
        method = HttpMethod.DELETE,
        pathParams = [OpenApiParam("user-id", Int::class, "User Id")],
        responses  = [OpenApiResponse("204")]
    )
    private fun deleteMessages(ctx: Context) = MessageController.deleteMessages(ctx)

    @OpenApi(
        summary = "delete message",
        operationId = "deleteMessage",
        tags = [tag],
        path = "/api/messages/{msg-id}",
        method = HttpMethod.DELETE,
        pathParams = [OpenApiParam("msg-id", Int::class, "message id")],
        responses  = [OpenApiResponse("204")]
    )
    private fun deleteMessage(ctx: Context) = MessageController.deleteMessage(ctx)

    @OpenApi(
        summary = "update message",
        operationId = "updateMessage",
        tags = [tag],
        path = "/api/messages/{msg-id}",
        method = HttpMethod.PATCH,
        pathParams = [OpenApiParam("msg-id", Int::class, "message Id")],
        responses  = [OpenApiResponse("204")]
    )
    private fun updateMessage(ctx: Context) = MessageController.updateMessage(ctx)

    @OpenApi(
        summary = "save message",
        operationId = "saveMessage",
        tags = [tag],
        path = "/api/messages",
        method = HttpMethod.POST,
        responses = [OpenApiResponse("200", [OpenApiContent(Message::class)])]
    )
    private fun saveMessage(ctx: Context) = MessageController.saveMessage(ctx)
}