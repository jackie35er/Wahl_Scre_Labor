package wahl.reader;

import wahl.domain.Stimme;

import java.io.IOException;
import java.util.Collection;

public interface StimmenReader extends DataReader<Collection<Stimme>>{

    Collection<Stimme> read() throws IOException;
}
