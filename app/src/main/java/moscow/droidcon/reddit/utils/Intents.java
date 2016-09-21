package moscow.droidcon.reddit.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import moscow.droidcon.reddit.R;

/**
 * @author Daniel Serdyukov
 */
public class Intents {

    public static Intent openUrl(Context context, String url) {
        final Intent intent = new CustomTabsIntent.Builder()
                .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .addDefaultShareMenuItem()
                .build().intent;
        intent.setData(Uri.parse(url));
        return intent;
    }

}
