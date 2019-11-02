package velvet.io;

import java.io.IOException;

public interface Loader<T> {

    void load(T t) throws IOException;
}
