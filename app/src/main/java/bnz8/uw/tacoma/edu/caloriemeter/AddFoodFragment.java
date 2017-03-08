package bnz8.uw.tacoma.edu.caloriemeter;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    public static final String SUGER_AMOUNT = "suger_amount";
    public static final String PROTIEN_AMOUNT = "protien_amount";
    public static final String SODIUM_AMOUNT = "sodium_amount";
    public static final String USER_EMAIL = "user_email";

    private final static String FOOD_ADD_URL
            = "http://cssgate.insttech.washington.edu/~meigsj/addCustomFood.php?";
    private String mFoodName;
    private String mFoodCalCount;
    private String mSugerAmount;
    private String mProtienAmount;
    private String mSodiumAmount;
    private String mEmail;

    private EditText mFoodName_Edit_Text;
    private EditText mFoodCalCount_Edit_Text;
    private EditText mFoodSuger_Edit_Text;
    private EditText mFoodProtien_Edit_Text;
    private EditText mFoodSodium_Edit_Text;

    private static final String PUBLIC_EMAIL = "admin@uw.edu";
    private FoodAddListener mListener;

    // Required empty public constructor
    public AddFoodFragment() {

    }

    /**
     * @param name The Name of the Food.
     * @param calCount The Calorie Count of the Food.
     * @return A new instance of fragment AddFoodFragment.
     */
    public static AddFoodFragment newInstance(String name, String calCount,
                                              String sugerAmount, String protienAmount,
                                              String SodiumAmount, String email) {
        AddFoodFragment fragment = new AddFoodFragment();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(CALORIE_COUNT, calCount);
        args.putString(SUGER_AMOUNT, sugerAmount);
        args.putString(PROTIEN_AMOUNT, protienAmount);
        args.putString(SODIUM_AMOUNT, SodiumAmount);
        args.putString(USER_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFoodName = getArguments().getString(NAME);
            mFoodCalCount = getArguments().getString(CALORIE_COUNT);
            mSugerAmount = getArguments().getString(SUGER_AMOUNT);
            mProtienAmount = getArguments().getString(PROTIEN_AMOUNT);
            mSodiumAmount = getArguments().getString(SODIUM_AMOUNT);
            mEmail = getArguments().getString(USER_EMAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View theView = inflater.inflate(R.layout.fragment_add_food, container, false);

        mFoodName_Edit_Text = (EditText) theView.findViewById(R.id.food_name_edit_text);
        mFoodCalCount_Edit_Text = (EditText) theView.findViewById(R.id.food_cal_count_edit_text);
        mFoodSuger_Edit_Text = (EditText) theView.findViewById(R.id.food_suger_edit_text);
        mFoodProtien_Edit_Text = (EditText) theView.findViewById(R.id.food_protein_edit_text);
        mFoodSodium_Edit_Text = (EditText) theView.findViewById(R.id.food_sodium_edit_text);

        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();

        Button addCourseButton = (Button) theView.findViewById(R.id.add_food_button);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildFoodURL(v,theView);


                mListener.addFood(url);

            }
        });

        return theView;
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
        void addFood(String url);
    }

    private String buildFoodURL(View v, View fragmentView) {

        StringBuilder sb = new StringBuilder(FOOD_ADD_URL);

        try {

            String foodName = mFoodName_Edit_Text.getText().toString();
            sb.append("name=");
            sb.append(foodName);

            String calCount =  mFoodCalCount_Edit_Text.getText().toString();
            sb.append("&calorie_count=");
            sb.append(URLEncoder.encode(calCount, "UTF-8"));

            String foodSugar = mFoodSuger_Edit_Text.getText().toString();
            sb.append("&suger_amount=");
            sb.append(URLEncoder.encode(foodSugar, "UTF-8"));

            String foodProtien =  mFoodProtien_Edit_Text.getText().toString();
            sb.append("&protien_amount=");
            sb.append(URLEncoder.encode(foodProtien, "UTF-8"));

            String foodSodium = mFoodSodium_Edit_Text.getText().toString();
            sb.append("&sodium_amount=");
            sb.append(URLEncoder.encode(foodSodium, "UTF-8"));
            CheckBox privateOrPublic = (CheckBox) fragmentView.findViewById(R.id.private_or_public_checkbox);
            sb.append("&user_email=");
            if(!privateOrPublic.isChecked()) {
                sb.append(PUBLIC_EMAIL);

            } else {
                sb.append(mEmail);
            }
            Log.d("AddFoodFragment", sb.toString());




        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }
}
