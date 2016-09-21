package moscow.droidcon.reddit.binding;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * @author Daniel Serdyukov
 */
public class ViewBindings {

    @BindingConversion
    public static int convertBooleanToVisibility(boolean visible) {
        return visible ? View.VISIBLE : View.GONE;
    }

    @BindingAdapter("errorText")
    public static void bindErrorText(View view, String text) {
        if (text != null && !text.isEmpty()) {
            Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
        }
    }

}
