package moscow.droidcon.reddit.viewmodel;

import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import moscow.droidcon.reddit.model.Subreddit;
import moscow.droidcon.reddit.repository.RedditRepository;
import moscow.droidcon.reddit.rx.ImmediateSchedulerTestRule;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * @author Daniel Serdyukov
 */
@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

    @Rule
    public TestRule mRule = new ImmediateSchedulerTestRule();

    private MainViewModel mViewModel;

    @Mock
    private RedditRepository mRepository;

    @Before
    public void setUp() throws Exception {
        mViewModel = new MainViewModel();
        mViewModel.mMainThreadScheduler = Schedulers.immediate();
        mViewModel.mRepository = mRepository;
    }

    @Test
    public void preconditions() throws Exception {
        Assert.assertThat(mViewModel.isLoading.get(), Is.is(false));
        Assert.assertThat(mViewModel.subreddits, IsEmptyCollection.empty());
    }

    @Test
    public void searchQueryChange() throws Exception {
        final Subreddit subreddit = new Subreddit();
        PublishSubject<Subreddit> subject = PublishSubject.create();
        Mockito.doReturn(subject.asObservable().toList())
                .when(mRepository)
                .searchSubreddits(Mockito.anyString());
        mViewModel.subscribeOnSearchQueryChange();
        mViewModel.mSearchQuery.onNext("test");
        Mockito.verify(mRepository).searchSubreddits("test");
        Assert.assertThat(mViewModel.isLoading.get(), Is.is(true));
        subject.onNext(subreddit);
        subject.onCompleted();
        Assert.assertThat(mViewModel.isLoading.get(), Is.is(false));
        Assert.assertThat(mViewModel.subreddits, IsCollectionContaining.hasItems(subreddit));
    }

}