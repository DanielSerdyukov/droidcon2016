package moscow.droidcon.reddit.viewmodel;

import android.app.Fragment;
import android.app.FragmentManager;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;

import java.util.concurrent.TimeUnit;

import moscow.droidcon.reddit.activity.SubredditActivity;
import moscow.droidcon.reddit.binding.OnSearchQueryChange;
import moscow.droidcon.reddit.binding.OnSubredditClick;
import moscow.droidcon.reddit.model.Subreddit;
import moscow.droidcon.reddit.repository.RedditRepository;
import moscow.droidcon.reddit.repository.Repository;
import moscow.droidcon.reddit.rx.RetryWhen;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

/**
 * @author Daniel Serdyukov
 */
public class MainViewModel extends Fragment {

    private static final String TAG = MainViewModel.class.getCanonicalName();

    public final ObservableBoolean isLoading = new ObservableBoolean();

    public final ObservableList<Subreddit> subreddits = new ObservableArrayList<>();

    public final OnSubredditClick onSubredditClick = (context, subreddit)
            -> SubredditActivity.launch(context, subreddit.getName(), subreddit.getTitle());

    @VisibleForTesting
    final Subject<String, String> mSearchQuery = BehaviorSubject.create();

    public final OnSearchQueryChange onSearchQueryChange = mSearchQuery::onNext;

    @VisibleForTesting
    RedditRepository mRepository;

    @VisibleForTesting
    Scheduler mMainThreadScheduler;

    private Subscription mSearchSubscription;

    public static MainViewModel from(FragmentManager fm) {
        Fragment viewModel = fm.findFragmentByTag(TAG);
        if (viewModel == null) {
            viewModel = new MainViewModel();
            viewModel.setRetainInstance(true);
            fm.beginTransaction().add(viewModel, TAG).commitAllowingStateLoss();
        }
        return (MainViewModel) viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = Repository.reddits();
        mMainThreadScheduler = AndroidSchedulers.mainThread();
        subscribeOnSearchQueryChange();
    }

    @Override
    public void onDestroy() {
        mSearchSubscription.unsubscribe();
        super.onDestroy();
    }

    @VisibleForTesting
    void subscribeOnSearchQueryChange() {
        mSearchSubscription = mSearchQuery.asObservable()
                .debounce(1, TimeUnit.SECONDS)
                .filter(query -> !query.isEmpty())
                .flatMap(query -> mRepository.searchSubreddits(query)
                        .doOnSubscribe(() -> isLoading.set(true))
                        .doOnTerminate(() -> isLoading.set(false)))
                .retry(RetryWhen.interrupted())
                .subscribeOn(Schedulers.io())
                .observeOn(mMainThreadScheduler)
                .subscribe(list -> {
                    subreddits.clear();
                    subreddits.addAll(list);
                });
    }

}
