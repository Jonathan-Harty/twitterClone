package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    Context context;
    List<Tweet> tweets = new ArrayList<>();

    public TweetAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Tweet tweet = tweets.get(i);
        viewHolder.tvTweetBody.setText(tweet.body);
        viewHolder.tvScreenName.setText(tweet.user.screenName);
        Glide.with(context).load(tweet.user.profilePicUrl).into(viewHolder.ivProfilePic);
        viewHolder.tvCreatedAt.setText(tweet.createdAt);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfilePic;
        TextView tvScreenName;
        TextView tvTweetBody;
        TextView tvCreatedAt;

        public ViewHolder(View itemView) {
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvTweetBody = itemView.findViewById(R.id.tvTweetBody);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
        }
    }
}
