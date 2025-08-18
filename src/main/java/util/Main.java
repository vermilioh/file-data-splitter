package util;

public class Main {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Обрабатывать нечего. Завершение работы...");
            return;
        }

        ParsedArguments parsed = new ArgumentsParser().parseFromCli(args);

        if (parsed.getInputFilesPaths().isEmpty()) {
            System.err.println("Нет входных файлов. Завершение работы...\n");
            return;
        }

        FileDataClassifier classifier = new FileDataClassifier(parsed);
        classifier.readInputFiles();
        classifier.printStatsByFlags();

        OutputFileWriter out = new OutputFileWriter(classifier.getArguments());
        out.writeAll(classifier.getIntegers(), classifier.getFloats(), classifier.getStrings());
    }
}



