package algonquin.cst2335.butl0109;

import static android.app.ProgressDialog.show;
import static algonquin.cst2335.butl0109.RecipeActivity.EXTRA_ID;
import static algonquin.cst2335.butl0109.RecipeActivity.EXTRA_TITLE;
import static algonquin.cst2335.butl0109.RecipeActivity.EXTRA_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
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


    /** stores id value from previous activity */
    String searchTerm;

    /** stores summary information from JSON request */
    String summary;

    /** provides context for imagerequest */
    Context jContext;

    /** queues JSON data request */
    private RequestQueue jRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.myToolbar2); /** makes toolbar visible */

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedText = prefs.getString("EditText", "");
        binding.otherEditText.setText(savedText);

        Intent intent = getIntent(); /** receives information from previous activity */
        String imageURL = intent.getStringExtra(EXTRA_URL);
        String title = intent.getStringExtra(EXTRA_TITLE);
        String id = intent.getStringExtra(EXTRA_ID);


        ImageView imageView = findViewById(R.id.detailImage);

        try {
            searchTerm = URLEncoder.encode(id, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String url = "https://api.spoonacular.com/recipes/" + searchTerm + "/information?apiKey=03477e102d7b4a69bbe63ef6989afbe7";

        jRequest = Volley.newRequestQueue(this); /** fetches JSON information */

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) { /** populates textviews and imageview with JSON data  */
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
        jRequest.add(request); /** queues JSON request */

        Button button = findViewById(R.id.saveButton); /** displays Toast and Snackbar when button is clicked */
        button.setOnClickListener(clk -> {
            Toast.makeText(getApplicationContext(),"Idk how to make a database lol",
                    Toast.LENGTH_SHORT).show();
            Snackbar.make(binding.getRoot(), "Idk how to make a database lol", Snackbar.LENGTH_SHORT).show();
        });

        Button editButton = findViewById(R.id.editButton); /** saves value in edittext to sharedprefs folder */
        editButton.setOnClickListener(click -> {
            String userInput = binding.otherEditText.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("EditText", userInput);
            editor.apply();
        });
    };

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

                AlertDialog.Builder builder = new AlertDialog.Builder(Detail.this);
                builder.setMessage("Click the lower button to save this recipe to the database");
                builder.show();

                break;
        }

        return true;
    }

}