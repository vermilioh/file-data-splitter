package util;

import java.util.ArrayList;
import java.util.List;

public class FileDataClassifier {
    List<Long> integers= new ArrayList<>();
    List<Double> floats = new ArrayList<>();
    List<String> strings = new ArrayList<>();

    ParsedArguments arguments = new ParsedArguments();

    public FileDataClassifier(ParsedArguments arguments){
        this.arguments = arguments;
    } // конструктор

    public void readInputFiles(){

    }

}
