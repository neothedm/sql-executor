package dm.sql.executor.config;

import dm.sql.executor.Formatter;

import java.io.PrintStream;

public class Configuration {

    private String server;
    private String database;
    private String username;
    private String query;
    private String output;
    private String input;
    private String format = "tsv";
    private boolean verbose;
    private String password;
    private String port;
    private PrintStream printStream;
    private String rowSeparator;
    private String columnSeparator;
    private Formatter.DsvFormatter formatter;
    private QueryMode queryMode = QueryMode.SELECT;

    public void setServer(String server) {
        this.server = server;
    }

    public String getServer() {
        return server;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    public void setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    public void setRowSeparator(String rowSeparator) {
        this.rowSeparator = rowSeparator;
    }

    public String getRowSeparator() {
        return rowSeparator;
    }

    public void setColumnSeparator(String columnSeparator) {
        this.columnSeparator = columnSeparator;
    }

    public String getColumnSeparator() {
        return columnSeparator;
    }

    public void setFormatter(Formatter.DsvFormatter formatter) {
        this.formatter = formatter;
    }

    public Formatter.DsvFormatter getFormatter() {
        return formatter;
    }

    public void setQueryMode(QueryMode queryMode) {
        this.queryMode = queryMode;
    }

    public QueryMode getQueryMode() {
        return queryMode;
    }
}
