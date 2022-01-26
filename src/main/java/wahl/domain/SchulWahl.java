package wahl.domain;


import wahl.reader.CSVKandidatenReader;
import wahl.reader.CustomStimmenReader;
import wahl.reader.KandidatenReader;
import wahl.reader.ReaderBasedWahlReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public record SchulWahl(Collection<Kandidat> kandidaten, Collection<Stimme> stimmen) implements Wahl {



    @Override
    public Ergebnis execute(){
        var punkte = setUpPunkteMap();
        var erstStimmen = setUpPunkteMap();
        for(var stimme : stimmen){
            punkte.compute(stimme.erststimme(),(k,v) -> handleAdd(v,2));
            punkte.compute(stimme.zweitstimme(),(k,v) -> handleAdd(v,1));
            erstStimmen.compute(stimme.erststimme(),(k,v) -> handleAdd(v,1));
        }
        return makeErgebnis(punkte,erstStimmen);
    }

    private int handleAdd(Integer integer, int toAdd){
        if(integer == null)
            return toAdd;
        return integer + toAdd;
    }

    private Map<Kandidat, Integer> setUpPunkteMap(){
        return kandidaten.stream()
                .collect(Collectors.toMap(
                        k -> k,
                        k -> 0,
                        (oldValue,newValue) -> newValue
                ));
    }

    private Ergebnis makeErgebnis(Map<Kandidat, Integer> punkteMap,Map<Kandidat, Integer> erstStimmenMap){
        Map<Kandidat, Ergebnis.Punkte> out = new HashMap<>();
        for(var kandidat : kandidaten){
            int punkte = punkteMap.get(kandidat);
            int erststimmen = erstStimmenMap.get(kandidat);
            out.put(kandidat,new Ergebnis.Punkte(punkte,erststimmen));
        }
        return new BasicErgebnis(out);
    }

    public static void main(String[] args) throws IOException {
        KandidatenReader kandidatenReader = new CSVKandidatenReader(new File("src/main/resources/kandidaten.csv"));
        CustomStimmenReader stimmenReader = new CustomStimmenReader(new File("src/main/resources/stimmen.txt"),kandidatenReader);
        KandidatenReader kandidatenReader2 = new CSVKandidatenReader(new File("src/main/resources/kandidaten.csv"));
        var wahl = new ReaderBasedWahlReader(kandidatenReader2,stimmenReader).read();
        System.out.println(wahl.execute().getKandidatenInOrder());


    }

}
