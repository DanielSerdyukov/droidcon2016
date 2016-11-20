package moscow.droidcon.reddit.repository;

import android.support.annotation.NonNull;

import moscow.droidcon.reddit.api.ServiceProvider;

/**
 * @author Daniel Serdyukov
 */
public class Repository {

    public static void init() {
        ServiceProvider.init();
    }

    @NonNull
    public static RedditRepository reddits() {
        return new RedditRepository(ServiceProvider.provideRedditService());
    }

}
