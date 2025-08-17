package util;


public class Main {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Нет файлов для обработки. Завершаюсь...");
        } else {
            ParsedArguments parsed = new ArgumentsParser().parseFromCli(args);

            FileDataClassifier classifier = new FileDataClassifier(parsed);
            classifier.readInputFiles(); // читаем переданные пользователем файлы
            classifier.printStatsByFlags();

            OutputFileWriter out = new OutputFileWriter(classifier.getArguments());
            out.writeAll(classifier.getIntegers(), classifier.getFloats(),classifier.getStrings());

        }
    }
}

