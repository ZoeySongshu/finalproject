package algonquin.cst2335.butl0109;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2335.butl0109.databinding.ActivityMainBinding;

/**
 * @author Ryan Butler
 * @Version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * holds text at center of screen
     */
    private TextView tv = null;
    /**
     * holds login button at bottom of screen
     */
    private Button btn = null;
    /**
     * holds edittext at center of screen
     */
    private EditText et = null;
    /**
     * will hold string value of edittext
     */
    private String password = null;

    protected String cityName;

    RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tv = findViewById(R.id.textView);
        btn = findViewById(R.id.button);
        et = findViewById(R.id.edittext);

        /**
         * on click listener that checks password to make sure it contains
         * sufficient complexity

         btn.setOnClickListener( clk -> {
         password = et.getText().toString();
         checkPasswordComplexity(password);
         if (checkPasswordComplexity(password)) {
         tv.setText("Your password meets the requirements");
         }

         else {
         tv.setText("You shall not pass!");
         }
         });*/

        queue = Volley.newRequestQueue(this);


        binding.button.setOnClickListener(click -> {
            try {
                cityName = URLEncoder.encode(binding.edittext.getText().toString(), "UTF-8");
                String stringURL = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                        (response) -> {
                    try {
                        JSONObject coord = response.getJSONObject( "coord" );
                        JSONArray weatherArray = response.getJSONArray ( "weather" );
                        JSONObject position0 = weatherArray.getJSONObject(0);
                        String description = position0.getString("description");
                        String iconName = position0.getString("icon");

                        JSONObject mainObject = response.getJSONObject( "main" );
                        double current = mainObject.getDouble("temp");
                        double min = mainObject.getDouble("temp_min");
                        double max = mainObject.getDouble("temp_max");
                        int humidity = mainObject.getInt("humidity");

                        int vis = response.getInt("visibility");
                        String name = response.getString( "name" );

                        runOnUiThread( ( ) -> {

                            binding.temp.setText("The current temperature is " + current);
                            binding.temp.setVisibility(View.VISIBLE);
                            binding.maxTemp.setText("The maximum temperature is " + max);
                            binding.maxTemp.setVisibility(View.VISIBLE);
                            binding.minTemp.setText("The minimum temperature is " + min);
                            binding.minTemp.setVisibility(View.VISIBLE);
                            binding.humidity.setText("The humidity is " + humidity);
                            binding.humidity.setVisibility(View.VISIBLE);
                            binding.description.setText(description);
                            binding.description.setVisibility(View.VISIBLE);
                        });

                        String pathname = getFilesDir() + "/" + iconName + ".png";
                        File file = new File(pathname);
                        if(file.exists()){
                            binding.weatherImage.setImageBitmap(BitmapFactory.decodeFile(pathname));
                        }
                        else {
                            String imageUrl = "https://openweathermap.org/img/w/" + iconName + ".png";
                            ImageRequest imgReq = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap bitmap) {
                                    // Do something with loaded bitmap...
                                    binding.weatherImage.setImageBitmap(bitmap);
                                    Log.d("Image received", "Got image");
                                    FileOutputStream fOut = null;
                                    try {
                                        fOut = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);

                                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                        fOut.flush();
                                        fOut.close();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();

                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }, 1024, 1024, ImageView.ScaleType.CENTER, null,
                                    (error) -> {
                                        Log.d("Image not received", "Did not get image");
                                    });
                            queue.add(imgReq);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                        },
                        (error) -> { } );
                queue.add(request);

            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

        });


        /**
         * @param c character object from String we are processing
         * @return Returns true if c is a special character

        boolean isSpecialCharacter (char c) {
        switch (c) {
        case '#' :
        case '$' :
        case '%' :
        case '^' :
        case '&' :
        case '*' :
        case '!' :
        case '@' :
        case '?' :
        return true;
        default:
        return false;
        }
        }*/


        /**
         * @param pw string object we are making
         * @return Returns true if password has sufficient complexity

        boolean checkPasswordComplexity (String pw) {

        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;

        foundUpperCase = false;
        foundLowerCase = false;
        foundNumber = false;
        foundSpecial = false;

        int duration = Toast.LENGTH_LONG;

        for (int i = 0; i < pw.length(); i++) {

        char c = pw.charAt(i);
        if (Character.isLowerCase(c))
        foundLowerCase = true;

        else if (Character.isUpperCase(c))
        foundUpperCase = true;

        else if (Character.isDigit(c))
        foundNumber = true;

        else if (isSpecialCharacter(c))
        foundSpecial = true;

        }

        if (!foundUpperCase) {

        Toast.makeText(this, "You are missing an upper case letter", duration);// Say that they are missing an upper case letter;

        return false;

        } else if (!foundLowerCase) {
        Toast.makeText(this, "You are missing a lower case letter", duration); // Say that they are missing a lower case letter;

        return false;

        } else if (!foundNumber) {
        Toast.makeText(this, "You are missing a number", duration);

        return false;
        } else if (!foundSpecial) {
        Toast.makeText(this, "You are missing a special character", duration);

        return false;
        } else

        return true; //only get here if they're all true
        }*/
    }
}