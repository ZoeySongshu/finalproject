package algonquin.cst2335.butl0109;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import algonquin.cst2335.butl0109.databinding.ActivityMainBinding;

/***
 * @author Ryan Butler
 * @Version 1.0
 */
public class RecipeActivity extends AppCompatActivity implements WebAdapter.OnItemClickListener {

    /** stores imageurl value */
    public static final String EXTRA_URL = "imageurl";

    /** stores title value */
    public static final String EXTRA_TITLE = "title";

    /** stores id value */
    public static final String EXTRA_ID = "id";

    /** for manipulating recyclerview */
    private RecyclerView jRecyclerView;

    /** adapter for recyclerview */
    private WebAdapter jAdapter;

    /** array of information to go into recyclerview */
    private ArrayList<WebInfo> jInfo;

    /** obtains JSON information */
    private RequestQueue jRequest;

    /** stores value entered in the edittext */
    String searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.myToolbar); /** makes toolbar visible */

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedText = prefs.getString("SearchText", "");
        binding.edittext.setText(savedText);  /** sets the edittext to the stored value */


        jRecyclerView = findViewById(R.id.recyclerView);
        jRecyclerView.setHasFixedSize(true);
        jRecyclerView.setLayoutManager(new LinearLayoutManager(this)); /** controls how the recyclerview displays */

        jInfo = new ArrayList<>(); /** array of data to be shown in the recyclerview */

        jRequest = Volley.newRequestQueue(this); /** creates request for JSON information */
        binding.button.setOnClickListener(click -> { /** enters the search value, acquires search hits from server */
            try {
                searchTerm = URLEncoder.encode(binding.edittext.getText().toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            String url = "https://api.spoonacular.com/recipes/complexSearch?query=" + searchTerm + "&apiKey=03477e102d7b4a69bbe63ef6989afbe7";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) { /** fills the recyclerview with information, */
                            try {                                     /** size changes based on number of search results */
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
                                /** controls behavior for when objects in recyclerview */
                                /** are clicked */
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
            Toast.makeText(getApplicationContext(),searchTerm, /** displays a Toast on button click */
                    Toast.LENGTH_SHORT).show();
            Snackbar.make(binding.getRoot(), searchTerm, Snackbar.LENGTH_SHORT).show(); /** displays a snackbar on button click */
            jRequest.add(request);
            String userInput = binding.edittext.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("SearchText", userInput);
            editor.apply();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { /** makes toolbar menu visible */
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { /** displays help AlertDialog when toolbar menu option is clicked */

        switch( item.getItemId() )
        {
            case R.id.item_1:

                AlertDialog.Builder builder = new AlertDialog.Builder(RecipeActivity.this);
                builder.setMessage("Enter a recipe into the text field");
                builder.show();

                break;
        }

        return true;
    }

    @Override
    public void onItemClick(int position) { /** grabs recyclerview position, passes relevant information to next activity */
        Intent detailIntent = new Intent(this, Detail.class);
        WebInfo clickedItem = jInfo.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_ID, clickedItem.getIdInfo());



        startActivity(detailIntent);
    }
}