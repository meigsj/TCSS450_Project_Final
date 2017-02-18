package bnz8.uw.tacoma.edu.caloriemeter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A class for the sign up fragment to retrieve user entries and
 * pass to the register method in main activity.
 */

public class RegisterFragment extends Fragment {
    private EditText mUsername;
    private EditText mPassword;
    private Button mSignupButton;
    private Button mRegBtn;

    // Required empty public constructor
    public RegisterFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        mUsername = (EditText) v.findViewById(R.id.email_signUp);
        mPassword = (EditText) v.findViewById(R.id.create_pw);
        mSignupButton = (Button) v.findViewById(R.id.sign_up_buton);

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = mPassword.getText().toString();

                ((MainActivity) getActivity()).register(mUsername.getText().toString(),password);
            }
        });

        return v;
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
        void switchToSignUp();
    }
}
