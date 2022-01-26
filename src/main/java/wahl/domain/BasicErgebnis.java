package wahl.domain;

import java.util.*;
import java.util.stream.Collectors;

public class BasicErgebnis implements Ergebnis {
    private Map<Kandidat, Punkte> ergebnisse;

    public BasicErgebnis(Map<Kandidat,Punkte> ergebnisse) {
        this.ergebnisse = new HashMap<>(ergebnisse);
    }

    @Override
    public Kandidat getWinner() {
        return getKandidatenInOrder().get(0);
    }

    @Override
    public Kandidat getByPosition(int index){
        return getKandidatenInOrder().get(index);
    }

    @Override
    public List<Kandidat> getKandidatenInOrder(){
        var list =  ergebnisse.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList())
                ;
        Collections.reverse(list);
        return new ArrayList<>(list);
    }

    @Override
    public Punkte getPunkteByKandidat(Kandidat kandidat){
        return ergebnisse.get(kandidat);
    }


}
