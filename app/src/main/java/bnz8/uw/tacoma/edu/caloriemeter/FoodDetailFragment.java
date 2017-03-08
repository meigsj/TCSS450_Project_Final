package bnz8.uw.tacoma.edu.caloriemeter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bnz8.uw.tacoma.edu.caloriemeter.food.Food;


public class FoodDetailFragment extends Fragment {


    private TextView mFoodNameTextView;
    private TextView mFoodCalorieTextView;
    private TextView mFoodSugerTextView;
    private TextView mFoodProteinTextView;
    private TextView mFoodSodiumTextView;

    private OnFragmentInteractionListener mListener;

    public FoodDetailFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_food_detail, container, false);

        mFoodNameTextView = (TextView) view.findViewById(R.id.food_name);
        mFoodCalorieTextView = (TextView) view.findViewById(R.id.food_cal_count);
        mFoodSugerTextView = (TextView) view.findViewById(R.id.food_suger_count);
        mFoodProteinTextView = (TextView) view.findViewById(R.id.food_protein_count);
        mFoodSodiumTextView = (TextView) view.findViewById(R.id.food_sodium_count);


        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.show();

        return view;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    public void updateView(Food food) {
        if (food != null) {
            mFoodNameTextView.setText(food.getName());
            mFoodCalorieTextView.setText(food.getCalorieCount());
            mFoodSugerTextView.setText(food.getmSugerAmount());
            mFoodProteinTextView.setText(food.getmProtienAmount());
            mFoodSodiumTextView.setText(food.getmSodiumAmount());
        }
    }


    public final static String FOOD_ITEM_SELECTED = "Food_selected";

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateView((Food) args.getSerializable(FOOD_ITEM_SELECTED));
        }
    }
}





