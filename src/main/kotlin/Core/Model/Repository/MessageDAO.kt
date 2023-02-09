package Core.Model.Repository

import Core.Model.Message
import Core.Model.PostgreSQL.Messages
import Core.Model.PostgreSQL.Users
import Utils.mapToMessage
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

/**
 * Handles transaction for message
 */
class MessageDAO {

    fun getMessages(byUserId: Int): List<Message> {
        return transaction {
            Messages.select{Messages.userId eq byUserId}
                .map { mapToMessage(it) }
        }
    }

    fun getMessage(byMessageId: Int): Message? {
        return transaction {
            Messages.select(){Messages.id eq byMessageId}
                .map { mapToMessage(it) }
                .firstOrNull()
        }
    }

    fun getAll(): ArrayList<Message> {
        val msgList: ArrayList<Message> = arrayListOf()
        transaction {
            Messages.selectAll().map {
                msgList.add(mapToMessage(it)) }
        }
        return msgList
    }

    fun deleteMessages(byUserId: Int): Int {
        return transaction {
            Messages.deleteWhere {
                Messages.userId eq byUserId
            }
        }
    }

    fun deleteMessage(byMessageId: Int): Int {
        return transaction {
            Messages.deleteWhere {
                Messages.id eq byMessageId
            }
        }
    }

    fun updateMessage(byMessageId: Int, newMessage: Message): Int {
        return transaction {
            Messages.update({Messages.id eq byMessageId}){
                it[text] = newMessage.text
            }
        }
    }

    fun saveMessage(comingMessage: Message) : Int?{
        return transaction {
            Messages.insert {
                it[text] = comingMessage.text
                it[userId] = comingMessage.userId
                it[created] = comingMessage.created
            }
        } get Messages.id
    }
}

