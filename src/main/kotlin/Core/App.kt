package Core

import Services.DBConfig
import Services.javalinConfig
/**
 * executes the application
 */
fun main() {
    DBConfig().getDbConnection()
    javalinConfig().startJavalinService()
}
