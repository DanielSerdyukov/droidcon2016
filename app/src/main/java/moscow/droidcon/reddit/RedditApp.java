package moscow.droidcon.reddit;

import android.app.Application;
import android.os.StrictMode;

import moscow.droidcon.reddit.repository.Repository;

/**
 * @author Daniel Serdyukov
 */
public class RedditApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
        }
        Repository.init(this);
    }

}
