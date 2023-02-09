package Helpers

import Core.Model.Message
import Core.Model.PostgreSQL.Messages
import Core.Model.PostgreSQL.Users
import Core.Model.Repository.MessageDAO
import Core.Model.Repository.UserDAO
import Core.Model.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transactionManager
import org.joda.time.DateTime

/**
 * mocks for testing
 */
val started = DateTime.parse("2020-06-11T05:59:27.258Z")
fun connectLocalDatabase(): Database {
    return Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
}
fun disconnectLocalDatabase(db: Database) {
    TransactionManager.resetCurrent(db.transactionManager)
    TransactionManager.closeAndUnregister(db)
}

val users = arrayListOf<User>(
    User(id = 1,
        username = "test",
        password = "test123",
        email = "test1@apple.com",
        created = DateTime.now()),
    User(id = 2,
        username = "test1",
        password = "test345",
        email = "test2@apple.com",
        created = DateTime.now()) ,
)

val newUser = User(id = 1,
    username = "test",
    password = "test324",
    email = "updatedTest@apple.com",
    created = DateTime.now() )

val onlineUser = User(id = 1,
    username = "onlineMock",
    password = "123",
    email = "online@apple.com",
    created = started)

val messages = arrayListOf<Message>(
    Message(id = 1,
        text = "hello",
        userId = 1,
        created = started),
    Message(id = 2,
        text = "how are you",
        userId = 1,
        created = started),
    Message(id = 3,
        text = "amazing",
        userId = 1,
        created = started),
    Message(id = 4,
        text = "hello",
        userId = 2,
        created = started),
    Message(id = 5,
        text = "Im grand",
        userId = 2,
        created = started),
    Message(id = 6,
        text = "alright cya",
        userId = 2,
        created = started)
)

val newMsg = Message(id = 1,
    text = "update Message",
    userId = 1,
    created = started)

val onlineMessage = Message(id = 1,
    text = "online Message",
    userId = 1,
    created = started)

internal fun initUsers(): UserDAO {
    SchemaUtils.create(Users)
    val userDAO = UserDAO()
    userDAO.saveUser(users[0])
    userDAO.saveUser(users[1])
    return userDAO
}

internal fun deinitUsers(instance : UserDAO){
    instance.deleteUser(users[0].email)
    instance.deleteUser(users[1].email)
}

internal fun initMessages(): MessageDAO {
    SchemaUtils.create(Messages)
    val msgDAO = MessageDAO()
    msgDAO.saveMessage(messages[0])
    msgDAO.saveMessage(messages[1])
    msgDAO.saveMessage(messages[2])
    msgDAO.saveMessage(messages[3])
    msgDAO.saveMessage(messages[4])
    msgDAO.saveMessage(messages[5])
    return msgDAO
}

internal fun deinitMessages(instance : MessageDAO){
    instance.deleteMessage(messages[0].id)
    instance.deleteMessage(messages[1].id)
    instance.deleteMessage(messages[2].id)
    instance.deleteMessage(messages[3].id)
    instance.deleteMessage(messages[4].id)
    instance.deleteMessage(messages[5].id)
}