package Core.Controller

import Core.Model.Repository.UserDAO
import Core.Model.User
import Utils.JSONUtils.jsonToObject
import Utils.Params.parseUserEmail
import Utils.Params.parseUserId
import Utils.Params.parseUserName
import io.javalin.http.Context
import io.javalin.plugin.openapi.annotations.*

/**
 * This class controls the data flow and handles errors for the user API.
 */
object UserController {

    private val userDao = UserDAO()

        fun addUser(ctx: Context) {
        val user : User = jsonToObject(ctx.body())
        val userId = userDao.saveUser(user)
        if (userId != null) {
            user.id = userId
            ctx.json(user)
            ctx.status(201)
        } else {
            ctx.status(500)
            ctx.json(mapOf("error" to "couldn't save user to database"))
        }
    }

    fun deleteUser(ctx: Context){
        val userId = parseUserEmail(ctx)
        if (userDao.deleteUser(userId) != 0)
            ctx.status(204)
        else
            ctx.status(404)
            ctx.json(mapOf("error" to "user not found"))

    }


    fun updateUser(ctx: Context){
        val foundUser : User = jsonToObject(ctx.body())
        if ((userDao.updateUser(parseUserName(ctx), foundUser)) != 0)
            ctx.status(204)
        else
            ctx.status(404)
            ctx.json(mapOf("error" to "couldn't update user"))
    }

    fun getUsers(ctx: Context) {
        val returnedUsers =  userDao.getAllUser()
        if (returnedUsers.size != 0) {
            ctx.json(returnedUsers)
            ctx.status(200)
        }else {
            ctx.status(404)
            ctx.json(mapOf("error" to "error fetching users"))
        }
    }

    fun getUser(ctx: Context) {
        val returnedUser =  userDao.getUser(parseUserEmail(ctx))
        if (returnedUser != null) {
            ctx.json(returnedUser)
            ctx.status(200)
        }else {
            ctx.status(404)
            ctx.json(mapOf("error" to "error fetching user"))
        }
    }

    fun getUserById(ctx: Context) {
        val returnedUser =  userDao.getUser(parseUserId(ctx))
        if (returnedUser != null) {
            ctx.json(returnedUser)
            ctx.status(200)
        }else {
            ctx.status(404)
            ctx.json(mapOf("error" to "error fetching user"))
        }
    }
}