package fingertiptech.medontime.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.register.RegisterFragment;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    EditText editText_username;
    EditText getEditText_password;
    Button btn_signIn;
    Button btn_register;
    Button btn_forgotPass;
    CheckBox checkBox_rmbPass;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        editText_username = root.findViewById(R.id.editText_userName);
        getEditText_password = root.findViewById(R.id.editText_Password);
        btn_signIn = root.findViewById(R.id.btnSignIn);
        btn_register = root.findViewById(R.id.btnRegister);
        btn_forgotPass = root.findViewById(R.id.btnForgotPassword);
        checkBox_rmbPass = root.findViewById(R.id.chkRememberPassword);


        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editText_username.getText().toString();
                String password = getEditText_password.getText().toString();
                boolean passRemember = checkBox_rmbPass.isChecked();
                Log.wtf("Username: ", username+"\n");
                Log.wtf("Password: ", password+"\n");
                if(passRemember){Log.wtf("Remember password?", "Yes");}
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment = new RegisterFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , registerFragment).commit();
            }
        });

        btn_forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.wtf("Forgot password!", "Forgot password is clicked");
            }
        });

        return root;
    }
}