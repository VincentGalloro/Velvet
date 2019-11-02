package velvet.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.stream.Stream;

public abstract class BasicLoader<T> {

    protected final DataInputStream in;

    public BasicLoader(DataInputStream in) {
        this.in = in;
    }

    public static <U> void safeStreamLoad(Stream<U> uStream, Loader<U> loader) throws IOException {
        boolean success = uStream.allMatch(t -> {
            try {
                loader.load(t);
                return true;
            } catch (IOException ignored) {}
            return false;
        });
        if(!success){
            throw new IOException();
        }
    }
}
