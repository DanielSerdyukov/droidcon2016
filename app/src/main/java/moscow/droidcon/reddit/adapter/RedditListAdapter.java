package moscow.droidcon.reddit.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import moscow.droidcon.reddit.R;
import moscow.droidcon.reddit.databinding.LiRedditBinding;
import moscow.droidcon.reddit.model.Reddit;

/**
 * @author Daniel Serdyukov
 */
public class RedditListAdapter extends RecyclerView.Adapter<RedditListAdapter.RedditViewHolder> {

    private List<Reddit> mReddits = Collections.emptyList();

    public RedditListAdapter() {
        this(Collections.emptyList());
    }

    public RedditListAdapter(List<Reddit> reddits) {
        mReddits = reddits;
    }

    public void changeDataSet(List<Reddit> reddits) {
        mReddits = new ArrayList<>(reddits);
        notifyDataSetChanged();
    }

    @Override
    public RedditViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new RedditViewHolder(DataBindingUtil.inflate(inflater, R.layout.li_reddit, parent, false));
    }

    @Override
    public void onBindViewHolder(RedditViewHolder holder, int position) {
        holder.mBinding.setReddit(mReddits.get(position));
    }

    @Override
    public int getItemCount() {
        return mReddits.size();
    }

    public Reddit getReddit(int position) {
        return mReddits.get(position);
    }

    static class RedditViewHolder extends RecyclerView.ViewHolder {

        private final LiRedditBinding mBinding;

        RedditViewHolder(LiRedditBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

    }

}
