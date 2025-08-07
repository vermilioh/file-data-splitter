package util;

import java.util.ArrayList;
import java.util.List;

public class ArgumentsParser {

    public ParsedArguments parse(String[] args) {

        ParsedArguments parsedArguments = new ParsedArguments();
        List<String> inputFiles = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-a":
                    parsedArguments.setAppendToFile(true);
                    break;

                case "-s":
                    parsedArguments.setShortStats(true);
                    break;

                case "-f":
                    parsedArguments.setFullStats(true);
                    break;

                case "-o":
                    if ((i + 1) >= args.length) {
                        System.out.println("Error!: Укажите путь для сохранения файла");
                    } else {
                        parsedArguments.setOutputPath(args[++i]);
                    }
                    break;

                case "-p":
                    if ((i + 1) >= args.length) {
                        System.out.println("Error!: Укажите префикс");
                    } else {
                        parsedArguments.setPrefix(args[++i]);
                    }
                    break;

                default:
                    if (args[i].startsWith("-")) {
                        System.out.println("Ошибка: опции " + args[i] + " не существует");
                        // TODO: возможно, стоит выбросить исключение или завершить программу?
                    } else {
                        inputFiles.add(args[i]);
                    }

            }
        }
        parsedArguments.setInputFiles(inputFiles);
        return parsedArguments;
    }

}
