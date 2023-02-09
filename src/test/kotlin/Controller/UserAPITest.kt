package Controller

import Core.Model.User
import Helpers.TestingContainer
import Helpers.onlineUser
import Services.DBConfig
import Utils.JSONUtils.jsonToObject
import Utils.UserUtil
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


/**
 * Integration tests
 */
open class UserAPITest {

    val db = DBConfig().getDbConnection()
    val app = TestingContainer.instance
    val origin = "http://localhost:" + app.port()
    val userUtil = UserUtil(origin)
    val mock = onlineUser

    @Nested
    inner class CreateUsers {
        @Test
        fun `creating valid user, returns a 201 response`() {
            val addResponse = userUtil.addUser(mock)
            assertEquals(201, addResponse.status)
            val retrieveResponse= userUtil.getUser(mock.email)
            assertEquals(200, retrieveResponse.status)
            val retrievedUser : User = jsonToObject(addResponse.body.toString())
            assertEquals(mock.email, retrievedUser.email)
            assertEquals(mock.username, retrievedUser.username)
            val deleteResponse = userUtil.deleteUser(mock.email)
            assertEquals(204, deleteResponse.status)
        }
    }

    @Nested
    inner class ReadUsers {
        @Test
        fun `reading users, returns 200`() {
            val response = userUtil.getUsers()
            if (response.status == 200) {
                val retrievedUsers: ArrayList<User> = jsonToObject(response.body.toString())
                assertEquals(0, retrievedUsers.size)
            }
            else {
                assertEquals(404, response.status)
            }
        }

        @Test
        fun `reading invalid user (by user id), returns a 404 response`() {
            val randInt = Int.MIN_VALUE
            assertEquals(404, userUtil.getUser(randInt).status)

        }

        @Test
        fun `reading invalid user (by email) , returns a 404 response`() {
             assertEquals(404, userUtil.getUser("InValidEmail").status)

        }

        @Test
        fun `reading valid user (by user id), returns 200 response`() {
            val addResponse = userUtil.addUser(mock)
            val addedUser : User = jsonToObject(addResponse.body.toString())
            val retrieveResponse = userUtil.getUser(addedUser.id)
            assertEquals(200, retrieveResponse.status)
            userUtil.deleteUser(mock.email)
        }

        @Test
        fun `getting a user by email when email exists, returns a 200 response`() {
            val addResponse = userUtil.addUser(mock)
            val retrieveResponse = userUtil.getUser(mock.email)
            assertEquals(200, retrieveResponse.status)
            val retrievedUser : User = jsonToObject(retrieveResponse.body.toString())
            userUtil.deleteUser(retrievedUser.email)
        }
    }

    @Nested
    inner class UpdateUsers {

        @Test
        fun `updating valid user, returns a 204 response`() {
            val addResponse = userUtil.addUser(mock)
            val addedUser : User = jsonToObject(addResponse.body.toString())
            assertEquals(204, userUtil.updateUser(addedUser.id, addedUser.username).status)
            val updatedUserResponse = userUtil.getUser(addedUser.id)
            val updatedUser : User = jsonToObject(updatedUserResponse.body.toString())
            assertEquals("updatedTest324", updatedUser.password)
            userUtil.deleteUser(updatedUser.email)
        }

        @Test
        fun `updating invalid user, returns a 404 response`() {
            assertEquals(404, userUtil.updateUser(Int.MIN_VALUE, "invalid").status)
        }
    }

    @Nested
    inner class DeleteUsers {

        @Test
        fun `deleting Invalid user, returns a 404 response`() {
            assertEquals(404, userUtil.deleteUser("invalid").status)
        }

        @Test
        fun `deleting valid user, returns a 204 response`() {
            val addResponse = userUtil.addUser(mock)
            val addedUser: User = jsonToObject(addResponse.body.toString())
            assertEquals(204, userUtil.deleteUser(addedUser.email).status)
            assertEquals(404, userUtil.getUser(addedUser.id).status)
        }

    }
}