package moscow.droidcon.reddit.model;

/**
 * @author Daniel Serdyukov
 */
public class Subreddit {

    private String mTitle;

    private String mName;

    public String getTitle() {
        return mTitle;
    }

    public String getName() {
        return mName;
    }

    public static class Builder {

        private final Subreddit mSubreddit = new Subreddit();

        public Builder setTitle(String title) {
            mSubreddit.mTitle = title;
            return this;
        }

        public Builder setName(String url) {
            mSubreddit.mName = url;
            return this;
        }

        public Subreddit build() {
            return mSubreddit;
        }

    }

}
