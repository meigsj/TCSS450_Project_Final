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

    private LoginButton mloginButton;
    private CallbackManager mcallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        mloginButton = (LoginButton)findViewById(R.id.fb_login_button);
        setTitle("Calorie Meter");

        mcallbackManager = CallbackManager.Factory.create();
        mloginButton.registerCallback(mcallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(WelcomeActivity.this,HomeActivity.class);
                startActivity(intent);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        mcallbackManager.onActivityResult(requestCode,resultCode,data);

    }
    // A methd to change welcome activity to log in fragment
    public void proceedAction(View v){
        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(intent);
    }

    //A method to switch to register fragment
    public void switchToSignUp() {

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new RegisterFragment()).addToBackStack(null).commit();
    }
}
