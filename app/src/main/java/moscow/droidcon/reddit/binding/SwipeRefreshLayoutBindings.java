package moscow.droidcon.reddit.binding;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * @author Daniel Serdyukov
 */
public class SwipeRefreshLayoutBindings {

    @BindingAdapter("onRefresh")
    public static void bindOnRefresh(SwipeRefreshLayout srl, OnRefresh listener) {
        srl.setOnRefreshListener(listener::onRefresh);
    }

}
