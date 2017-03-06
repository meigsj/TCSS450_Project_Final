package bnz8.uw.tacoma.edu.caloriemeter;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bnz8.uw.tacoma.edu.caloriemeter.food.Food;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FoodDetailFragment} factory method to
 * create an instance of this fragment.
 */
public class FoodDetailFragment extends Fragment {
    public final static String FOOD_ITEM_SELECTED = "Food_selected";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TextView mFoodNameTextView;
    private TextView mFoodCalorieTextView;
// Required empty public constructor
    public FoodDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_food_detail, container, false);
        mFoodNameTextView = (TextView) view.findViewById(R.id.food_name);
        mFoodCalorieTextView = (TextView) view.findViewById(R.id.food_cal_count);

        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.show();

        return view;
    }

    public void updateView(Food food) {
        if (food != null) {
            mFoodNameTextView.setText(food.getName());
            mFoodCalorieTextView.setText(food.getCalorieCount());
        }
    }

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





