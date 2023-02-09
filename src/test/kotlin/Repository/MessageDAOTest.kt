package Repository

import Core.Model.PostgreSQL.Messages
import Core.Model.Repository.MessageDAO
import Helpers.*
import Helpers.deinitMessages
import Helpers.initMessages
import junit.framework.TestCase.assertEquals
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*

/**
 * Unit test
 */
private val msgs = messages

class MessageDAOTest : ConfigDAOTest() {

    @Nested
    inner class CreateMessages {

        @Test
        fun `multiple messages added, retrieved successfully`() {
            transaction {
                val userDAO = initUsers()
                val msgDAO = initMessages()
                assertEquals(6, msgDAO.getAll().size)
                deinitMessages(msgDAO)
                deinitUsers(userDAO)
            }
        }

        @Test
        fun `empty message table, returns 0 count`() {
            transaction {
                SchemaUtils.create(Messages)
                val msgDAO = MessageDAO()
                assertEquals(0, msgDAO.getAll().size)
            }
        }
    }


    @Nested
    inner class ReadMessages {
        @Test
        fun `reading messages via invalid id, results unsuccessfully`() {
            transaction {
                val userDAO = initUsers()
                val msgDAO = initMessages()
                assertEquals(null, msgDAO.getMessage(-1))
                assertEquals(0, msgDAO.getMessages(-1).size)
                deinitMessages(msgDAO)
                deinitUsers(userDAO)
            }
        }

        @Test
        fun `reading messages via valid id, results successfully`() {
            transaction {
                val userDAO = initUsers()
                val msgDAO = initMessages()
                assertEquals(msgs[0].text, msgDAO.getMessage(1)?.text)
                assertEquals(msgs[1].text, msgDAO.getMessage(2)?.text)
                assertEquals(msgs[4].text, msgDAO.getMessage(5)?.text)
                deinitMessages(msgDAO)
                deinitUsers(userDAO)
            }
        }
    }

    @Nested
    inner class DeleteMessages {

        @Test
        fun `deleting invalid message, results unsuccessfully`() {
            transaction {
                val userDAO = initUsers()
                val msgDAO = initMessages()
                assertEquals(6, msgDAO.getAll().size)
                msgDAO.deleteMessage(-1)
                assertEquals(6, msgDAO.getAll().size)
                deinitMessages(msgDAO)
                deinitUsers(userDAO)
            }
        }

        @Test
        fun `deleting valid message, results successfully`() {
            transaction {
                val userDAO = initUsers()
                val msgDAO = initMessages()
                assertEquals(6, msgDAO.getAll().size)
                msgDAO.deleteMessages(2)
                assertEquals(3, msgDAO.getAll().size)
                deinitMessages(msgDAO)
                deinitUsers(userDAO)
            }
        }
    }

    @Nested
    inner class UpdateMessages {

        @Test
        fun `updating valid message, results successfully`() {
            transaction {
                val userDAO = initUsers()
                val msgDAO = initMessages()
                msgDAO.updateMessage(1, newMsg)
                assertEquals(msgDAO.getMessage(1)?.text, newMsg.text)
                deinitMessages(msgDAO)
                deinitUsers(userDAO)
            }
        }

        @Test
        fun `updating invalid message, results unsuccessfully`() {
            transaction {
                val userDAO = initUsers()
                val msgDAO = initMessages()
                msgDAO.updateMessage(-1, newMsg)
                assertEquals(null, msgDAO.getMessage(-1))
                deinitMessages(msgDAO)
                deinitUsers(userDAO)
            }
        }
    }
}