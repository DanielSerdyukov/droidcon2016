package moscow.droidcon.reddit.fragment;

import android.app.Fragment;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moscow.droidcon.reddit.R;
import moscow.droidcon.reddit.adapter.RedditListAdapter;
import moscow.droidcon.reddit.databinding.FmtRedditListBinding;
import moscow.droidcon.reddit.viewmodel.RedditListViewModel;

/**
 * @author Daniel Serdyukov
 */
public class RedditListFragment extends Fragment {

    public static final String KEY_TAB = "tab";

    private FmtRedditListBinding mBinding;

    public static RedditListFragment newInstance(String tab) {
        final RedditListFragment fragment = new RedditListFragment();
        final Bundle args = new Bundle();
        args.putString(KEY_TAB, tab);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fmt_reddit_list, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.recycler.setHasFixedSize(true);
        if (Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation) {
            mBinding.recycler.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        } else {
            mBinding.recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }
        mBinding.recycler.setAdapter(new RedditListAdapter());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.setViewModel(RedditListViewModel.from(getFragmentManager(),
                getArguments().getString(KEY_TAB)));
    }

}
