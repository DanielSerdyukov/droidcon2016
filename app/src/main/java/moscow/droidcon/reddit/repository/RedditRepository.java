package moscow.droidcon.reddit.repository;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import moscow.droidcon.reddit.api.RedditService;
import moscow.droidcon.reddit.model.Reddit;
import moscow.droidcon.reddit.model.Subreddit;
import rx.Observable;

/**
 * @author Daniel Serdyukov
 */
public class RedditRepository {

    private final RedditService mService;

    RedditRepository(@NonNull RedditService service) {
        mService = service;
    }

    @NonNull
    public Observable<List<Reddit>> getReddits(String category) {
        return Observable.defer(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                return mService.getReddits(category);
            } catch (InterruptedException e) {
                return Observable.error(e);
            }
        });
    }

    @NonNull
    public Observable<List<Reddit>> getRedditsForSubreddit(String subreddit) {
        return mService.getRedditsFromSubreddit(subreddit);
    }

    @NonNull
    public Observable<List<Subreddit>> searchSubreddits(String query) {
        return mService.searchSubreddits(query);
    }

}
