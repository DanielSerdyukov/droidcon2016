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

import moscow.droidcon.reddit.model.Subreddit;

/**
 * @author Daniel Serdyukov
 */
class GsonSubredditDeserializer implements JsonDeserializer<List<Subreddit>> {

    @Override
    public List<Subreddit> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonArray childrens = ((JsonObject) json).getAsJsonObject("data").getAsJsonArray("children");
        final List<Subreddit> reddits = new ArrayList<>(childrens.size());
        for (final JsonElement child : childrens) {
            final JsonObject data = ((JsonObject) child).getAsJsonObject("data");
            reddits.add(new Subreddit.Builder()
                    .setTitle(data.getAsJsonPrimitive("title").getAsString())
                    .setName(data.getAsJsonPrimitive("display_name").getAsString())
                    .build());
        }
        return reddits;
    }

}