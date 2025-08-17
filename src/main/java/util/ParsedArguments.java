package util;

import java.util.ArrayList;
import java.util.List;

public class ParsedArguments {
    private List<String> inputFilesPaths = new ArrayList<>();

    private boolean appendToFile;
    private boolean shortStats;
    private boolean fullStats;

    private String outputPath = "";
    private String prefix = "";



    public boolean isAppendToFile() {
        return appendToFile;
    }

    public void setAppendToFile(boolean appendToFile) {
        this.appendToFile = appendToFile;
    }

    public void setInputFilesPaths(List<String> inputFilesPaths) {
        this.inputFilesPaths = inputFilesPaths;
    }

    public List<String> getInputFilesPaths() {
        return inputFilesPaths;
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
