package bnz8.uw.tacoma.edu.caloriemeter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * A class to manage log in and register fragments.
 */
public class MainActivity extends AppCompatActivity implements SignInFragment.LoginInteractionListener {

    private Intent mIntent;
    private SharedPreferences mSharedPreferences;

    private static final String REG_URL = "http://cssgate.insttech.washington.edu/~_450bteam15/adduser.php?";
    private static final String LOGIN_URL = "http://cssgate.insttech.washington.edu/~_450bteam15/login.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS)
                , Context.MODE_PRIVATE);
        if (mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false)) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new SignInFragment())
                    .commit();
        }
    }

    /**
     * A method to be able to switch the log in fragment to the Homeactivity class.
     * up on successfull entry of email and password and network connectivity.
     *
     * @param email    the email to be entered.
     * @param password the password to be entered.
     */
    @Override
    public void login(String email, String password) {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            mIntent = new Intent(this, HomeActivity.class);
            RegisteryTask task = new RegisteryTask();
            task.execute(buildString(email, password, LOGIN_URL));

        } else {
            Toast.makeText(this, "No network connection available. Cannot provide services",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A method to register new users up on checking network connectivity.
     *
     * @param email    the email to be registered.
     * @param password the password to be registered.
     */
    public void register(String email, String password) {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            mIntent = new Intent(this, HomeActivity.class);
            RegisteryTask task = new RegisteryTask();
            task.execute(buildString(email, password, REG_URL));
        } else {
            Toast.makeText(this, "No network connection available. Cannot provide services",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * A method to switch to the registeration fragment.
     */
    public void switchToSignUpFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new RegisterFragment()).addToBackStack(null).commit();
    }

    /**
     * A private method to create the email and passowrds.
     * @param email the email to be appended.
     * @param password the password to be appeded.
     * @param url
     * @return
     */
    private String buildString(String email, String password, String url) {
        StringBuilder stringBuilder = new StringBuilder(url);

        try {
            stringBuilder.append("email=");
            stringBuilder.append(URLEncoder.encode(email, "UTF-8"));

            stringBuilder.append("&password=");
            stringBuilder.append(URLEncoder.encode(password, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return stringBuilder.toString();
    }

    private class RegisteryTask extends AsyncTask<String, Void, String> {

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
                    response = "Unable to log in, Reason: " + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_LONG).show();
                    mSharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN),true).commit();
                    startActivity(mIntent);
                    MainActivity.this.finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed! " + jsonObject.get("error")
                            , Toast.LENGTH_LONG).show();
                }
            }
            catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}