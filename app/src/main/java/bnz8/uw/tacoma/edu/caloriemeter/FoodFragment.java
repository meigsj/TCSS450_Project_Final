package bnz8.uw.tacoma.edu.caloriemeter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import bnz8.uw.tacoma.edu.caloriemeter.food.Food;

/**
 * A fragment representing a list of selectable foods.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FoodFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;


    private ArrayList<String> mContentToBeShared;
    private String mEmail;
    private String mCalTotal;

    /*
    private static final String FOOD_URL
            = "http://cssgate.insttech.washington.edu/~_450bteam15/food_list.php?cmd=food";
    */
    private static final String FOOD_URL
            = "http://cssgate.insttech.washington.edu/~meigsj/food_list.php?cmd=food";
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FoodFragment() {
    }

    @SuppressWarnings("unused")
    public static FoodFragment newInstance(int columnCount) {
        FoodFragment fragment = new FoodFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fragment f = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);
        mEmail = getArguments().getString("Email");
        mCalTotal = getArguments().getString("CalTotal");
        mContentToBeShared = getArguments().getStringArrayList("content");

        // Set the adapter

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            DownloadFoodTask task = new DownloadFoodTask();

            String foodURL = FOOD_URL;
            foodURL += "&user_email=";
            foodURL += mEmail;
            task.execute(foodURL);
        }
        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.show();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Food item);
    }


    private class DownloadFoodTask extends AsyncTask<String, Void, String> {


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
                    response = "Unable to download the list of Foods, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }
            //mFoodList = new ArrayList<Food>();
            List<Food> foodList = new ArrayList<Food>();
            result = Food.parseFoodJSON(result, foodList);
            int calorie_total = calcTotalCalories(foodList);
            mCalTotal = String.valueOf(calorie_total);
            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            // Everything is good, show the list of foods.
            if (!foodList.isEmpty()) {
                TextView calorieDisplay = (TextView) getActivity().findViewById(R.id.calorieDisplayAmountTextView);
                calorieDisplay.setText(mCalTotal);
                mRecyclerView.setAdapter(new MyItemRecyclerViewAdapter(foodList, mListener));
            }
        }

        public int calcTotalCalories(List<Food> foods) {
            int calorieTotal = 0;
            int i = 0;
            for (Food food: foods) {
                mContentToBeShared.add(i,food.getName());
                mContentToBeShared.add(i+1," ");
                mContentToBeShared.add(i+2,food.getCalorieCount());
                mContentToBeShared.add(i+3,", ");
                i+=4;
                String calories = food.getCalorieCount();
                calorieTotal += Integer.parseInt(calories);
            }
            return calorieTotal;
        }

    }
}
