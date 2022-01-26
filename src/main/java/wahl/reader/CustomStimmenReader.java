package wahl.reader;

import org.jetbrains.annotations.NotNull;
import wahl.domain.Kandidat;
import wahl.domain.Stimme;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * This reader throws away invalid stimmen
 *
 * File format:
 * (Erststimmekandidat_id)-(Zweitstimmekandidat_id)\n
 * (Erststimmekandidat_id)-(Zweitstimmekandidat_id)\n
 * (Erststimmekandidat_id)-(Zweitstimmekandidat_id)\n
 * .
 * .
 * .
 */
public class CustomStimmenReader implements StimmenReader, AutoCloseable{

    private final InputStream source;
    private final List<Kandidat> kandidaten;

    public CustomStimmenReader(@NotNull File file, Collection<Kandidat> kandidaten) throws FileNotFoundException {
        source = new FileInputStream(file);
        this.kandidaten = new ArrayList<>(kandidaten);
    }

    public CustomStimmenReader(@NotNull Path path,Collection<Kandidat> kandidaten) throws FileNotFoundException {
        this(path.toFile(),kandidaten);
    }

    public CustomStimmenReader(@NotNull InputStream source,Collection<Kandidat> kandidaten){
        this.source = source;
        this.kandidaten = new ArrayList<>(kandidaten);
    }

    public CustomStimmenReader(@NotNull File file, KandidatenReader kandidaten) throws IOException {
        source = new FileInputStream(file);
        this.kandidaten = new ArrayList<>(kandidaten.read());
    }

    public CustomStimmenReader(@NotNull Path path,KandidatenReader kandidaten) throws IOException {
        this(path.toFile(),kandidaten);
    }

    public CustomStimmenReader(@NotNull InputStream source,KandidatenReader kandidaten) throws IOException {
        this.source = source;
        this.kandidaten = new ArrayList<>(kandidaten.read());
    }

    @Override
    public Collection<Stimme> read() throws IOException {
        var stringBuilder = new StringBuilder();
        List<Stimme> out = new ArrayList<>();
        while(source.available() > 0){
            char currentchar = (char) source.read();
            if(currentchar == '\n'){
                handleAdd(out,stringBuilder.toString().trim());
                stringBuilder = new StringBuilder();
            }
            else {
                stringBuilder.append(currentchar);
            }
        }
        return out;
    }

    private void handleAdd(List<Stimme> out,String line){
        try {
            var stimme = lineToStimme(line);
            if(stimme.erststimme().id() == stimme.zweitstimme().id())
                return;
            out.add(stimme);
        }
        catch (Exception ignored){
            //Stimme invalid add nothing
        }
    }

    private Stimme lineToStimme(String line){
        var splits = line.split("-");
        Kandidat kandidat1 = findKandidat(Integer.parseInt(splits[0]));
        Kandidat kandidat2 = findKandidat(Integer.parseInt(splits[1]));

        return new Stimme(kandidat1,kandidat2);
    }

    private Kandidat findKandidat(int id){
        return kandidaten.stream()
                .filter(k -> k.id() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Kandidat wurde nicht in der übergebenenListe gefunden"));
    }

    @Override
    public void close() throws Exception {
        this.source.close();
    }

    public static void main(String[] args) throws IOException {
        KandidatenReader kandidatenReader = new CSVKandidatenReader(new File("src/main/resources/kandidaten.csv"));
        CustomStimmenReader stimmenReader = new CustomStimmenReader(new File("src/main/resources/stimmen.txt"),kandidatenReader);
        System.out.println(stimmenReader.read());
    }
}
