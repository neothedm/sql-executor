package dm.sql.executor;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Formatter {

    protected final PrintStream printStream;

    public Formatter(PrintStream printStream) {
        this.printStream = printStream;
    }

    public abstract void out(ResultSet resultSet) throws SQLException;

    public abstract void out(int count) throws SQLException;

    public static class DsvFormatter extends Formatter {

        private final String rowSeperator;
        private final String columnSeperator;

        public DsvFormatter(PrintStream printStream, String rowSeperator, String columnSeparator) {
            super(printStream);
            this.rowSeperator = rowSeperator;
            this.columnSeperator = columnSeparator;
        }

        @Override
        public void out(ResultSet resultSet) throws SQLException {
            int i = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                String[] values = new String[i];
                for (int i1 = 0; i1 < i; i1++) {
                    values[i1] = resultSet.getString(i1 + 1);
                }
                printStream.print(String.join(rowSeperator, values));
                printStream.print(columnSeperator);
            }
        }

        @Override
        public void out(int count) throws SQLException {
            printStream.print(count);
        }
    }

    public static class CsvFormatter extends DsvFormatter {

        public CsvFormatter(PrintStream printStream) {
            super(printStream, ",", "\n");
        }
    }

    public static class TsvFormatter extends DsvFormatter {

        public TsvFormatter(PrintStream printStream) {
            super(printStream, "\t", "\n");
        }
    }
}
