package util;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("нет входных файлов");
            //TODO: в дальнейшем обработать как исключение?
        } else {
            ArgumentsParser parser = new ArgumentsParser();
            ParsedArguments parsed = parser.parse(args); // парсер раскидал CLI по полям parsed
            FileDataClassifier classifier = new FileDataClassifier(parsed);
            classifier.readInputFiles(); // читаем переданные файлы
            OutputFileWriter out = new OutputFileWriter(classifier.getArguments());
            out.writeInt(classifier.getIntegers());
            out.writeFloat(classifier.getFloats());
            out.writeString(classifier.getStrings());
        }
    }
}

