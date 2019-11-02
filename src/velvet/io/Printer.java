package velvet.io;

import java.io.IOException;

public interface Printer<T> {

    void print(T t) throws IOException;
}
