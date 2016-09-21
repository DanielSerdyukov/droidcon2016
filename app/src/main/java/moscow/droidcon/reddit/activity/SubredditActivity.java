package moscow.droidcon.reddit.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import moscow.droidcon.reddit.R;
import moscow.droidcon.reddit.adapter.RedditListAdapter;
import moscow.droidcon.reddit.databinding.AcSubredditBinding;
import moscow.droidcon.reddit.viewmodel.SubredditViewModel;

/**
 * @author Daniel Serdyukov
 */
public class SubredditActivity extends AppCompatActivity {

    private static final String KEY_SUBREDDIT = "subreddit";

    private static final String KEY_TITLE = "title";

    private AcSubredditBinding mBinding;

    public static void launch(Context context, String subreddit, String title) {
        final Intent intent = new Intent(context, SubredditActivity.class);
        intent.putExtra(KEY_SUBREDDIT, subreddit);
        intent.putExtra(KEY_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.ac_subreddit);
        setTitle(getIntent().getStringExtra(KEY_TITLE));
        setSupportActionBar(mBinding.toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mBinding.recycler.setHasFixedSize(true);
        if (Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation) {
            mBinding.recycler.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            mBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
        }
        mBinding.recycler.setAdapter(new RedditListAdapter());
        mBinding.setViewModel(SubredditViewModel.from(getFragmentManager(),
                getIntent().getStringExtra(KEY_SUBREDDIT)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
