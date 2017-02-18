package bnz8.uw.tacoma.edu.caloriemeter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFoodFragment extends Fragment {
    private static final String NAME = "Name";
    private static final String CALORIE_COUNT = "Calorie_Count";

    private final static String FOOD_ADD_URL
            = "http://cssgate.insttech.washington.edu/~_450bteam15/addCustomFood.php?";
    private String mFoodName;
    private String mFoodCalCount;


    private EditText mFoodName_Edit_Text;
    private EditText mFoodCalCount_Edit_Text;

    private FoodAddListener mListener;

    // Required empty public constructor
    public AddFoodFragment() {

    }

    /**
     * @param name The Name of the Food.
     * @param calCount The Calorie Count of the Food.
     * @return A new instance of fragment AddFoodFragment.
     */
    public static AddFoodFragment newInstance(String name, String calCount) {
        AddFoodFragment fragment = new AddFoodFragment();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(CALORIE_COUNT, calCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFoodName = getArguments().getString(NAME);
            mFoodCalCount = getArguments().getString(CALORIE_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_food, container, false);

        mFoodName_Edit_Text = (EditText) v.findViewById(R.id.food_name_edit_text);
        mFoodCalCount_Edit_Text = (EditText) v.findViewById(R.id.food_cal_count_edit_text);

        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();

        Button addCourseButton = (Button) v.findViewById(R.id.add_food_button);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildCourseURL(v);
                mListener.addFood(url);
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FoodAddListener) {
            mListener = (FoodAddListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface FoodAddListener {
        public void addFood(String url);
    }

    private String buildCourseURL(View v) {

        StringBuilder sb = new StringBuilder(FOOD_ADD_URL);

        try {

            String courseId = mFoodName_Edit_Text.getText().toString();
            sb.append("name=");
            sb.append(courseId);


            String courseShortDesc =  mFoodCalCount_Edit_Text.getText().toString();
            sb.append("&calorie_count=");
            sb.append(URLEncoder.encode(courseShortDesc, "UTF-8"));

            Log.i("AddFoodFragment", sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }
}
