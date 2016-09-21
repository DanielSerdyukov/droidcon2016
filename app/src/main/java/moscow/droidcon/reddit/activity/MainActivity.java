package moscow.droidcon.reddit.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import moscow.droidcon.reddit.R;
import moscow.droidcon.reddit.adapter.RedditListPagerAdapter;
import moscow.droidcon.reddit.databinding.AcMainBinding;
import moscow.droidcon.reddit.viewmodel.MainViewModel;

/**
 * @author Daniel Serdyukov
 */
public class MainActivity extends AppCompatActivity {

    private AcMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.ac_main);
        setSupportActionBar(mBinding.toolbar);
        mBinding.pager.setAdapter(new RedditListPagerAdapter(getFragmentManager()));
        mBinding.pager.setOffscreenPageLimit(2);
        mBinding.tabs.setupWithViewPager(mBinding.pager);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mBinding.setViewModel(MainViewModel.from(getFragmentManager()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (mBinding.searchView.isSearchOpen()) {
            mBinding.searchView.close(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.searchAction == item.getItemId()) {
            mBinding.searchView.open(true, item);
        }
        return super.onOptionsItemSelected(item);
    }

}
