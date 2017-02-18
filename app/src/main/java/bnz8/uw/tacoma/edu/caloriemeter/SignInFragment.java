package bnz8.uw.tacoma.edu.caloriemeter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class SignInFragment extends Fragment {
    private Button mSignUpBtn;

    // Required empty public constructor
    public SignInFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_sign_in, container, false);
    final EditText userIdText = (EditText) v.findViewById(R.id.userid_edit);
    final EditText pwdText = (EditText) v.findViewById(R.id.pwd_edit);


    Button signInButton = (Button) v.findViewById(R.id.login_button);
        mSignUpBtn = (Button) v.findViewById(R.id.signUp_Button_toFragment);

        signInButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String userId = userIdText.getText().toString();
            String pwd = pwdText.getText().toString();
            ((MainActivity) getActivity()).login(userId, pwd);
        }
    });

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).switchToSignUpFragment();
            }
        });
    return v;
}
    public interface LoginInteractionListener {
        public void login(String userId, String pwd);
    }
}
