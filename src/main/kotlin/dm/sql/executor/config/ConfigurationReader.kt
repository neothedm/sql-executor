package dm.sql.executor.config

import dm.sql.executor.Formatter
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream

class ConfigurationReader {

    fun read(args: Array<String>): Configuration {
        val configuration = Configuration()

        for (i in args.indices) {
            when (args[i]) {
                "-s", "--server" -> configuration.server = getValue(args[i], args, i + 1)
                "-d", "--database" -> configuration.database = getValue(args[i], args, i + 1)
                "-u", "--username" -> configuration.username = getValue(args[i], args, i + 1)
                "-p", "--password" -> configuration.password = getValue(args[i], args, i + 1)
                "-t", "--port" -> configuration.port = getValue(args[i], args, i + 1)
                "-q", "--query" -> configuration.query = getValue(args[i], args, i + 1)
                "-m", "--mode" -> configuration.queryMode = QueryMode.valueOf(getValue(args[i], args, i + 1).toUpperCase())
                "-o", "--output" -> configuration.output = getValue(args[i], args, i + 1)
                "-i", "--input" -> configuration.input = getValue(args[i], args, i + 1)
                "-f", "--format" -> configuration.format = getValue(args[i], args, i + 1)
                "-r", "--row-separator" -> configuration.rowSeparator = getValue(args[i], args, i + 1)
                "-c", "--column-separator" -> configuration.columnSeparator = getValue(args[i], args, i + 1)
                "-v", "--verbose" -> configuration.verbose = true
            }
        }

        validate(configuration)

        return configuration
    }

    fun getValue(arg: String, args: Array<String>, index: Int): String {
        if (args.size - 1 < index) {
            throw IllegalArgumentException("Missing required argument for option " + arg)
        }
        return args[index]
    }

    private fun validate(configuration: Configuration) {
        if (configuration.username.isBlank()) {
            throw IllegalArgumentException("No username specified, use -u | --username to set it.")
        }
        if (configuration.server.isBlank()) {
            throw IllegalArgumentException("No host specified, use -s | --server to set it.")
        }
        if (configuration.database.isBlank()) {
            throw IllegalArgumentException("No database url specified, use -d | --database to set it.")
        }
        if (configuration.password.isBlank()) {
            throw IllegalArgumentException("No password specified, use -p | --password to set it.")
        }
        if (configuration.input.isNotBlank()) {
            configuration.query = File(configuration.input).readText()
        }
        if (configuration.query.isBlank()) {
            throw IllegalArgumentException("No query specified, use -q | --query to set it.")
        }
        if (configuration.port.isBlank()) {
            throw IllegalArgumentException("No query specified, use -t | --port to set it.")
        }

        if (configuration.output != null) {
            val output = File(configuration.output)
            val printStream = PrintStream(FileOutputStream(output))
            configuration.printStream = printStream
        }

        if (configuration.rowSeparator.isNotEmpty() || configuration.columnSeparator.isNotEmpty()) {
            configuration.formatter = Formatter.DsvFormatter(configuration.printStream, configuration.rowSeparator, configuration.columnSeparator)
        } else {
            when (configuration.format) {
                "csv" ->
                    configuration.formatter = Formatter.CsvFormatter(configuration.printStream)
                "tsv" ->
                    configuration.formatter = Formatter.TsvFormatter(configuration.printStream)
                else ->
                    throw IllegalArgumentException("Only tsv or csv formats are supported or use -r | --row-separator or -c | --column-separator for a custom setup ")
            }
        }
    }
}
