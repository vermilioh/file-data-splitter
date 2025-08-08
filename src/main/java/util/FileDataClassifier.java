package util;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileDataClassifier {
    List<Long> integers = new ArrayList<>();
    List<Double> floats = new ArrayList<>();
    List<String> strings = new ArrayList<>();

    ParsedArguments arguments;

    public FileDataClassifier(ParsedArguments arguments) {
        this.arguments = arguments;
    } // конструктор

    public void readInputFiles() {
        List<String> files = arguments.getInputFiles(); // получили пути ко Всем записанным файлам
        // 1. Запускаем цикл по каждому пути из files:
        //    - проверяем, существует ли файл (Files.exists(Path))
        //    - если не существует: сообщение в консоль и continue
        //    - если существует: вызвать приватный метод readSingleFile(Path filePath)

        // readSingleFile:
        //    - открываем файл (Scanner или BufferedReader)
        //    - читаем построчно (while/for)
        //    - для каждой строки вызываем getTypeOfString(line), который
        //      вернёт enum (INTEGER, FLOAT, STRING) или символ ('I', 'F', 'S')
        //    - в зависимости от результата — добавляем в соответствующий список:
        //      integers, floats или strings

        // 2. После обработки всех файлов:
        //    - если включена статистика (shortStats или fullStats):
        //      вызвать calculateStats() — он посчитает и сразу выведет, или вернёт объект со значениями

        // TODO: если включена статистика: вызвать calculateStats() после цикла
        // TODO: создать и реализовать readSingleFile(Path filePath)
        // TODO: создать и реализовать getTypeOfString(line)

        // TODO: создать и реализовать calculateStats()


        Path file;

    }

    private boolean isInteger(String str) {
        return false;
    }

    private boolean isDouble(String str) {

        return false;
    }



}
