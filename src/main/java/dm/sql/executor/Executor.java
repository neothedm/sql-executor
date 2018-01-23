package dm.sql.executor;

import dm.sql.executor.config.Configuration;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Executor {

    private final Configuration configuration;
    private volatile boolean initialized;

    public Executor(Configuration configuration) {
        this.configuration = configuration;
    }

    public void execute(String query, Formatter formatter) throws SQLException {
        switch (configuration.getQueryMode()) {
            case SELECT:
                _execute(query, formatter);
                break;
            case UPDATE:
                _update(query, formatter);
                break;
            default:
                throw new IllegalArgumentException("Unsupported query mode");
        }
    }

    public void _execute(String query, Formatter formatter) throws SQLException {
        initialize();

        List<String> queries = process(query);

        try (Connection connection = DriverManager.getConnection(getUrl(), configuration.getUsername(), configuration.getPassword())) {

            for (String s : queries) {

                if (s.trim().isEmpty())
                    return;

                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(s);
                formatter.out(resultSet);
                stmt.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final Pattern PATTERN_GO = Pattern.compile("\n\\s*(GO|go|Go)\\s*");

    private List<String> process(String query) {
        return Arrays.asList(PATTERN_GO.split(query));
    }

    public void _update(String query, Formatter formatter) throws SQLException {
        initialize();

        List<String> queries = process(query);

        try (Connection connection = DriverManager.getConnection(getUrl(), configuration.getUsername(), configuration.getPassword())) {
            for (String s : queries) {
                Statement stmt = connection.createStatement();
                formatter.out(stmt.executeUpdate(s));
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initialize() {
        if (initialized)
            return;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        initialized = true;
    }

    private String getUrl() {
        return String.format("jdbc:jtds:sqlserver://%s:%s/%s;tds=8.0", configuration.getServer(), configuration.getPort(), configuration.getDatabase());
    }
}
