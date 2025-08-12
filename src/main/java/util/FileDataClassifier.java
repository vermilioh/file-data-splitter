package util;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FileDataClassifier {
    private List<String> integers = new ArrayList<>();
    private List<String> floats = new ArrayList<>();
    private List<String> strings = new ArrayList<>();

    private ParsedArguments arguments;

    public FileDataClassifier(ParsedArguments arguments) {
        this.arguments = arguments;
    } // конструктор

    public void readInputFiles() {
        List<String> files = arguments.getInputFiles(); // получили пути ко Всем файлам из CLI(input)

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

    public void printStats() {
        if (arguments.isShortStats()) {
            printShortStats();
        }
        if (arguments.isFullStats()) {
            printFullStats();
        }
    }

    private void printShortStats() {
        System.out.println("Краткая статистика:");
        System.out.println("Количество целых чисел: " + integers.size());
        System.out.println("Количество вещественных чисел: " + floats.size());
        System.out.println("Количество строк: " + strings.size());
    }

    private void printFullStats() {
        System.out.println("Полная статистика:");

        System.out.println("Количество целых чисел: " + integers.size());

        List<Integer> numbers = integers.stream()
                .map(Integer::parseInt)
                .toList();
        Integer min = Collections.min(numbers);
        Integer max = Collections.max(numbers);

        Integer sum = 0;
        for (Integer n : numbers) {
            sum += n;
        }

        float avg = (float) sum / numbers.size();

        System.out.println("Минимальное значение: " + min);
        System.out.println("Максимальное значение: " + max);
        System.out.println("Сумма всех целых чисел: " + sum);
        System.out.println("Среднее всех целых чисел: " + avg);

        System.out.println();

//        System.out.println("Количество вещественных чисел: " + floats.size());
//        System.out.println("Минимальное значение: " + integers.size());
//        System.out.println("Максимальное значение: " + integers.size());
//        System.out.println("Сумма всех вещ. чисел: " + integers.size());
//        System.out.println("Среднее всех вещ. чисел: " + integers.size());

        System.out.println();

        System.out.println("Количество строк: " + strings.size());
        System.out.println("Размер самой короткой строки: " + strings.size());
        System.out.println("Размер самой длинной строки: " + strings.size());
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
