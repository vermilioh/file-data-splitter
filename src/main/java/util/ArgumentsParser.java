package util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ArgumentsParser {

    public ParsedArguments parseFromCli(String[] args) {

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
                    if (isNextInvalid(args, i)) {
                        System.out.println("Отсутствует значение для -o, сохраню файлы в текущую папку");
                    } else {
                        parsedArguments.setOutputPath(args[++i]);
                    }
                    break;

                case "-p":
                    if (isNextInvalid(args, i)) {
                        System.out.println("Отсутствует значение для -p, в названии не будет префикса");
                    } else {
                        parsedArguments.setPrefix(args[++i]);
                    }
                    break;

                default:
                    if (args[i].startsWith("-")) {
                        System.err.println("Ошибка: опции " + args[i] + " не существует");
                    } else {
                        inputFiles.add(args[i]);
                    }
            }
        }
        parsedArguments.setInputFilesPaths(inputFiles);
        return parsedArguments;
    }

    private boolean isNextInvalid(String[] args, int i) {
        if (i + 1 >= args.length || args[i + 1].startsWith("-")) {
            return true;
        }
        Path path = Path.of(args[i + 1]);
        return Files.exists(path) && !Files.isDirectory(path);
    }

}
