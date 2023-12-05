package algonquin.cst2335.butl0109;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import algonquin.cst2335.butl0109.databinding.ActivityMainBinding;
import algonquin.cst2335.butl0109.databinding.RecipeSearchBinding;

/**
 * @author Ryan Butler
 * @Version 1.0
 */
public class MainActivity extends AppCompatActivity {
    private RecyclerView jRecyclerView;
    private webAdapter jAdapter;
    private ArrayList<webInfo> jInfo;
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
                                    int id = result.getInt("id");
                                    String imageURL = result.getString("image");

                                    jInfo.add(new webInfo(imageURL, title, id));
                                }
                                jAdapter = new webAdapter(MainActivity.this, jInfo);
                                jRecyclerView.setAdapter(jAdapter);
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
}