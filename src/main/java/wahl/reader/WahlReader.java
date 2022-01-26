package wahl.reader;

import wahl.domain.SchulWahl;

import java.io.IOException;

public interface WahlReader extends DataReader<SchulWahl>{

    SchulWahl read() throws IOException;
}
