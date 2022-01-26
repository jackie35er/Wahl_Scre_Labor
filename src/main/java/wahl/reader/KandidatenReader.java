package wahl.reader;

import wahl.domain.Kandidat;

import java.io.IOException;
import java.util.Collection;

public interface KandidatenReader extends DataReader<Collection<Kandidat>>{

    Collection<Kandidat> read() throws IOException;
}
