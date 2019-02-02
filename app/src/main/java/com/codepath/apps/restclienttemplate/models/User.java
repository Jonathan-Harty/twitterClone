package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    public String name;
    public long id;
    public String screenName;
    public String profilePicUrl;

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();

        user.name = jsonObject.getString("name");
        user.id = jsonObject.getLong("id");
        user.screenName = jsonObject.getString("screen_name");
        user.profilePicUrl = jsonObject.getString("profile_image_url");

        return user;
    }

}
