package Utils

import io.javalin.http.Context

/**
 * Parameter for user and message
 */
object Params {

    const val USER_ID = "user-id"
    const val USER_MAIL = "user-mail"
    const val USER_NAME = "user-name"
    const val MSG_ID = "msg-id"

    fun parseUserId(ctx: Context) = ctx.pathParam(USER_ID).toInt()
    fun parseUserEmail(ctx: Context) = ctx.pathParam(USER_MAIL)
    fun parseUserName(ctx: Context) = ctx.pathParam(USER_NAME)
    fun parseMsgId(ctx: Context) = ctx.pathParam(MSG_ID).toInt()
}