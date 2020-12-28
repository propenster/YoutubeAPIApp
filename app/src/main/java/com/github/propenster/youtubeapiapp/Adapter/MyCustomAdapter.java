package com.github.propenster.youtubeapiapp.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.propenster.youtubeapiapp.Models.VideoDetails;
import com.github.propenster.youtubeapiapp.R;
import com.github.propenster.youtubeapiapp.VideoPlay;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<VideoDetails> videoDetailsList;
    LayoutInflater inflater;

    public MyCustomAdapter(Activity activity, ArrayList<VideoDetails> videoDetailsList) {
        this.activity = activity;
        this.videoDetailsList = videoDetailsList;
    }

    @Override
    public int getCount() {
        return this.videoDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.videoDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       if(inflater == null){
           inflater = this.activity.getLayoutInflater();
       }
       if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.custom_item, null);
        }

        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
        TextView textView = (TextView)convertView.findViewById(R.id.videoTitle);
        LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.rootLinearLayout);
        VideoDetails videoDetails = (VideoDetails)this.videoDetailsList.get(position);


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, VideoPlay.class);
                intent.putExtra("videoId", videoDetails.getVideoId());
                activity.startActivity(intent);
            }
        });


        Picasso.get().load(videoDetails.getImageUrl()).into(imageView);
        textView.setText(videoDetails.getTitle());


        return convertView;
    }
}
