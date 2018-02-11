package dm.sql.executor

import dm.sql.executor.config.ConfigurationReader

fun main(args: Array<String>) {
    val configuration = ConfigurationReader().read(args)

    val executor = Executor(configuration)

    executor.execute(configuration.query, configuration.formatter)
}