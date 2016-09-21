package moscow.droidcon.reddit.viewmodel;

import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsCollectionContaining;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import moscow.droidcon.reddit.model.Reddit;
import moscow.droidcon.reddit.repository.RedditRepository;
import moscow.droidcon.reddit.rx.ImmediateSchedulerTestRule;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * @author Daniel Serdyukov
 */
@RunWith(MockitoJUnitRunner.class)
public class RedditListViewModelTest {

    @Rule
    public TestRule mRule = new ImmediateSchedulerTestRule();

    private RedditListViewModel mViewModel;

    @Mock
    private RedditRepository mRepository;

    @Before
    public void setUp() throws Exception {
        mViewModel = new RedditListViewModel();
        mViewModel.mRepository = mRepository;
        mViewModel.mCategory = "test";
        mViewModel.mMainThreadScheduler = Schedulers.immediate();
    }

    @Test
    public void preconditions() throws Exception {
        Assert.assertThat(mViewModel.isLoading.get(), Is.is(false));
        Assert.assertThat(mViewModel.reddits, IsEmptyCollection.empty());
    }

    @Test
    public void refresh() throws Exception {
        final Reddit reddit = new Reddit();
        PublishSubject<Reddit> subject = PublishSubject.create();
        Mockito.doReturn(subject.asObservable().toList())
                .when(mRepository)
                .getReddits(Mockito.anyString());
        mViewModel.refresh();
        Mockito.verify(mRepository).getReddits("test");
        Assert.assertThat(mViewModel.errorText.get(), IsNull.nullValue());
        Assert.assertThat(mViewModel.isLoading.get(), Is.is(true));
        subject.onNext(reddit);
        subject.onCompleted();
        Assert.assertThat(mViewModel.isLoading.get(), Is.is(false));
        Assert.assertThat(mViewModel.reddits, IsCollectionContaining.hasItems(reddit));
    }

    @Test
    public void refreshError() throws Exception {
        PublishSubject<Reddit> subject = PublishSubject.create();
        Mockito.doReturn(subject.asObservable().toList())
                .when(mRepository)
                .getReddits(Mockito.anyString());
        mViewModel.refresh();
        Mockito.verify(mRepository).getReddits("test");
        Assert.assertThat(mViewModel.errorText.get(), IsNull.nullValue());
        Assert.assertThat(mViewModel.isLoading.get(), Is.is(true));
        subject.onError(new Exception("error text"));
        Assert.assertThat(mViewModel.isLoading.get(), Is.is(false));
        Assert.assertThat(mViewModel.errorText.get(), IsEqual.equalTo("error text"));
    }

}