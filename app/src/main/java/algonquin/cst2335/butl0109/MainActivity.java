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
        parseJSON();
    }

    private void parseJSON() {

        String url = "https://api.spoonacular.com/recipes/complexSearch?query="  + "&apiKey=03477e102d7b4a69bbe63ef6989afbe7"
    }
}