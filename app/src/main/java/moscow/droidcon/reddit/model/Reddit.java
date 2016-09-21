package moscow.droidcon.reddit.model;

/**
 * @author Daniel Serdyukov
 */
public class Reddit {

    private String mTitle;

    private String mAuthor;

    private String mSubreddit;

    private String mThumbnail;

    private String mUrl;

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getSubreddit() {
        return mSubreddit;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public String getUrl() {
        return mUrl;
    }

    public static class Builder {

        private final Reddit mReddit = new Reddit();

        public Builder setTitle(String title) {
            mReddit.mTitle = title;
            return this;
        }

        public Builder setAuthor(String author) {
            mReddit.mAuthor = author;
            return this;
        }

        public Builder setSubreddit(String subreddit) {
            mReddit.mSubreddit = subreddit;
            return this;
        }

        public Builder setThumbnail(String thumbnail) {
            mReddit.mThumbnail = thumbnail;
            return this;
        }

        public Builder setUrl(String url) {
            mReddit.mUrl = url;
            return this;
        }

        public Reddit build() {
            return mReddit;
        }

    }

}
