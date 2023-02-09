package Core.Model

import org.joda.time.DateTime

/**
 * Structure for user
 */
data class User ( var id: Int,
                  var username: String,
                  var password: String,
                  var email:    String,
                  var created: DateTime)

