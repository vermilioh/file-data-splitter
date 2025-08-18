package util;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileDataClassifier {
    private final List<String> integers = new ArrayList<>();
    private final List<String> floats = new ArrayList<>();
    private final List<String> strings = new ArrayList<>();

    private final ParsedArguments arguments;

    public FileDataClassifier(ParsedArguments arguments) {
        this.arguments = arguments;
    }

    public void readInputFiles() {
        List<String> files = arguments.getInputFilesPaths();

        for (String line : files) {
            Path path = Path.of(line);
            if (Files.exists(path)) {
                readSingleFile(path);
            } else {
                System.err.println("Ошибка! файла " + path + " не существует");
            }
        }
    }

    private void readSingleFile(Path filePath) {
        try (Scanner scanner = new Scanner(filePath.toFile())) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isBlank()) continue;
                DataType type = detectType(line);
                switch (type) {
                    case INTEGER -> integers.add(line);
                    case FLOAT -> floats.add(line);
                    case STRING -> strings.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + filePath + e.getMessage());
        }

    }

    private DataType detectType(String line) {

        try {
            Long.parseLong(line);
            return DataType.INTEGER;
        } catch (NumberFormatException exception1) {
            try {
                Double.parseDouble(line);
                return DataType.FLOAT;
            } catch (NumberFormatException exception2) {
                return DataType.STRING;
            }
        }
    }

    public void printStatsByFlags() {
        if (integers.isEmpty() && floats.isEmpty() && strings.isEmpty()) {
            System.err.println("Нет данных для статистики");
            return;
        }
        if (arguments.isFullStats()) {
            printFullStats();
        }
        if (arguments.isShortStats()) {
            printShortStats();
        }

    }

    private void printShortStats() {
        System.out.println("Краткая статистика");
        System.out.println("Количество целых чисел: " + integers.size());
        System.out.println("Количество вещественных чисел: " + floats.size());
        System.out.println("Количество строк: " + strings.size());
    }

    private void printFullStats() {
        System.out.println("Полная статистика\n");

        if (!integers.isEmpty()) {
            printIntStats();
            System.out.println();
        } else {
            System.out.println("Количество целых чисел: 0\n");
        }

        if (!floats.isEmpty()) {
            printFloatStats();
            System.out.println();
        } else {
            System.out.println("Количество вещественных чисел: 0\n");
        }

        if (!strings.isEmpty()) {
            printStringStats();
        } else {
            System.out.println("Количество строк: 0\n");
        }

    }

    private void printIntStats() {
        IntStats intStats = new IntStats();
        intStats.calculateIntStats();

        System.out.println("Всего целых чисел: " + intStats.count);
        System.out.println("Минимальное значение: " + intStats.min);
        System.out.println("Максимальное значение: " + intStats.max);
        System.out.println("Сумма всех целых чисел: " + intStats.sum);
        System.out.println("Среднее всех целых чисел: " + intStats.avg);

    }

    private void printFloatStats() {
        FloatStats floatStats = new FloatStats();
        floatStats.calculateFloatStats();

        System.out.println("Всего вещ. чисел: " + floatStats.count);
        System.out.println("Минимальное значение: " + floatStats.min);
        System.out.println("Максимальное значение: " + floatStats.max);
        System.out.println("Сумма всех вещ. чисел: " + floatStats.sum);
        System.out.println("Среднее всех вещ. чисел: " + floatStats.avg);
    }

    private void printStringStats() {
        StringStats stringStats = new StringStats();
        stringStats.calculateStringStats();

        System.out.println("Всего строк: " + stringStats.count);
        System.out.println("Размер самой короткой строки: " + stringStats.minLength);
        System.out.println("Размер самой длинной строки: " + stringStats.maxLength);
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

    private class IntStats {
        int count;
        long min;
        long max;
        long sum;
        double avg;

        void calculateIntStats() {
            min = Long.parseLong(integers.getFirst());
            max = min;
            count = integers.size();

            for (String x : integers) {
                long num = Long.parseLong(x);
                if (num > max) max = num;
                if (num < min) min = num;
                sum += num;
            }
            avg = (double) sum / count;
        }
    }

    private class FloatStats {
        int count;
        double min;
        double max;
        double sum;
        double avg;

        void calculateFloatStats() {
            min = Double.parseDouble(floats.getFirst());
            max = min;
            sum = 0.0;
            count = floats.size();

            for (String x : floats) {
                double num = Double.parseDouble(x);
                if (num > max) max = num;
                if (num < min) min = num;
                sum += num;
            }
            avg = sum / count;
        }
    }

    private class StringStats {
        int count = strings.size();
        int minLength;
        int maxLength;

        void calculateStringStats() {
            minLength = strings.getFirst().length();
            maxLength = minLength;

            for (String str : strings) {
                int currentLength = str.length();
                if (currentLength > maxLength) {
                    maxLength = currentLength;
                } else if (currentLength < minLength) {
                    minLength = currentLength;
                }
            }
        }
    }
}
