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
import android.util.Log;
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

public class MainActivity extends AppCompatActivity implements SignInFragment.LoginInteractionListener {
    private Intent mIntent;
    private static final String REG_URL = "http://cssgate.insttech.washington.edu/~bnz8/adduser.php?";
    private static final String LOGIN_URL = "http://cssgate.insttech.washington.edu/~bnz8/login.php?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, new SignInFragment())
                        .commit();
            }

    @Override
    public void login(String email, String password) {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){

            mIntent = new Intent(this, HomeActivity.class);
            RegisteryTask task = new RegisteryTask();
            task.execute(buildString(email, password, LOGIN_URL));

        } else {
            Toast.makeText(this, "No network connection available. Cannot provide services",
                    Toast.LENGTH_LONG).show();
        }


//        Intent i = new Intent(this, HomeActivity.class);
//        startActivity(i);
//        finish();


    }

    public void register(String email, String password){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){

            mIntent = new Intent(this, HomeActivity.class);
            RegisteryTask task = new RegisteryTask();
            task.execute(buildString(email, password, REG_URL));
        } else {
            Toast.makeText(this, "No network connection available. Cannot provide services",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void switchToSignUpFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new RegisterFragment()).addToBackStack(null).commit();
    }

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
//                    Toast.makeText(getApplicationContext(), jsonObject.get("message").toString()
//                            , Toast.LENGTH_LONG).show();
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
                Log.e("WRX2", "exception thrown"+ e.getMessage());
            }
        }
    }
}














//    private boolean comfirm(String email, String password) {
//        boolean result = true;
//
//        if (TextUtils.isEmpty(email)) {
//            Toast.makeText(this, "Please enter your email.", Toast.LENGTH_LONG).show();
//            result = false;
//        } else if (!email.contains("@")) {
//            Toast.makeText(this, "Please enter a valid email.", Toast.LENGTH_LONG).show();
//            result = false;
//        } else if (email.length() > 40) {
//            Toast.makeText(this, "Please enter a valid email (less than fourty characters).",
//                    Toast.LENGTH_LONG).show();
//            result = false;
//        } else if (TextUtils.isEmpty(password)) {
//            Toast.makeText(this, "Please enter your password.", Toast.LENGTH_LONG).show();
//            result = false;
//        } else if (password.length() > 20) {
//            Toast.makeText(this, "Please enter a valid password (less than twenty characters).",
//                    Toast.LENGTH_LONG).show();
//            result = false;
//        } else if (password.length() < 6) {
//            Toast.makeText(this, "Please enter a valid password (more than six characters).",
//                    Toast.LENGTH_LONG).show();
//            result = false;
//        }
//
//        return result;
//    }
