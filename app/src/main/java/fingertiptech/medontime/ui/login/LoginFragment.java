package fingertiptech.medontime.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.Hashing.HashingPassword;
import fingertiptech.medontime.ui.home.HomeFragment;
import fingertiptech.medontime.ui.jsonplaceholder.PatientJSONPlaceholder;
import fingertiptech.medontime.ui.model.Patient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {

    // 1. User enter email and password
    // 2. Hashing password
    // 3. Get all patient data
    // 4. Find Equal password
    private LoginViewModel loginViewModel;
    EditText editText_username;
    EditText getEditText_password;
    Button btn_signIn;
    Button btn_register;
    Button btn_forgotPass;
    CheckBox checkBox_rmbPass;

    PatientJSONPlaceholder patientJSONPlaceholder;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://medontime-api.herokuapp.com/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        patientJSONPlaceholder = retrofit.create(PatientJSONPlaceholder.class);




        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editText_username.getText().toString();
                String password = getEditText_password.getText().toString();
                boolean passRemember = checkBox_rmbPass.isChecked();
                Log.wtf("Username: ", username+"\n");
                Log.wtf("Password: ", password+"\n");

                if(passRemember){Log.wtf("Remember password?", "Yes");}

                // Hashing password
                String hashPassword = new HashingPassword().getHash(password,username);
                // Verify email and password match
                verifyPatient(username, hashPassword);
//                Call<List<Patient>> callPatient = patientJSONPlaceholder.getAllPatients();
//                ArrayList<Patient> allPatients = new ArrayList<>();
//                callPatient.enqueue(new Callback<List<Patient>>() {
//                    @Override
//                    public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
//                        if (response.isSuccessful()){
////                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getActivity(), "TEST", Toast.LENGTH_SHORT).show();
//                            allPatients.addAll(response.body());
//                            Log.wtf("test nancy ", "TESTTEST");
//
////                    if (verifyLogin(hashPassword) != null){
////                        Toast.makeText(getActivity(), verifyLogin(hashPassword).getFirstName(), Toast.LENGTH_LONG).show();
////                    }else{
////                        Toast.makeText(getActivity(), "Not found", Toast.LENGTH_LONG).show();
////                    }
//                        }
//                        List<Patient> itemList = response.body();
//                        System.out.println(itemList.toArray().toString());
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Patient>> call, Throwable t) {
//                        Toast.makeText(getActivity(), t.getMessage() , Toast.LENGTH_SHORT).show();
//                    }
//
//                });

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.wtf("Register", "Register is clicked");
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
    // sample test :
    // email:test@mail.com
    // password: admin
    private void verifyPatient(String email, String hashPassword) {
        Call<List<Patient>> callPatient = patientJSONPlaceholder.getAllPatients();
        ArrayList<Patient> allPatients = new ArrayList<>();
        callPatient.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                }
                List<Patient> patientsList = response.body();
                System.out.println(patientsList.toArray().toString());
                for(Patient patient: patientsList){
                    if(hashPassword.equals(patient.getPassword())){
                        Toast.makeText(getActivity(), "Welcome " +patient.getFirstName(), Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "Not Found", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage() , Toast.LENGTH_SHORT).show();
            }

        });
    }

//    public ArrayList<Patient> passPatients(ArrayList<Patient> patients){
//        allPatient = patients;
//        return patients;
//    }
//
//    public Patient verifyLogin(String hashPassword){
//        for(int i = 0 ; i <= getAllPatient.size(); i++){
//            if(hashPassword.equals(getAllPatient.get(i).getPassword())){
//                return getAllPatient.get(i);
//            }
//        }
//        return null;
//    }
    public void addPatient(){
        Patient patient = new Patient(0,0,"Hung1","Chen1","test1@mail.com",new HashingPassword().getHash("admin1","test1@mail.com"),"9993332222",30,null,null,null);

        Call<Patient> call = patientJSONPlaceholder.addPatient(patient);

        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Patient> itemList = new ArrayList<>();
                itemList.add(response.body());
                Toast.makeText(getActivity(),response.code() + "Response", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
    
}