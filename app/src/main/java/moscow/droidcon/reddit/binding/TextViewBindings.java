package moscow.droidcon.reddit.binding;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.TextView;

/**
 * @author Daniel Serdyukov
 */
public class TextViewBindings {

    @BindingAdapter("errorText")
    public static void bindErrorText(TextView view, String errorText) {
        if (errorText != null && !errorText.isEmpty()) {
            view.setText(errorText);
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

}
