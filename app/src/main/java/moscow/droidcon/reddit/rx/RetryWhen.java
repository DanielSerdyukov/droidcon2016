package moscow.droidcon.reddit.rx;

import java.io.InterruptedIOException;

import rx.functions.Func2;

/**
 * @author Daniel Serdyukov
 */
public class RetryWhen {

    public static Func2<Integer, Throwable, Boolean> interrupted() {
        return (integer, throwable) -> throwable instanceof InterruptedIOException;
    }

}
