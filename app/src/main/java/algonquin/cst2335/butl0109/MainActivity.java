package algonquin.cst2335.butl0109;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        btn = findViewById(R.id.button);
        et = findViewById(R.id.edittext);

        /**
         * on click listener that checks password to make sure it contains
         * sufficient complexity
         */
        btn.setOnClickListener( clk -> {
            password = et.getText().toString();
            checkPasswordComplexity(password);
            if (checkPasswordComplexity(password)) {
                tv.setText("Your password meets the requirements");
            }

            else {
                tv.setText("You shall not pass!");
            }
        });
    }


    /**
     * @param c character object from String we are processing
     * @return Returns true if c is a special character
     */
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
    }


    /**
     * @param pw string object we are making
     * @return Returns true if password has sufficient complexity
     */
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
        }
}