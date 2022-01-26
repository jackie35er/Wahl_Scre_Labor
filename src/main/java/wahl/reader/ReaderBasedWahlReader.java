package wahl.reader;

import wahl.domain.SchulWahl;

import java.io.IOException;

public class ReaderBasedWahlReader implements WahlReader,AutoCloseable {

    private final KandidatenReader kandidatenReader;
    private final StimmenReader stimmenReader;

    public ReaderBasedWahlReader(KandidatenReader kandidatenReader, StimmenReader stimmenReader) {
        this.kandidatenReader = kandidatenReader;
        this.stimmenReader = stimmenReader;
    }


    @Override
    public SchulWahl read() throws IOException {
        return new SchulWahl(kandidatenReader.read(),stimmenReader.read());
    }


    @Override
    public void close() throws Exception {
        kandidatenReader.close();
        stimmenReader.close();
    }
}
