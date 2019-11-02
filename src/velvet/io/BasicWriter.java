package velvet.io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.stream.Stream;

public abstract class BasicWriter<T> implements Writer<T> {

    protected final DataOutputStream out;

    public BasicWriter(DataOutputStream out) {
        this.out = out;
    }

    public static <U> void safeStreamWrite(Stream<U> stream, Writer<U> writer) throws IOException {
        boolean success = stream.allMatch(t -> {
            try {
                writer.write(t);
                return true;
            } catch (IOException ignored) {}
            return false;
        });
        if(!success){
            throw new IOException();
        }
    }
}
