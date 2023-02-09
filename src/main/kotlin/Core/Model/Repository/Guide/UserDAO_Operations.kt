package Core.Model.Repository.Guide

import Core.Model.User

/**
 * All methods for user repository
 */
interface UserDAO_Operations {
    fun saveUser(user: User) : Int?
    fun deleteUser(byEmail: String): Int
    fun updateUser(withName: String, user: User) : Int
    fun getAllUser(): ArrayList<User>
    fun getUser(byEmail: String) : User?
    fun getUser(byUserId: Int) : User?
}