package moscow.droidcon.reddit.viewmodel;

import android.app.Fragment;
import android.app.FragmentManager;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;

import moscow.droidcon.reddit.binding.OnRedditClick;
import moscow.droidcon.reddit.binding.OnRefresh;
import moscow.droidcon.reddit.model.Reddit;
import moscow.droidcon.reddit.repository.RedditRepository;
import moscow.droidcon.reddit.repository.Repository;
import moscow.droidcon.reddit.utils.Intents;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Daniel Serdyukov
 */
public class SubredditViewModel extends Fragment {

    private static final String TAG = SubredditViewModel.class.getCanonicalName();

    public final ObservableBoolean isLoading = new ObservableBoolean();

    public final ObservableList<Reddit> reddits = new ObservableArrayList<>();

    public final ObservableField<String> errorText = new ObservableField<>();

    public final OnRedditClick onRedditClick = (context, reddit) ->
            context.startActivity(Intents.openUrl(context, reddit.getUrl()));

    String mSubreddit;

    @VisibleForTesting
    RedditRepository mRepository;

    @VisibleForTesting
    Scheduler mMainThreadScheduler;

    private Subscription mSubscription;

    public final OnRefresh onRefresh = this::refresh;

    public static SubredditViewModel from(FragmentManager fm, String subreddit) {
        SubredditViewModel viewModel = (SubredditViewModel) fm.findFragmentByTag(TAG);
        if (viewModel == null) {
            viewModel = new SubredditViewModel();
            viewModel.setRetainInstance(true);
            viewModel.mSubreddit = subreddit;
            fm.beginTransaction().add(viewModel, TAG).commitAllowingStateLoss();
        }
        return viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = Repository.reddits();
        mMainThreadScheduler = AndroidSchedulers.mainThread();
        refresh();
    }

    @Override
    public void onDestroy() {
        mSubscription.unsubscribe();
        super.onDestroy();
    }

    @VisibleForTesting
    void refresh() {
        mSubscription = mRepository
                .getRedditsForSubreddit(mSubreddit)
                .subscribeOn(Schedulers.io())
                .observeOn(mMainThreadScheduler)
                .doOnSubscribe(() -> errorText.set(null))
                .doOnSubscribe(() -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe(list -> {
                    reddits.clear();
                    reddits.addAll(list);
                }, e -> {
                    errorText.set(e.getMessage());
                });
    }

}
