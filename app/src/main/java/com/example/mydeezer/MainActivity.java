package com.example.mydeezer;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    private EditText etQuery;
    private ImageView ivSearch;
    List<Artist> artistList = new ArrayList<>();
    ArtistAdapter adapter;
    private RecyclerView rvArtist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        etQuery = (EditText) findViewById(R.id.et_query);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        rvArtist = (RecyclerView) findViewById(R.id.rv_artist);
        SharedPreferences setting = getSharedPreferences("hello",MODE_PRIVATE);

        String name = setting.getString("search","");
        if(!name.isEmpty()){
            etQuery.setText(name);
        }

        adapter=new ArtistAdapter(getApplicationContext(),artistList);
        rvArtist.setAdapter(adapter);
        rvArtist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter.setOnItemClickListener(new ArtistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra("url",artistList.get(position).getTracklist());
                startActivity(intent);
            }
        });
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String artist = etQuery.getText().toString();
//
                SharedPreferences.Editor editor = setting.edit();
                editor.putString("search",artist);
                editor.commit();
                if (!artist.isEmpty()) {
                    artistList.clear();
                    String url = "https://api.deezer.com/search/artist/?q=" + artist;
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("TAG", response.toString());


                                    try {
                                        JSONObject responseJson = new JSONObject(response.toString()); // response 是你从 Deezer 收到的 JSON 数据
                                        JSONArray artists = responseJson.getJSONArray("data");
                                        // 迭代解析艺术家信息数组
                                        for (int i = 0; i < artists.length(); i++) {
                                            JSONObject artist = artists.getJSONObject(i);
                                            String id = artist.getString("id");
                                            String artistName = artist.getString("name");
                                            String link = artist.getString("link");
                                            String picture = artist.getString("picture_medium");
                                            String nb_album = artist.getString("nb_album");
                                            String nb_fan = artist.getString("nb_fan");
                                            String radio = artist.getString("radio");
                                            String tracklist = artist.getString("tracklist");
                                            Artist art = new Artist(id, artistName, link, picture, nb_album, nb_fan, radio, tracklist, false);
                                            artistList.add(art);
                                            Log.i(TAG, "onResponse: " + art.toString());

                                            // 提取并使用艺术家信息
                                        }
                                        adapter.notifyDataSetChanged();
                                        Log.i("TAG", "onClick: ---------------artlist is " + artistList.size());

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
                } else {
                    Toast.makeText(getApplicationContext(), "is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}