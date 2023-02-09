package Core.Model.PostgreSQL

import org.jetbrains.exposed.sql.Table

/**
 * User table in the database
 */
object Users : Table("users") {
    val id = integer("id").autoIncrement().primaryKey()
    val username = varchar("username", 50)
    val password = varchar("password", 50)
    val email = varchar("email", 255)
    val created = datetime("created")
}
