package bnz8.uw.tacoma.edu.caloriemeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * A class that displays the welcome screen.
 */

public class WelcomeActivity extends AppCompatActivity implements RegisterFragment.OnFragmentInteractionListener{
    private Button mRegBtn;
    private Intent mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setTitle("Calorie Meter");

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
