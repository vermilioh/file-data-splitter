package util;

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
                    if ((i + 1) >= args.length) { // TODO: чета я блин забыла уже как это работает
                        System.out.println("Отсутствует значение для -o, сохраню в текущую папку");
                    } else {
                        if (!args[i + 1].startsWith("-")) {
                            parsedArguments.setOutputPath(args[++i]);
                        }

                    }
                    break;

                case "-p":
                    if ((i + 1) >= args.length) { // TODO: чета я блин забыла уже как это работает
                        System.out.println("Отсутствует значение для опции -p, в названии не будет префикса");
                    } else {
                        if (!args[i + 1].startsWith("-")) {
                            parsedArguments.setPrefix(args[++i]);
                        }
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

}
