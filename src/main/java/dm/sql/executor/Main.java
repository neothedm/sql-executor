package dm.sql.executor;

import dm.sql.executor.config.Configuration;
import dm.sql.executor.config.ConfigurationReader;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        Configuration configuration = ConfigurationReader.read(args);

        Executor executor = new Executor(configuration);

        executor.execute(configuration.getQuery(), configuration.getFormatter());
    }
}
