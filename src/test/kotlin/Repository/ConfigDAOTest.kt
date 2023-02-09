package Repository

import Core.Model.PostgreSQL.Messages
import Core.Model.PostgreSQL.Users
import Core.Model.Repository.MessageDAO
import Core.Model.Repository.UserDAO
import Helpers.*
import Helpers.deinitMessages
import Helpers.deinitUsers
import Helpers.initMessages
import Helpers.initUsers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach

/**
 * Handles database connection for unit test
 */
open class ConfigDAOTest {

    companion object {

        private lateinit var db: Database

        @BeforeAll
        @JvmStatic
        internal fun setup() {
            db = connectLocalDatabase()
        }

        @AfterAll
        @JvmStatic
        internal fun tearDown() {
            disconnectLocalDatabase(db)
        }
    }
}