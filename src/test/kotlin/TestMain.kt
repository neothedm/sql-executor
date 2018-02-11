import dm.sql.executor.main

fun main(args: Array<String>) {
    main(arrayOf("-s", "dbaj1.atypon.com", "-t", "1433", "-d", "pericles_trunk", "-u", "pericles", "-p", "pericles20161011", "-f", "csv", "-c", "\n", "-r", "|", "-i", "/tmp/query"))
}