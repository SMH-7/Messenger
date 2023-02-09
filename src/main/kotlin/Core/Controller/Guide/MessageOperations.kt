package Core.Controller.Guide

import io.javalin.http.Context

/**
 * All methods for message controller
 */
interface MessageOperations {
    fun getMessages(ctx: Context)
    fun getMessage(ctx: Context)
    fun deleteMessages(ctx: Context)
    fun deleteMessage(ctx: Context)
    fun updateMessage(ctx: Context)
    fun saveMessage(ctx: Context)
}