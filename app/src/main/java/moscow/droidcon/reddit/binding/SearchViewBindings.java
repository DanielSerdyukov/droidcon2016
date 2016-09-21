package moscow.droidcon.reddit.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.lapism.searchview.SearchView;

import java.util.List;

import moscow.droidcon.reddit.adapter.SubredditSearchAdapter;
import moscow.droidcon.reddit.model.Subreddit;

/**
 * @author Daniel Serdyukov
 */
public class SearchViewBindings {

    @BindingAdapter("onSearchQueryChange")
    public static void bindOnSearchQueryChange(SearchView searchView, OnSearchQueryChange listener) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                listener.onSearchQueryChange(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });
    }

    @BindingAdapter("subreddits")
    public static void bindSubreddits(SearchView searchView, List<Subreddit> subreddits) {
        final RecyclerView.Adapter adapter = searchView.getAdapter();
        if (adapter instanceof SubredditSearchAdapter) {
            ((SubredditSearchAdapter) adapter).changeDataSet(subreddits);
        } else {
            final SubredditSearchAdapter searchAdapter = new SubredditSearchAdapter(searchView.getContext());
            searchAdapter.changeDataSet(subreddits);
            searchView.setAdapter(searchAdapter);
        }
    }

    @BindingAdapter("onSubredditClick")
    public static void bindOnSubredditClick(SearchView searchView, OnSubredditClick listener) {
        final RecyclerView.Adapter adapter = searchView.getAdapter();
        if (adapter instanceof SubredditSearchAdapter) {
            final SubredditSearchAdapter searchAdapter = (SubredditSearchAdapter) adapter;
            ((SubredditSearchAdapter) adapter).addOnItemClickListener((view, position) ->
                    listener.onSubredditClick(view.getContext(), searchAdapter.getSubreddit(position)));
        } else {
            final SubredditSearchAdapter searchAdapter = new SubredditSearchAdapter(searchView.getContext());
            searchAdapter.addOnItemClickListener((view, position) ->
                    listener.onSubredditClick(view.getContext(), searchAdapter.getSubreddit(position)));
            searchView.setAdapter(searchAdapter);
        }
    }

}
