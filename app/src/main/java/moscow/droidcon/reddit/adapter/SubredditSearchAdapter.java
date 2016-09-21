package moscow.droidcon.reddit.adapter;

import android.content.Context;

import com.lapism.searchview.SearchAdapter;
import com.lapism.searchview.SearchItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import moscow.droidcon.reddit.model.Subreddit;

/**
 * @author Daniel Serdyukov
 */
public class SubredditSearchAdapter extends SearchAdapter {

    private List<Subreddit> mSubreddits = Collections.emptyList();

    public SubredditSearchAdapter(Context context) {
        super(context);
    }

    @SuppressWarnings("Convert2streamapi")
    public final void changeDataSet(List<Subreddit> subreddits) {
        mSubreddits = new ArrayList<>(subreddits);
        final List<SearchItem> searchItems = new ArrayList<>(subreddits.size());
        for (final Subreddit subreddit : subreddits) {
            searchItems.add(new SearchItem(subreddit.getTitle()));
        }
        setData(searchItems);
    }

    public Subreddit getSubreddit(int position) {
        return mSubreddits.get(position);
    }

}
