package moscow.droidcon.reddit.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import moscow.droidcon.reddit.R;

/**
 * @author Daniel Serdyukov
 */
public class ImageViewBindings {

    private static volatile Drawable sErrorDrawable;

    @BindingAdapter("imageUrl")
    public static void bindImageUrl(ImageView view, String url) {
        if (url != null && !url.isEmpty()) {
            Picasso.with(view.getContext())
                    .load(url)
                    .transform(new PicassoCircleTransform())
                    .error(getErrorDrawable(view.getContext()))
                    .into(view);
        } else {
            view.setImageResource(R.drawable.ic_reddit);
        }
    }

    private static Drawable getErrorDrawable(Context context) {
        Drawable drawable = sErrorDrawable;
        if (drawable == null) {
            synchronized (ImageViewBindings.class) {
                drawable = sErrorDrawable;
                if (drawable == null) {
                    drawable = sErrorDrawable = VectorDrawableCompat.create(context
                            .getResources(), R.drawable.ic_reddit, context.getTheme());
                }
            }
        }
        return drawable;
    }

}
