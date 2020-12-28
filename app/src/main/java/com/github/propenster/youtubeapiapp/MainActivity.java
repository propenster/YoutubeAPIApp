package com.github.propenster.youtubeapiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.propenster.youtubeapiapp.Adapter.MyCustomAdapter;
import com.github.propenster.youtubeapiapp.Models.VideoDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String url;
    ArrayList<VideoDetails> videoDetailsArrayList;
    ListView listView;
    MyCustomAdapter myCustomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = getResources().getString(R.string.API_URL);
        //url = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCSl6OzXEfKSwm1CBBJWumHQ&maxResults=5&key=AIzaSyCg1gk4YEXHqESWCkmwIOLniQFRqGLJP0c";
        //url = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCwWhs_6x42TyRM4Wstoq8HA&maxResults=7&key=AIzaSyCg1gk4YEXHqESWCkmwIOLniQFRqGLJP0c";

        listView = (ListView) findViewById(R.id.listView);
        videoDetailsArrayList = new ArrayList<VideoDetails>();
        myCustomAdapter = new MyCustomAdapter(MainActivity.this, this.videoDetailsArrayList);

        displayVideos();
    }

    public void displayVideos() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                try {
                    JSONArray videoItemArrayJSON = response.getJSONArray("items");
                    for(int i=1; i<videoItemArrayJSON.length(); i++){
                        JSONObject jsonObject1 = videoItemArrayJSON.getJSONObject(i);
                        //id from the API contains the most important stuff - videoId
                        JSONObject videoId = jsonObject1.getJSONObject("id");
                        JSONObject snippetJSONObjectFromGoogleAPI = jsonObject1.getJSONObject("snippet");
                        //we obtain thumbnails from the snippet JSONObject
                        //Snipet contains publishedAt, channelId, title, description, thumbnails, channelTitle, liveBroadcastContent, publishTime
                        JSONObject thumbnailMediumDefault = snippetJSONObjectFromGoogleAPI.getJSONObject("thumbnails").getJSONObject("medium");

                        //Get the video_id into a String
                        //String video_id = videoId.getString("videoId");
                        //Now, fill the VideoDetails Model with these freaking data
                        VideoDetails vd = new VideoDetails();
                        vd.setVideoId(videoId.getString("videoId"));
                        vd.setTitle(snippetJSONObjectFromGoogleAPI.getString("title"));
                        vd.setDescription(snippetJSONObjectFromGoogleAPI.getString("description"));
                        vd.setImageUrl(thumbnailMediumDefault.getString("url"));

                        videoDetailsArrayList.add(vd);
                        //Toast.makeText(MainActivity.this, videoDetailsArrayList.toString(), Toast.LENGTH_LONG).show();
                        Log.v("JSONAPIResults",videoDetailsArrayList.toString());
                    }
                    listView.setAdapter(myCustomAdapter);
                    myCustomAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        requestQueue.add(jsonObjectRequest);
    }
}