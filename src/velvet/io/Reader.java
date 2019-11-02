package velvet.io;

import java.io.IOException;

public interface Reader<T> {

    T read() throws IOException;
}
