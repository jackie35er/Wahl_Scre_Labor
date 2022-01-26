package wahl.reader;

import java.io.IOException;
import java.util.Collection;

public interface DataReader<T> extends AutoCloseable{

    T read() throws IOException;
}
