package util;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileDataClassifier {
    private List<String> integers = new ArrayList<>();
    private List<String> floats = new ArrayList<>();
    private List<String> strings = new ArrayList<>();

    private  ParsedArguments arguments;

    public FileDataClassifier(ParsedArguments arguments) {
        this.arguments = arguments;
    } // конструктор

    public void readInputFiles() {
        List<String> files = arguments.getInputFiles(); // получили пути ко Всем записанным файлам

        for (String line : files) {
            Path path = Path.of(line);
            if (Files.exists(path)) {
                readSingleFile(path);
            } else {
                System.out.println("Ошибка! файла не существует"); //Todo: заменить на exception
            }
        }



    }

    private void readSingleFile(Path filePath) {
        try (Scanner scanner = new Scanner(filePath.toFile())) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                DataType type = detectType(line);
                switch (type) {
                    case INTEGER -> integers.add(line);
                    case FLOAT -> floats.add(line);
                    case STRING -> strings.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e.getMessage()); // TODO: разложить для себя FileNotFoundException
        }

    }

    private DataType detectType(String line) {
        String trimmed = line.trim();
        try {
            Long.parseLong(trimmed);
            return DataType.INTEGER;
        } catch (NumberFormatException exception1) {
            try {
                Double.parseDouble(trimmed);
                return DataType.FLOAT;
            } catch (NumberFormatException exception2) {
                return DataType.STRING;
            }
        }
    }

    public List<String> getIntegers() {
        return integers;
    }

    public List<String> getFloats() {
        return floats;
    }

    public List<String> getStrings() {
        return strings;
    }

    public ParsedArguments getArguments() {
        return arguments;
    }
}
