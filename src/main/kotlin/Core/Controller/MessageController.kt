package Core.Controller

import Core.Model.Message
import Core.Model.Repository.MessageDAO
import Core.Model.Repository.UserDAO
import Utils.JSONUtils.jsonToObject
import Utils.Params.parseMsgId
import Utils.Params.parseUserId
import io.javalin.http.Context
import io.javalin.plugin.openapi.annotations.*

/**
 * This class controls the data flow and handles errors for the message API.
 */
object MessageController {

    private val messageDAO = MessageDAO()
    private val userDAO = UserDAO()

    fun getMessages(ctx: Context) {
        val userId = parseUserId(ctx)
        val user = userDAO.getUser(userId)
        if (user == null) {
            ctx.status(404)
            ctx.json(mapOf("error" to "User not found"))
        } else {
            val messages = messageDAO.getMessages(userId)
            if (messages.isNotEmpty()) {
                ctx.json(messages)
                ctx.status(200)
            } else {
                ctx.status(404)
                ctx.json(mapOf("error" to "No messages found for user"))
            }
        }
    }

    fun getMessage(ctx: Context) {
        val message = messageDAO.getMessage(parseMsgId(ctx))
        if (message != null){
            ctx.json(message)
            ctx.status(200)
        }
        else{
            ctx.status(404)
            ctx.json(mapOf("error" to "No messages found for user"))
        }
    }

    fun deleteMessages(ctx: Context){
        val userId = parseUserId(ctx)
        if (messageDAO.deleteMessages(userId) != 0)
            ctx.status(204)
        else
            ctx.status(404)
            ctx.json(mapOf("error" to "Couldn't delete the messages"))
    }


    fun deleteMessage(ctx: Context){
        val messageId = parseMsgId(ctx)
        if (messageDAO.deleteMessage(messageId) != 0)
            ctx.status(204)
        else
            ctx.status(404)
            ctx.json(mapOf("error" to "Couldn't delete the message"))

    }

    fun updateMessage(ctx: Context){
        val msg : Message = jsonToObject(ctx.body())
        val messageId = parseMsgId(ctx)
        if (messageDAO.updateMessage(messageId, msg)!= 0)
            ctx.status(204)
        else
            ctx.status(404)
            ctx.json(mapOf("error" to "Couldn't update the message"))
    }

    fun saveMessage(ctx: Context) {
        val msg : Message = jsonToObject(ctx.body())
        val messageId = messageDAO.saveMessage(msg)
        if (userDAO.getUser(msg.userId) != null && messageId != null){
            msg.id = messageId
            ctx.json(msg)
            ctx.status(201)
        }
        else{
            ctx.status(404)
            ctx.json(mapOf("error" to "couldn't save message to database"))
        }
    }
}