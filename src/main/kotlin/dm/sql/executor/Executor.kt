package dm.sql.executor

import dm.sql.executor.config.Configuration
import dm.sql.executor.config.QueryMode
import java.sql.DriverManager
import java.util.regex.Pattern

private var PATTERN_GO = Pattern.compile("\n\\s*(GO|go|Go)\\s*")
private var initialized: Boolean = false

class Executor(private val configuration: Configuration) {

    fun execute(query: String, formatter: Formatter) {
        initialize()
        when (configuration.queryMode) {
            QueryMode.SELECT -> _execute(query, formatter)
            QueryMode.UPDATE -> _update(query, formatter)
        }
    }

    private fun initialize() {
        if (initialized)
            return

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        initialized = true
    }

    private fun process(query: String): List<String> {
        return PATTERN_GO.split(query).toList()
    }

    private fun getUrl(): String {
        return String.format("jdbc:jtds:sqlserver://%s:%s/%s;tds=8.0", configuration.server, configuration.port, configuration.database)
    }

    private fun _execute(query: String, formatter: Formatter) {
        DriverManager.getConnection(getUrl(), configuration.username, configuration.password).use { c ->
            process(query).filter{ s -> s.trim().isEmpty() }.forEach { c.createStatement().use { s -> formatter.out(s.executeQuery(it)) } }
        }
    }

    private fun _update(query: String, formatter: Formatter) {
        DriverManager.getConnection(getUrl(), configuration.username, configuration.password).use { c ->
            process(query).filter{ s -> s.trim().isEmpty() }.forEach { c.createStatement().use { s -> formatter.out(s.executeUpdate(it)) } }
        }
    }
}
