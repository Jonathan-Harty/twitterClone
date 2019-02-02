package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tweet {
    public String body;
    public long id;
    public String createdAt;
    public User user;

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        tweet.body = jsonObject.getString("text");
        tweet.id = jsonObject.getLong("id");

        //format date
        Date dateLong = new Date(jsonObject.getString("created_at"));
        tweet.createdAt = new SimpleDateFormat("yyyy-MM-dd").format(dateLong);

        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        return tweet;
    }

}
