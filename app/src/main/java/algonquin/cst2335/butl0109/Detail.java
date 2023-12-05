package algonquin.cst2335.butl0109;

import static algonquin.cst2335.butl0109.MainActivity.EXTRA_ID;
import static algonquin.cst2335.butl0109.MainActivity.EXTRA_TITLE;
import static algonquin.cst2335.butl0109.MainActivity.EXTRA_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import algonquin.cst2335.butl0109.databinding.ActivityDetailBinding;

public class Detail extends AppCompatActivity {

    private ArrayList<WebInfo> jInfo;
    String searchTerm;

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

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String summary = response.getString("summary");
                            binding.summary.setText(summary);
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