package moscow.droidcon.reddit.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import moscow.droidcon.reddit.fragment.RedditListFragment;

/**
 * @author Daniel Serdyukov
 */
public class RedditListPagerAdapter extends FragmentStatePagerAdapter {

    private static final String[] TABS = {
            "NEW", "TOP", "HOT", "404"
    };

    public RedditListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return RedditListFragment.newInstance(TABS[position].toLowerCase());
    }

    @Override
    public int getCount() {
        return TABS.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TABS[position];
    }

}
