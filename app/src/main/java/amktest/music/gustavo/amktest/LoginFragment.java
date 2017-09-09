package amktest.music.gustavo.amktest;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.regex.Pattern;

import amktest.music.gustavo.amktest.Models.Constants;
import amktest.music.gustavo.amktest.Models.SharedPreferences;
import amktest.music.gustavo.amktest.Utilities.Communication;

/**
 * Created by gustavoalvarez on 08/09/17.
 */

public class LoginFragment extends Fragment implements View.OnClickListener{

    private TextInputLayout tilLoginUser;
    private TextInputLayout tilLoginPassword;
    private TextInputEditText tvLoginUser;
    private TextInputEditText tvLoginPassword;
    private Button btnLogin;
    private ProgressBar pbLogin;

    Communication mCallback;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (Communication) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement Communication");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getActivity()).updateToolbarTitle("Login");

        tilLoginUser = view.findViewById(R.id.til_login_user);
        tilLoginPassword = view.findViewById(R.id.til_login_password);
        tvLoginUser = view.findViewById(R.id.tv_login_user);
        tvLoginPassword = view.findViewById(R.id.tv_login_password);
        btnLogin = view.findViewById(R.id.btn_login);
        pbLogin = view.findViewById(R.id.pb_login);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onClick(View view) {
        if (view.getId() == btnLogin.getId()) {
            if (validations()) {
                pbLogin.setVisibility(View.VISIBLE);

                int LOGIN_DURATION = 3000;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pbLogin.setVisibility(View.GONE);
                        SharedPreferences sharedPreferences = new SharedPreferences(getContext());
                        sharedPreferences.setValueString(SharedPreferences.ACCESS_TOKEN, tvLoginUser.getText().toString());
                        mCallback.onResponse(Constants.LOGIN_SUCCESS, null);
                    }
                }, LOGIN_DURATION);

            }
        }
    }

    private boolean validations() {
        boolean isValid = true;
        Pattern patron = Pattern.compile("^(?=.*[A-Z])[A-Za-z\\d]{8,}$");
        tilLoginUser.setError(null);
        tilLoginPassword.setError(null);

        if (tvLoginUser.getText().toString().isEmpty()) {
            tilLoginUser.setError(getString(R.string.error_user_empty));
            isValid = false;

        } else if (!patron.matcher(tvLoginUser.getText().toString()).matches()) {
            tilLoginUser.setError(getString(R.string.error_validations));
            isValid = false;

        }
        if (tvLoginPassword.getText().toString().isEmpty()) {
            tilLoginPassword.setError(getString(R.string.error_password_empty));
            isValid = false;
        } else if (!patron.matcher(tvLoginPassword.getText().toString()).matches()) {
            tilLoginPassword.setError(getString(R.string.error_validations));
            isValid = false;

        }

        return isValid;
    }

}
