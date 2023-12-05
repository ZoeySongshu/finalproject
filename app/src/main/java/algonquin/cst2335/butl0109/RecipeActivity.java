package algonquin.cst2335.butl0109;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import algonquin.cst2335.butl0109.databinding.ActivityMainBinding;

/**
 * @author Ryan Butler
 * @Version 1.0
 */
public class RecipeActivity extends AppCompatActivity implements WebAdapter.OnItemClickListener {

    public static final String EXTRA_URL = "imageurl";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_ID = "id";
    private RecyclerView jRecyclerView;
    private WebAdapter jAdapter;
    private ArrayList<WebInfo> jInfo;
    private RequestQueue jRequest;

    String searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        jRecyclerView = findViewById(R.id.recyclerView);
        jRecyclerView.setHasFixedSize(true);
        jRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        jInfo = new ArrayList<>();

        jRequest = Volley.newRequestQueue(this);
        binding.button.setOnClickListener(click -> {
            try {
                searchTerm = URLEncoder.encode(binding.edittext.getText().toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            String url = "https://api.spoonacular.com/recipes/complexSearch?query=" + searchTerm + "&apiKey=03477e102d7b4a69bbe63ef6989afbe7";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("results");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject result = jsonArray.getJSONObject(i);

                                    String title = result.getString("title");
                                    String id = result.getString("id");
                                    String imageURL = result.getString("image");

                                    jInfo.add(new WebInfo(imageURL, title, id));
                                }
                                jAdapter = new WebAdapter(RecipeActivity.this, jInfo);
                                jRecyclerView.setAdapter(jAdapter);
                                jAdapter.setOnItemClickListener((WebAdapter.OnItemClickListener) RecipeActivity.this);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error){
                    error.printStackTrace();
                }
            });
            jRequest.add(request);
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, Detail.class);
        WebInfo clickedItem = jInfo.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_ID, clickedItem.getIdInfo());

        startActivity(detailIntent);
    }
}