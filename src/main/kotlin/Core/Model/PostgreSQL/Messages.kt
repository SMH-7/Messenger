package Core.Model.PostgreSQL

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

/**
 * Message table in the database
 */
object Messages : Table("messages"){
    val id = integer("id").autoIncrement().primaryKey()
    val text = varchar("text", 255)
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val created = datetime("created")
}
