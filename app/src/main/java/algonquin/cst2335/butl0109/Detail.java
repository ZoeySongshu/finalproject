package algonquin.cst2335.butl0109;

import static algonquin.cst2335.butl0109.RecipeActivity.EXTRA_ID;
import static algonquin.cst2335.butl0109.RecipeActivity.EXTRA_TITLE;
import static algonquin.cst2335.butl0109.RecipeActivity.EXTRA_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.butl0109.databinding.ActivityDetailBinding;


public class Detail extends AppCompatActivity {

    private ArrayList<WebInfo> jInfo;
    String searchTerm;

    String id;

    String title;

    String summary;

    Context jContext;
    private RequestQueue jRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        String imageURL = intent.getStringExtra(EXTRA_URL);
        String title = intent.getStringExtra(EXTRA_TITLE);
        String id = intent.getStringExtra(EXTRA_ID);

        jInfo = new ArrayList<>();

        ImageView imageView = findViewById(R.id.detailImage);

        try {
            searchTerm = URLEncoder.encode(id, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String url = "https://api.spoonacular.com/recipes/" + searchTerm + "/information?apiKey=03477e102d7b4a69bbe63ef6989afbe7";

        jRequest = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            summary = response.getString("summary");
                            binding.summary.setText(summary);
                            String imageURL = response.getString("image");
                            Picasso.with(jContext)
                                    .load(imageURL)
                                    .into(imageView);
                            String sourceURL = response.getString("sourceUrl");
                            binding.sourceURL.setText(sourceURL);
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
    };

}