package Services
import okhttp3.internal.concurrent.TaskRunner
import org.jetbrains.exposed.sql.Database


/**
 * This handles connection to the database
 */
class DBConfig{

    fun getDbConnection() : Database {
        val PGUSER = "nkpitquz"
        val PGPASSWORD = "JZyBfEIASepHEX5uMdkYNMF30R4bUjpO"
        val PGHOST = "kandula.db.elephantsql.com"
        val PGPORT = "5432"
        val PGDATABASE = "nkpitquz"

        val url = "jdbc:postgresql://$PGHOST:$PGPORT/$PGDATABASE"
        val dbConfig = Database.connect(url,
            driver="org.postgresql.Driver",
            user = PGUSER,
            password = PGPASSWORD
        )
        TaskRunner.logger.info{"db url - connection: " + dbConfig.url}
        return dbConfig
    }
}