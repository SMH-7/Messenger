package Core.Model.Repository

import Core.Model.PostgreSQL.Users

import Core.Model.User
import Utils.mapToUser
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

/**
 * Handles transaction for user
 */
class UserDAO {

    fun saveUser(user: User) : Int?{
        return transaction {
            Users.insert {
                it[username] = user.username
                it[password] = user.password
                it[email] = user.email
                it[created] = user.created
            } get Users.id
        }
    }

    fun deleteUser(byEmail: String): Int {
        return transaction{ Users.deleteWhere{
            Users.email eq byEmail
            }
        }
    }

    fun updateUser(withName: String, user: User) : Int {
        return transaction {
            Users.update ({
                Users.username eq withName}) {
                it[password] = user.password
                it[email] = user.email
            }
        }
    }

    fun getAllUser(): ArrayList<User> {
        val userList: ArrayList<User> = arrayListOf()
        transaction {
            Users.selectAll().map {
                userList.add(mapToUser(it)) }
        }
        return userList
    }

    fun getUser(byEmail: String) : User?{
        return transaction {
            Users.select() {
                Users.email eq byEmail}
                .map{mapToUser(it)}
                .firstOrNull()
        }
    }

    fun getUser(byUserId: Int) : User?{
        return transaction {
            Users.select() {
                Users.id eq byUserId}
                .map{mapToUser(it)}
                .firstOrNull()
        }
    }
}