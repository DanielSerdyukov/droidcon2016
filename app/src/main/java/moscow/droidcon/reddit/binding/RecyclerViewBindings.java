package moscow.droidcon.reddit.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import moscow.droidcon.reddit.adapter.RedditListAdapter;
import moscow.droidcon.reddit.model.Reddit;

/**
 * @author Daniel Serdyukov
 */
public class RecyclerViewBindings {

    @BindingAdapter("reddits")
    public static void bindReddits(RecyclerView recycler, List<Reddit> reddits) {
        final RecyclerView.Adapter adapter = recycler.getAdapter();
        if (adapter instanceof RedditListAdapter) {
            ((RedditListAdapter) adapter).changeDataSet(reddits);
        } else {
            recycler.setAdapter(new RedditListAdapter(reddits));
        }
    }

    @BindingAdapter("onRedditClick")
    public static void bindOnRedditClick(RecyclerView recycler, OnRedditClick listener) {
        recycler.addOnItemTouchListener(new RedditTouchAdapter(recycler.getContext(), listener));
    }

    private static class RedditTouchAdapter extends RecyclerView.SimpleOnItemTouchListener {

        private final OnRedditClick mListener;

        private final GestureDetector mDetector;

        RedditTouchAdapter(Context context, OnRedditClick listener) {
            mListener = listener;
            mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            final View childView = rv.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mDetector.onTouchEvent(e)) {
                final int position = rv.getChildAdapterPosition(childView);
                final RecyclerView.Adapter adapter = rv.getAdapter();
                if (adapter instanceof RedditListAdapter) {
                    mListener.onRedditClick(rv.getContext(), ((RedditListAdapter) adapter).getReddit(position));
                }
            }
            return super.onInterceptTouchEvent(rv, e);
        }

    }

}
