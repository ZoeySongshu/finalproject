package com.example.mydeezer;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    String url;
    private Button button;
    private RecyclerView rvSong;
    List<Song> list=new ArrayList<>();
    DetailsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        url = getIntent().getStringExtra("url");
        initView();
    }

    private void initView() {
        button = (Button) findViewById(R.id.button);
        rvSong = (RecyclerView) findViewById(R.id.rv_song);
        adapter=new DetailsAdapter(this,list);
        rvSong.setAdapter(adapter);
        rvSong.setLayoutManager(new LinearLayoutManager(this));
        initData();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this, CollectionActivity.class));
            }
        });
    }
    public void initData(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());


                        try {
                            JSONObject responseJson = new JSONObject(response.toString()); // response 是你从 Deezer 收到的 JSON 数据
                            JSONArray artists = responseJson.getJSONArray("data");
                            //   JSONArray songs = responseJson.getJSONArray("album");

                            // 迭代解析信息数组
                            for (int i = 0; i < artists.length(); i++) {
                                JSONObject artist = artists.getJSONObject(i);
                                // JSONObject song = songs.getJSONObject(i);


                                String id = artist.getString("id");
                                String title = artist.getString("title");
                                String duration = artist.getString("duration");
                                String album = artist.getString("album");
                                JSONObject responseJson2 = new JSONObject(album);
                                String album_title=responseJson2.getString("title");
                                String cover =responseJson2.getString("cover_small");
                                Song song1=new Song(id,title,duration,album_title,cover);
                                list.add(song1);
                                adapter.notifyDataSetChanged();
                                Log.i(TAG, "onResponse: ---------"+song1.toString());
                                // 提取并使用艺术家信息
                            }

                            Log.i("TAG", "onClick: ---------------artlist is " + list.size());

                            // 类似的方式解析 albums 和 tracks 等其他字段
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        queue.add(jsonObjectRequest);
    }
}