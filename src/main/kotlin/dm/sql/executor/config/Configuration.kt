package dm.sql.executor.config

import dm.sql.executor.Formatter

import java.io.PrintStream

class Configuration {

    var server: String = ""
    var database: String = ""
    var username: String = ""
    var query: String = ""
    var output: String? = null
    var input: String = ""
    var format: String = ""
    var verbose: Boolean = false
    var password: String = ""
    var port: String = ""
    var printStream: PrintStream = System.out
    var rowSeparator: String = ""
    var columnSeparator: String = ""
    lateinit var formatter: Formatter.DsvFormatter
    var queryMode: QueryMode = QueryMode.SELECT
}
