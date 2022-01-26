package wahl.reader;

import org.jetbrains.annotations.NotNull;
import wahl.domain.Kandidat;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class CSVKandidatenReader implements KandidatenReader, AutoCloseable{
    private final InputStream source;

    private String delimiter = ",";

    public CSVKandidatenReader(@NotNull File file) throws FileNotFoundException {
        source = new FileInputStream(file);

    }

    public CSVKandidatenReader(@NotNull Path path) throws FileNotFoundException {
        this(path.toFile());
    }

    public CSVKandidatenReader(@NotNull InputStream source){
        this.source = source;
    }

    @Override
    public Collection<Kandidat> read() throws IOException {
        var stringBuilder = new StringBuilder();
        List<Kandidat> out = new ArrayList<>();

        while(source.available() > 0){
            char currentChar = (char) source.read();
            if(currentChar == '\n'){
                tryToBuild(stringBuilder,out);
                stringBuilder = new StringBuilder();
            }
            else{
                stringBuilder.append(currentChar);
            }
        }
        tryToBuild(stringBuilder, out);

        return out;
    }

    private void tryToBuild(StringBuilder stringBuilder, List<Kandidat> out) {
        var canBeBuild = csvLineToKandidat(stringBuilder.toString());
        canBeBuild.ifPresent(out::add);
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    private Optional<Kandidat> csvLineToKandidat(String line){
        try{
            var splits = line.trim().split(delimiter);
            return Optional.of( new Kandidat(Integer.parseInt(splits[0]),splits[1],splits[2]));
        }
        catch (Exception e){
            return Optional.empty();
        }

    }

    @Override
    public void close() throws Exception {
        this.source.close();
    }


    public static void main(String[] args) throws IOException {
        KandidatenReader kandidatenReader = new CSVKandidatenReader(new File("src/main/resources/kandidaten.csv"));
        System.out.println(kandidatenReader.read());
    }
}
