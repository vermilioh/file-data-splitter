package util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;

public class OutputFileWriter implements AutoCloseable {
    private boolean mode;
    private Path outputDir;
    private String prefix;

    public OutputFileWriter(ParsedArguments arguments){
        String dir = arguments.getOutputPath();
        this.outputDir = dir.isEmpty()? Path.of("") : Path.of(dir);
        this.mode= arguments.isAppendToFile();
        this.prefix= arguments.getPrefix().isEmpty()? "" : arguments.getPrefix();
    }


    public void WriteAll(){

    }

    public void writeInt(List<String> integers){
        try (PrintWriter writer = new PrintWriter("sample-integers")){
            for(String line : integers){
                writer.println(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeFloat(List<String> floats){
        try(PrintWriter writer = new PrintWriter("sample-floats")){
            for(String line : floats ){
                writer.println(line);
            }
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public void writeString(List <String> strings){
        try(PrintWriter writer = new PrintWriter("sample-strings.txt")){
            for(String line: strings){
                writer.println(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {

    }
}
