package moscow.droidcon.reddit.binding;

import android.content.Context;

import moscow.droidcon.reddit.model.Subreddit;

/**
 * @author Daniel Serdyukov
 */
public interface OnSubredditClick {

    void onSubredditClick(Context context, Subreddit subreddit);

}
