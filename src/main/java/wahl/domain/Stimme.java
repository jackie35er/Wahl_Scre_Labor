package wahl.domain;

import java.util.Objects;

public record Stimme(Kandidat erststimme, Kandidat zweitstimme) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stimme stimme = (Stimme) o;
        return Objects.equals(erststimme, stimme.erststimme) && Objects.equals(zweitstimme, stimme.zweitstimme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(erststimme, zweitstimme);
    }

    @Override
    public String toString() {
        return "Stimme{" +
                "erststimme=" + erststimme +
                ", zweitstimme=" + zweitstimme +
                '}';
    }
}
