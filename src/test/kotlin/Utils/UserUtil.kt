package Utils

import Core.Model.User
import Helpers.started
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import kong.unirest.Unirest

/**
 * Utility class for user API
 */
class UserUtil (private val origin: String) {

    private val path = "$origin/api/users"

    val updatedUser = User(id = 1,
        username = "updatedTest",
        password = "updatedTest324",
        email = "updatedTest@apple.com",
        created = started)

    fun getUsers(): HttpResponse<JsonNode> = Unirest.get(path).asJson()

    fun getUser(byId: Int) : HttpResponse<JsonNode> = Unirest.get("$path/id/${byId}").asJson()

    fun getUser(byEmail : String) : HttpResponse<JsonNode> = Unirest.get("$path/mail/${byEmail}").asJson()

    fun deleteUser(mail: String) : HttpResponse<JsonNode> = Unirest.delete("$path/mail/$mail").asJson()

    fun addUser (user: User): HttpResponse<JsonNode> {
        return Unirest.post(path)
            .body("""
                {
                    "id": ${user.id},
                    "username":"${user.username}",
                    "password":"${user.password}",
                    "email":"${user.email}",
                    "created":"${user.created}"
                }
            """.trimIndent())
            .asJson()
    }

    fun updateUser (id : Int, username: String) : HttpResponse<JsonNode> {
        return Unirest.patch("$path/name/$username")
            .body("""
                {
                   "username":"$username",
                   "password":"${updatedUser.password}",
                   "email":"${updatedUser.email}",
                   "created":"$started"
                }
            """.trimIndent())
            .asJson()
    }

}