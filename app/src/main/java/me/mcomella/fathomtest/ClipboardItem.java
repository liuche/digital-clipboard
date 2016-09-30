package me.mcomella.fathomtest;

import org.json.JSONObject;

public class ClipboardItem {
    public static final String LOGTAG = "ClipboardItem";

    public final String title;
    public final String description;
    public final String rating;
    public final String imageUrl;
    public final String site;

    public ClipboardItem(JSONObject jsonObject) {
        // XXX Use optString right now so we don't have to try/catch/reassign.
        title = jsonObject.optString("title");
        description = jsonObject.optString("description");
        rating = jsonObject.optString("rating");
        imageUrl = jsonObject.optString("poster");
        site = jsonObject.optString("site");
    }
}
