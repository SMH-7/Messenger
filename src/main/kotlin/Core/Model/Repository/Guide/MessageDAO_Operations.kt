package Core.Model.Repository.Guide

import Core.Model.Message

/**
 * All methods for message repository
 */
interface MessageDAO_Operations {
    fun getMessages(byUserId: Int): List<Message>
    fun getMessage(byMessageId: Int): Message?
    fun getAll(): ArrayList<Message>
    fun deleteMessages(byUserId: Int): Int
    fun deleteMessage(byMessageId: Int): Int
    fun updateMessage(byMessageId: Int, newMessage: Message): Int
    fun saveMessage(comingMessage: Message) : Int?
}