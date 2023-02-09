package Controller


import Core.Model.Message
import Core.Model.User
import Helpers.*
import Utils.JSONUtils.jsonNodeToObject
import Utils.JSONUtils.jsonToObject
import Utils.MessagesUtil
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

/**
 * Integration tests
 */
class MessageAPITest : UserAPITest() {

    private val msgMock = onlineMessage
    private val msgUtil = MessagesUtil(origin)

    @Nested
    inner class CreateMessage {

        @Test
        fun `creating valid message, returns 201 response`() {
            var newUser : User = jsonToObject(userUtil.addUser(newUser).body.toString())
            var addResponse = msgUtil.addMessage(msgMock, newUser.id)
            assertEquals(201, addResponse.status)
            val msg : Message = jsonToObject(addResponse.body.toString())
            msgUtil.deleteMessage(msg.id)
            userUtil.deleteUser(newUser.email)
        }

        @Test
        fun `creating message to invalid user, returns 404 response`() {
            val randInt = Int.MIN_VALUE
            assertEquals(404, msgUtil.getMessage(randInt).status)
        }
    }

    @Nested
    inner class ReadMessage {

        @Test
        fun `reading all valid message for a user id, returns 200 response`() {
            var newUser : User = jsonToObject(userUtil.addUser(newUser).body.toString())
            var addResponse = msgUtil.addMessage(msgMock, newUser.id)
            val response = msgUtil.getMessages(newUser.id)
            val returnMsgs = jsonNodeToObject<Array<Message>>(response)
            assertEquals(1, returnMsgs.size)
            msgUtil.deleteMessage(returnMsgs[0].id)
            userUtil.deleteUser(newUser.email)
        }

        @Test
        fun `reading all invalid message for a user id, returns 404 response`() {
            var newUser : User = jsonToObject(userUtil.addUser(newUser).body.toString())
            assertEquals(404, msgUtil.getMessages(newUser.id).status)
            userUtil.deleteUser(newUser.email)
        }

        @Test
        fun `reading message for invalid user, returns 404`() {
            assertEquals(404, msgUtil.getMessages(Int.MIN_VALUE).status)
        }

        @Test
        fun `reading message for invalid message id, returns 404`() {
            assertEquals(404, msgUtil.getMessage(Int.MIN_VALUE).status)

        }

        @Test
        fun `reading valid message, return 200 response`() {
            var newUser : User = jsonToObject(userUtil.addUser(newUser).body.toString())
            var addResponse = msgUtil.addMessage(msgMock, newUser.id)
            val messages = jsonNodeToObject<Message>(addResponse)
            val response = msgUtil.getMessage(messages.id)
            assertEquals(200, response.status)
            msgUtil.deleteMessage(messages.id)
            userUtil.deleteUser(newUser.email)
        }
    }

    @Nested
    inner class UpdateMessages {

        @Test
        fun `updating invalid message, returns response 404`() {
            val randInt = Int.MIN_VALUE
            assertEquals(404, msgUtil.updateMessage(randInt, randInt).status)
        }

        @Test
        fun `updating valid message, returns response 204`() {
            var newUser : User = jsonToObject(userUtil.addUser(newUser).body.toString())
            var addResponse =   msgUtil.addMessage(msgMock, newUser.id)
            assertEquals(201, addResponse.status)
            val msg = jsonNodeToObject<Message>(addResponse)
            assertEquals(204, msgUtil.updateMessage(msg.id, newUser.id).status)
            msgUtil.deleteMessage(msg.id)
            userUtil.deleteUser(newUser.email)
        }
    }

    @Nested
    inner class DeleteMessages {

        @Test
        fun `deleting invalid message, returns response 404`() {
            assertEquals(404, msgUtil.deleteMessage(Int.MIN_VALUE).status)
        }

        @Test
        fun `deleting messages for invalid user, returns response 404`() {
            assertEquals(404, msgUtil.deleteMessages(Int.MIN_VALUE).status)
        }

        @Test
        fun `deleting valid message, returns response 204`() {
            var newUser : User = jsonToObject(userUtil.addUser(newUser).body.toString())
            var addResponse =   msgUtil.addMessage(msgMock, newUser.id)
            assertEquals(201, addResponse.status)
            val msg = jsonNodeToObject<Message>(addResponse)
            assertEquals(204, msgUtil.deleteMessage(msg.id).status)
            userUtil.deleteUser(newUser.email)
        }
    }
}