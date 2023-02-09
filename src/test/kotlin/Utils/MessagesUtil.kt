package Utils

import Core.Model.Message
import Helpers.started
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import kong.unirest.Unirest

/**
 * Utility class for message API
 */
class MessagesUtil (private val origin: String){

    private val path = "$origin/api/messages"

    private val localMessage = Message(id = 1,
        text = "updated Message",
        userId = 1,
        created = started)

    fun getMessage(byId : Int): HttpResponse<JsonNode> = Unirest.get("$path/${byId}").asJson()

    fun getMessages(byUserId: Int) : HttpResponse<JsonNode> = Unirest.get(origin + "/api/users/id/${byUserId}/messages").asJson()

    fun deleteMessage(msgId: Int) : HttpResponse<JsonNode> = Unirest.delete("$path/$msgId").asJson()

    fun deleteMessages(userId: Int) : HttpResponse<JsonNode> = Unirest.delete(origin + "/api/users/id/${userId}/messages").asJson()

    fun addMessage (message: Message, forUser: Int): HttpResponse<JsonNode> {
        return Unirest.post(path)
            .body("""
                {
                   "text":"$message",
                   "userId":$forUser,
                   "created":"$started"
                }
            """.trimIndent())
            .asJson()
    }

    fun updateMessage (msgId : Int, userId: Int) : HttpResponse<JsonNode> {
        return Unirest.patch("$path/$msgId")
            .body("""
                {
                   "text":"${localMessage.text}",
                   "userId":$userId,
                   "created":"$started"
                }
            """.trimIndent())
            .asJson()
    }
}