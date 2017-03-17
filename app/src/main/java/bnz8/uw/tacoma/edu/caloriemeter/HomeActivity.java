package bnz8.uw.tacoma.edu.caloriemeter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import bnz8.uw.tacoma.edu.caloriemeter.food.Food;
/**
 * A class to hold  the list of foods.
 */

public class HomeActivity extends AppCompatActivity implements FoodFragment.OnListFragmentInteractionListener,
AddFoodFragment.FoodAddListener {
    private ShareActionProvider mShareActionProvider;
    private final static String FOOD_SELECT_URL
            = "http://cssgate.insttech.washington.edu/~_450bteam15/selectFood.php?";

    private Button selectFoodButton;
    private String mEmail;
    private String calorieTotal = "0";
    private ArrayList<String> contentToBeShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mEmail =  getIntent().getExtras().getString("Email");
        contentToBeShared =  new ArrayList<>();
        Intent intent = getIntent();
        setTitle("Home");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFoodFragment foodAddFragment = new AddFoodFragment();
                Bundle bundle = new Bundle();
                bundle.putString("user_email",mEmail);
                foodAddFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, foodAddFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });
        selectFoodButton = (Button) findViewById(R.id.select_food_button);

        selectFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectFoodFragment foodSelectFragment = new SelectFoodFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Email",mEmail);
                foodSelectFragment.setArguments(bundle);
                //selectFoodButton.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, foodSelectFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });
        if (savedInstanceState == null || getSupportFragmentManager().findFragmentById(R.id.list) == null) {
            FoodFragment foodFragment = new FoodFragment();

            Bundle bundle = new Bundle();
            bundle.putString("Email",mEmail);
            bundle.putString("CalTotal", calorieTotal);

            bundle.putStringArrayList("content",contentToBeShared);
            foodFragment.setArguments(bundle);

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

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
            return true;
        } if(id == R.id.menu_item_share) {
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);



            TextView calorieDisplay = (TextView) findViewById(R.id.calorieDisplayAmountTextView);
            StringBuilder sb = new StringBuilder();

            calorieTotal = calorieDisplay.getText().toString();
            sb.append( "Calorie total for " + mEmail + " is: " + calorieTotal);
            sb.append('\n');
            sb.append('\n');
            sb.append("Their Menu Was: ");

            for(String menuItem: contentToBeShared) {
                sb.append(menuItem);
            }

            sentIntent.putExtra(Intent.EXTRA_TEXT,sb.toString());

            sentIntent.setType("text/plain");
            startActivity(sentIntent);
            finish();
        }
        return true;
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_log_out, menu);
//        return true;
//    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
            //

        getMenuInflater().inflate(R.menu.menu_share, menu);
        getMenuInflater().inflate(R.menu.menu_log_out,menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);
        // Fetch and store ShareActionProvider
//        mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.menu_item_share).getActionProvider();

            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        // Return true to display menu
        return true;

//            mShareActionProvider.setOnShareTargetSelectedListener(this);


    }
    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
    @Override
    public void onListFragmentInteraction(Food food) {
        FoodDetailFragment foodDetailFragment = new FoodDetailFragment();
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(f instanceof SelectFoodFragment) {
            String s = FOOD_SELECT_URL;
            s += "&foodID=";
            s += food.getID();
            s += "&user_email=";
            s += mEmail;
            addFood(s);
        } else {
            Bundle args = new Bundle();
            args.putSerializable(FoodDetailFragment.FOOD_ITEM_SELECTED, food);
            foodDetailFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, foodDetailFragment)
                    .addToBackStack(null)
                    .commit();
        }


    }


    @Override
    public void addFood(String url) {
        AddFoodTask task = new AddFoodTask();
        task.execute(url.toString());

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
                //Toast.makeText(getApplicationContext(), "Something wrong with the data: " +
                //        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}

