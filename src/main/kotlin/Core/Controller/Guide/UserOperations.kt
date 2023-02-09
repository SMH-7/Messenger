package Core.Controller.Guide

import io.javalin.http.Context

/**
 * All methods for user controller
 */
interface UserOperations {
    fun addUser(ctx: Context)
    fun deleteUser(ctx: Context)
    fun updateUser(ctx: Context)
    fun getUsers(ctx: Context)
    fun getUser(ctx: Context)
    fun getUserById(ctx: Context)
}