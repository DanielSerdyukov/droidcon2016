package moscow.droidcon.reddit.api;

import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import moscow.droidcon.reddit.model.Reddit;
import moscow.droidcon.reddit.model.Subreddit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Daniel Serdyukov
 */
public class ServiceProvider {

    private static Retrofit sRetrofit;

    public static void init() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(new HttpLoggingInterceptor()
                                    .setLevel(HttpLoggingInterceptor.Level.BASIC))
                            .build())
                    .baseUrl("https://www.reddit.com/")
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .registerTypeAdapter(new TypeToken<List<Reddit>>() {
                            }.getType(), new GsonRedditDeserializer())
                            .registerTypeAdapter(new TypeToken<List<Subreddit>>() {
                            }.getType(), new GsonSubredditDeserializer())
                            .create()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
    }

    @NonNull
    public static RedditService provideRedditService() {
        return sRetrofit.create(RedditService.class);
    }

}
