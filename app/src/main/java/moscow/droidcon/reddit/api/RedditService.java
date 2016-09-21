package moscow.droidcon.reddit.api;

import java.util.List;

import moscow.droidcon.reddit.model.Reddit;
import moscow.droidcon.reddit.model.Subreddit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Daniel Serdyukov
 */
public interface RedditService {

    @GET("/{category}.json")
    Observable<List<Reddit>> getReddits(@Path("category") String category);

    @GET("/r/{subreddit}/.json")
    Observable<List<Reddit>> getRedditsFromSubreddit(@Path("subreddit") String subreddit);

    @GET("/subreddits/search.json")
    Observable<List<Subreddit>> searchSubreddits(@Query("q") String query);

}
