import dm.sql.executor.Main;

import java.io.IOException;
import java.sql.SQLException;

public class TestMain {

    /**
     <set name="database">
     <string name="JDBCServer">localhost</string>
     <string name="JDBCPort">1433</string>
     <string name="dbPrefix">pericles</string>
     <string name="dbSuffix">trunk</string>
     <string name="JDBCUser">pericles</string>
     <string name="JDBCPassword">pericles</string>
     </set>
     */
    public static void main(String[] args) throws SQLException, IOException {
        Main.main(new String[] {"-s", "localhost", "-t", "1433", "-d", "pericles_trunk", "-u", "pericles", "-p", "pericles", "-f", "csv"
        ,"-c", "\n", "-r", "|", "-i", "/tmp/query"});
    }
}
