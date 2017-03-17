package bnz8.uw.tacoma.edu.caloriemeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * A class that displays the welcome screen.
 */

public class WelcomeActivity extends AppCompatActivity implements RegisterFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        setTitle("Calorie Meter");

    }

    // A method to change welcome activity to log in fragment
    public void proceedAction(View v){
        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(intent);
    }

    //A method to switch to register fragment
    public void switchToSignUp() {

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new RegisterFragment()).addToBackStack(null)
                .commit();
    }
}
