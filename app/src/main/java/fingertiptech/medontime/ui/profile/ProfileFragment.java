package fingertiptech.medontime.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.profile.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    EditText editText_username;
    EditText getEditText_password;
    Button btn_signIn;
    Button btn_register;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        editText_username = root.findViewById(R.id.editText_userName);
        getEditText_password = root.findViewById(R.id.editText_Password);
        btn_signIn = root.findViewById(R.id.btnSignIn);
        btn_register = root.findViewById(R.id.btnRegister);

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editText_username.getText().toString();
                String password = getEditText_password.getText().toString();
                Log.wtf("Username: ", username+"\n");
                Log.wtf("Password: ", password);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.wtf("Register", "Register is clicked");
            }
        });

        return root;
    }
}