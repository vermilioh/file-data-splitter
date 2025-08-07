package util;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("нет входных файлов");
            //TODO: в дальнейшем обработать как исключение?
        } else {
            ArgumentsParser parser = new ArgumentsParser();
            ParsedArguments parsed = parser.parse(args); // парсер раскидал CLI по полям parsed

            FileDataClassifier file1 = new FileDataClassifier();
            file1.readInputFiles(parsed);

        }
    }
}

