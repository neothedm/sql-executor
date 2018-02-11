package dm.sql.executor

import java.io.PrintStream
import java.sql.ResultSet

abstract class Formatter(var printStream: PrintStream) {

    abstract fun out(resultSet: ResultSet)

    abstract fun out(count: Int)

    open class DsvFormatter(printStream: PrintStream, var rowSeperator: String, private var columnSeparator: String) : Formatter(printStream) {

        override fun out(resultSet: ResultSet) {
            val i = resultSet.metaData.columnCount
            while (resultSet.next()) {

                val values = Array(i, {""})


                for (i1 in 0..i) {
                    values[i1] = resultSet.getString(i1 + 1)
                }

                printStream.print(values.joinToString(rowSeperator))
                printStream.print(columnSeparator)
            }
        }

        override fun out(count: Int) {
            printStream.print(count)
        }
    }

    class CsvFormatter(printStream: PrintStream) : DsvFormatter(printStream, ",", "\n")

    class TsvFormatter(printStream: PrintStream) : DsvFormatter(printStream, "\t", "\n")
}
