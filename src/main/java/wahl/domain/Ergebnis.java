package wahl.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public interface Ergebnis {
    Kandidat getWinner();

    Kandidat getByPosition(int index);

    List<Kandidat> getKandidatenInOrder();

    Punkte getPunkteByKandidat(Kandidat kandidat);

    record Punkte(int punkte, int erstStimmen) implements Comparable<BasicErgebnis.Punkte> {

        @Override
        public int compareTo(@NotNull Ergebnis.Punkte o) {
            return Comparator.comparing(Ergebnis.Punkte::punkte).thenComparing(Ergebnis.Punkte::erstStimmen).compare(this, o);
        }
    }
}
