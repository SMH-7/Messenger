package Repository

import Core.Model.PostgreSQL.Users
import Core.Model.Repository.UserDAO
import Helpers.*
import junit.framework.TestCase.assertEquals
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*

private val user1 = users[0]
private val user2 = users[1]

/**
 * Unit test
 */
class UserDAOTest : ConfigDAOTest() {

    @Nested
    inner class CreateUsers {

        @Test
        fun `empty user table, returns 0 count`() {
            transaction {
                SchemaUtils.create(Users)
                val userDAO = UserDAO()
                assertEquals(0, userDAO.getAllUser().size)
            }
        }

        @Test
        fun `multiple users added, retrieved successfully`() {
            transaction {
                val userDAO = initUsers()
                assertEquals(2, userDAO.getAllUser().size)
                assertEquals(user1, userDAO.getUser(user1.email))
                assertEquals(user2, userDAO.getUser(user2.email))
                deinitUsers(userDAO)
            }
        }
    }

    @Nested
    inner class ReadUsers  {

        @Test
        fun `reading users via invalid email, results unsuccessfully`() {
            transaction {
                val userDAO = initUsers()
                assertEquals(null, userDAO.getUser("invalidEmail"))
            }
        }

        @Test
        fun `reading user via valid email, results successfully`() {
            transaction {
                val userDAO = initUsers()
                assertEquals(user2, userDAO.getUser(user2.email))
                deinitUsers(userDAO)
            }
        }

        @Test
        fun `reading user via invalid id, results unsuccessfully`() {
            transaction {
                val userDAO = initUsers()
                assertEquals(null, userDAO.getUser(32))
                deinitUsers(userDAO)
            }
        }

        @Test
        fun `reading user via valid id, results successfully`() {
            transaction {
                val userDAO = initUsers()
                assertEquals(user2, userDAO.getUser(user2.id))
                deinitUsers(userDAO)
            }
        }
    }

    @Nested
    inner class DeleteUsers {

        @Test
        fun `deleting invalid user, results unsuccessfully`() {
            transaction {
                val userDAO = initUsers()
                assertEquals(2, userDAO.getAllUser().size)
                userDAO.deleteUser("invalidEmail")
                assertEquals(2, userDAO.getAllUser().size)
                deinitUsers(userDAO)
            }
        }

        @Test
        fun `deleting valid user, results successfully`() {
            transaction {
                val userDAO = initUsers()
                assertEquals(2, userDAO.getAllUser().size)
                userDAO.deleteUser(user1.email)
                assertEquals(1, userDAO.getAllUser().size)
                deinitUsers(userDAO)
            }
        }
    }

    @Nested
    inner class UpdateUsers {

        @Test
        fun `updating valid user, results successfully`() {
            transaction {
                val userDAO = initUsers()
                userDAO.updateUser(user1.username,newUser)
                assertEquals(newUser.email, userDAO.getUser(user1.id)?.email)
                deinitUsers(userDAO)
            }
        }

        @Test
        fun `updating invalid user, results unsuccessfully`() {
            transaction {
                val userDAO = initUsers()
                userDAO.updateUser("Invalid",newUser)
                assertEquals(null, userDAO.getUser(newUser.email))
                deinitUsers(userDAO)
            }
        }
    }
}
