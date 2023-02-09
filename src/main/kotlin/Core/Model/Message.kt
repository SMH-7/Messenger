package Core.Model

import org.joda.time.DateTime

/**
 * Structure for message
 */
data class Message (var id: Int,
               var text: String,
               var userId: Int,
               var created: DateTime)

