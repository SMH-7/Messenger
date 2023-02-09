package Utils

import Core.Model.Message
import Core.Model.PostgreSQL.Messages
import Core.Model.PostgreSQL.Users
import Core.Model.User
import org.jetbrains.exposed.sql.ResultRow


/**
 * Mapping for models
 */
fun mapToUser(it: ResultRow) = User(
    id = it[Users.id],
    username = it[Users.username],
    password = it[Users.password],
    email = it[Users.email],
    created = it[Users.created]
)

fun mapToMessage(it: ResultRow) = Message(
    id = it[Messages.id],
    text = it[Messages.text],
    userId = it[Messages.userId],
    created = it[Messages.created]
)