package com.codepath.apps.restclienttemplate;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TimeLineActivity extends AppCompatActivity {

    TwitterClient client;
    RecyclerView rvTweets;
    TweetAdapter adapter;
    List<Tweet> tweets;

    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        client = TwitterApp.getRestClient(this);

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateHomeTimeLine();
            }
        });

        rvTweets = findViewById(R.id.rvTweets);
        tweets = new ArrayList<>();
        adapter = new TweetAdapter(this, tweets);

        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvTweets.setAdapter(adapter);


        populateHomeTimeLine();
    }

    private void populateHomeTimeLine() {
        client.getHomeTimeLine(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                List<Tweet> tweetsToAdd = new ArrayList<>();
                for(int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Tweet tweet = Tweet.fromJson(jsonObject);
                        tweetsToAdd.add(tweet);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter.clear();
                adapter.addAll(tweetsToAdd);
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("TwitterClient", responseString);
            }


        });
    }
}
