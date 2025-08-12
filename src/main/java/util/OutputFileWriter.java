package util;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class OutputFileWriter implements AutoCloseable {
    private boolean mode;
    private Path outputPath;
    private String prefix;

    public OutputFileWriter(ParsedArguments arguments) {
        String dir = arguments.getOutputPath();
        this.outputPath = dir.isEmpty() ? Path.of(
                "") : Path.of(dir);
        this.mode = arguments.isAppendToFile();
        this.prefix = arguments.getPrefix().isEmpty() ? "" : arguments.getPrefix();
    }


    public void writeAll(List<String> integers, List<String> floats, List<String> strings) {

        writeLines("sample-integers.txt", integers);
        writeLines("sample-floats.txt", floats);
        writeLines("sample-strings.txt", strings);
    }

    private void appendToFile() {

    }

    private void writeLines(String fileName, List<String> lines) {
        if (lines == null || lines.isEmpty()) return;

        String fullFileName = prefix.concat(fileName);
        Path fullPath = outputPath.resolve(fullFileName);

        try {
            Path dir = fullPath.getParent();
            if (dir != null) {
                Files.createDirectories(dir);
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(fullPath.toFile(), mode))) {
                for (String line : lines) {
                    writer.println(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {

    }
}