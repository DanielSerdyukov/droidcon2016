package moscow.droidcon.reddit.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import moscow.droidcon.reddit.model.Reddit;

/**
 * @author Daniel Serdyukov
 */
class GsonRedditDeserializer implements JsonDeserializer<List<Reddit>> {

    @Override
    public List<Reddit> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonArray childrens = ((JsonObject) json).getAsJsonObject("data").getAsJsonArray("children");
        final List<Reddit> reddits = new ArrayList<>(childrens.size());
        for (final JsonElement child : childrens) {
            final JsonObject data = ((JsonObject) child).getAsJsonObject("data");
            reddits.add(new Reddit.Builder()
                    .setTitle(data.getAsJsonPrimitive("title").getAsString())
                    .setAuthor(data.getAsJsonPrimitive("author").getAsString())
                    .setSubreddit(data.getAsJsonPrimitive("subreddit").getAsString())
                    .setThumbnail(data.getAsJsonPrimitive("thumbnail").getAsString())
                    .setUrl(data.getAsJsonPrimitive("url").getAsString())
                    .build());
        }
        return reddits;
    }

}