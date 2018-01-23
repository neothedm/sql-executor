package dm.sql.executor.config;

import dm.sql.executor.Formatter;

import java.io.*;
import java.nio.file.Files;

public class ConfigurationReader {

    public static Configuration read(String... args) throws IOException {
        Configuration configuration = new Configuration();

        for (int i = 0; i < args.length; i++) {
            String option = args[i];

            String value;
            switch (option) {
                case "-s":
                case "--server":
                    value = getValue(args[i], args, ++i);
                    configuration.setServer(value);
                    break;
                case "-d":
                case "--database":
                    value = getValue(args[i], args, ++i);
                    configuration.setDatabase(value);
                    break;
                case "-u":
                case "--username":
                    value = getValue(args[i], args, ++i);
                    configuration.setUsername(value);
                    break;
                case "-p":
                case "--password":
                    value = getValue(args[i], args, ++i);
                    configuration.setPassword(value);
                    break;
                case "-t":
                case "--port":
                    value = getValue(args[i], args, ++i);
                    configuration.setPort(value);
                    break;
                case "-q":
                case "--query":
                    value = getValue(args[i], args, ++i);
                    configuration.setQuery(value);
                    break;
                case "-m":
                case "--mode":
                    value = getValue(args[i], args, ++i);
                    if ("update".equalsIgnoreCase(value)) {
                        configuration.setQueryMode(QueryMode.UPDATE);
                    } else {
                        configuration.setQueryMode(QueryMode.SELECT);
                    }
                    break;
                case "-o":
                case "--output":
                    value = getValue(args[i], args, ++i);
                    configuration.setOutput(value);
                    break;
                case "-i":
                case "--input":
                    value = getValue(args[i], args, ++i);
                    configuration.setInput(value);
                    break;
                case "-f":
                case "--format":
                    value = getValue(args[i], args, ++i);
                    configuration.setFormat(value);
                    break;
                case "-r":
                case "--row-separator":
                    value = getValue(args[i], args, ++i);
                    configuration.setRowSeparator(value);
                    break;
                case "-c":
                case "--column-separator":
                    value = getValue(args[i], args, ++i);
                    configuration.setColumnSeparator(value);
                    break;
                case "-v":
                case "--verbose":
                    configuration.setVerbose(true);
                    break;
            }
        }

        validate(configuration);

        return configuration;
    }

    private static String getValue(String arg, String[] args, int index) {
        if (args.length - 1 < index) {
            throw new IllegalArgumentException("Missing required argument for option " + arg);
        }
        return args[index];
    }

    private static void validate(Configuration configuration) throws IOException {
        if (configuration.getUsername() == null) {
            throw new IllegalArgumentException("No username specified, use -u | --username to set it.");
        }
        if (configuration.getServer() == null) {
            throw new IllegalArgumentException("No host specified, use -s | --server to set it.");
        }
        if (configuration.getDatabase() == null) {
            throw new IllegalArgumentException("No database url specified, use -d | --database to set it.");
        }
        if (configuration.getPassword() == null) {
            throw new IllegalArgumentException("No password specified, use -p | --password to set it.");
        }

        if (configuration.getInput() != null) {
            configuration.setQuery(new String(Files.readAllBytes(new File(configuration.getInput()).toPath()), "UTF-8"));
        }

        if (configuration.getQuery() == null) {
            throw new IllegalArgumentException("No query specified, use -q | --query to set it.");
        }
        if (configuration.getPort() == null) {
            throw new IllegalArgumentException("No query specified, use -t | --port to set it.");
        }

        if (configuration.getOutput() != null) {
            File output = new File(configuration.getOutput());
            PrintStream printStream = new PrintStream(new FileOutputStream(output));
            configuration.setPrintStream(printStream);
        } else {
            configuration.setPrintStream(System.out);
        }

        if (configuration.getRowSeparator() != null || configuration.getColumnSeparator() != null) {
            configuration.setFormatter(new Formatter.DsvFormatter(configuration.getPrintStream(), configuration.getRowSeparator(), configuration.getColumnSeparator()));
        } else {
            switch (configuration.getFormat()) {
                case "csv":
                    configuration.setFormatter(new Formatter.CsvFormatter(configuration.getPrintStream()));
                    break;
                case "tsv":
                    configuration.setFormatter(new Formatter.TsvFormatter(configuration.getPrintStream()));
                    break;
                default:
                    throw new IllegalArgumentException("Only tsv or csv formats are supported or use -r | --row-separator or -c | --column-separator for a custom setup ");
            }
        }
    }
}
