package bnz8.uw.tacoma.edu.caloriemeter;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import bnz8.uw.tacoma.edu.caloriemeter.food.Food;
/**
 * A class to hold  the list of foods.
 */

public class HomeActivity extends AppCompatActivity implements FoodFragment.OnListFragmentInteractionListener,
AddFoodFragment.FoodAddListener {

//    private LoginButton loginButton;

    //    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFoodFragment foodAddFragment = new AddFoodFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, foodAddFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });
        if (savedInstanceState == null || getSupportFragmentManager().findFragmentById(R.id.list) == null) {
            FoodFragment foodFragment = new FoodFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, foodFragment)
                    .commit();
        }
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            SharedPreferences msharedPreferences =
                    getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
            msharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false)
                    .commit();
            msharedPreferences.edit().putString(getString(R.string.loggedin_email), null).commit();

            Intent i = new Intent(this, WelcomeActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_food_list, menu);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Food item) {
        FoodDetailFragment foodDetailFragment = new FoodDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(FoodDetailFragment.FOOD_ITEM_SELECTED, item);
        foodDetailFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, foodDetailFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addFood(String url) {

        AddFoodTask task = new AddFoodTask();
        task.execute(new String[]{url.toString()});

// Takes you back to the previous fragment by popping the current fragment out.
        getSupportFragmentManager().popBackStackImmediate();
    }


    private class AddFoodTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to add course, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. It tries to call the parse Method and checks to see if it was successful.
         * If not, it displays the exception.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Successfully added!"
                            , Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data: " +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}

