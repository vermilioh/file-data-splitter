package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParsedArguments {
    private List<String> inputFiles = new ArrayList<>();

    private boolean appendToFile;
    private boolean shortStats;
    private boolean fullStats;

    private String outputPath = "";
    private String prefix = "";


//    public void printParsedArguments(){
//        System.out.println(appendToFile);
//        System.out.println(shortStats);
//        System.out.println(fullStats);
//
//        for (String inputFile : inputFiles) {
//            System.out.println(inputFile);
//        }
//    }

    public boolean isAppendToFile() {
        return appendToFile;
    }

    public void setAppendToFile(boolean appendToFile) {
        this.appendToFile = appendToFile;
    }

    public void setInputFiles(List<String> inputFiles) {
        this.inputFiles = inputFiles;
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }


    public boolean isShortStats() {
        return shortStats;
    }

    public void setShortStats(boolean shortStats) {
        this.shortStats = shortStats;
    }

    public boolean isFullStats() {
        return fullStats;
    }

    public void setFullStats(boolean fullStats) {
        this.fullStats = fullStats;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


}
