package velvet.io;

import java.io.IOException;
import java.io.Writer;
import java.util.stream.Stream;

public abstract class BasicPrinter<T> implements Printer<T>{

    protected final Writer out;

    public BasicPrinter(Writer out) {
        this.out = out;
    }

    public static <U> void safeStreamPrint(Stream<U> stream, Printer<U> printer) throws IOException {
        boolean success = stream.allMatch(t -> {
            try {
                printer.print(t);
                return true;
            } catch (IOException ignored) {}
            return false;
        });
        if(!success){
            throw new IOException();
        }
    }
}
