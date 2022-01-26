package wahl.domain;

import java.util.Objects;

public record Kandidat(int id, String firstname, String lastname) {

    @Override
    public String toString() {
        return "Kandidat{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kandidat kandidat = (Kandidat) o;
        return id == kandidat.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname);
    }
}
