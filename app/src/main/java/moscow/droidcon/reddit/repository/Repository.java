package moscow.droidcon.reddit.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import moscow.droidcon.reddit.api.ServiceProvider;

/**
 * @author Daniel Serdyukov
 */
public class Repository {

    public static void init(@NonNull Context context) {
        ServiceProvider.init(context);
    }

    @NonNull
    public static RedditRepository reddits() {
        return new RedditRepository(ServiceProvider.provideRedditService());
    }

}
