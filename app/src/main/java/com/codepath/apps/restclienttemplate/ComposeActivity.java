package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    public static final int MAX_TWEET_LENGTH = 140;

    TwitterClient client;

    EditText etCompose;
    Button btnTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApp.getRestClient(this);

        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTweetContent = etCompose.getText().toString();

                if(newTweetContent.isEmpty()) {
                    Toast.makeText(ComposeActivity.this, "Tweet Cannot Be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(newTweetContent.length() > MAX_TWEET_LENGTH) {
                    Toast.makeText(ComposeActivity.this, "Tweet Cannot Be More Than " + MAX_TWEET_LENGTH + " Characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    client.composeTweet(newTweetContent, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Toast.makeText(ComposeActivity.this, "Tweet Posted", Toast.LENGTH_SHORT).show();
                            try {
                                Tweet tweet = Tweet.fromJson(response);
                                Intent data = new Intent();
                                data.putExtra("tweet", Parcels.wrap(tweet));
                                setResult(RESULT_OK, data);
                                finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Toast.makeText(ComposeActivity.this, "Failure to Post", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}
