package fingertiptech.medontime.ui.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.Hashing.HashingPassword;
import fingertiptech.medontime.ui.home.HomeFragment;
import fingertiptech.medontime.ui.jsonplaceholder.PatientJSONPlaceholder;
import fingertiptech.medontime.ui.medicine.MedicineFragmentStep2;
import fingertiptech.medontime.ui.model.Patient;
import fingertiptech.medontime.ui.register.RegisterFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {

    /**
     *      User without caretaker isTemporyPassrword(false)
     *      1. User enter email and password
     *      2. Hashing password
     *      3. Get all patient data
     *      4. Find Equal password
     *      User with caretaker -> isTemporyPassrod(true) first time
     *      1. when enter temp password will pop up window
     *      2. in Pop up window will have filed to enter their age and phone
     *      3. If correct will forward to reset password and update
     */

    private LoginViewModel loginViewModel;
    EditText editText_username;
    EditText getEditText_password;
    Button btn_signIn;
    Button btn_register;
    Button btn_forgotPass;
    CheckBox checkBox_rmbPass;

    PatientJSONPlaceholder patientJSONPlaceholder;

    //For popup window
    private AlertDialog.Builder verifyDialogBuilder;
    private AlertDialog verifyDialog;
    private EditText patienFirstName, patientLastName, patientAge, patienPhoneNo;
    private Button btnVerify, btnNotMe;

    /**
     *  Here is LoginFragment
     *  User will enter their email and password in the text filed
     *  In here we call the HashingPassword.getHash function
     *  to get the encrypted password first.
     *
     *  After we have email and encrypted password, we verify a patient's email and password
     *  by calling verifyPatient() function is matching to our database or not.
     */

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        editText_username = root.findViewById(R.id.editText_userName);
        getEditText_password = root.findViewById(R.id.editText_Password);
        btn_signIn = root.findViewById(R.id.btnSignIn);
        btn_register = root.findViewById(R.id.btnRegister);
//        btn_forgotPass = root.findViewById(R.id.btnForgotPassword);
//        checkBox_rmbPass = root.findViewById(R.id.chkRememberPassword);

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
//                boolean passRemember = checkBox_rmbPass.isChecked();
                Log.wtf("Username: ", username+"\n");
                Log.wtf("Password: ", password+"\n");

                // Hashing password
                String hashPassword = new HashingPassword().getHash(password,username);
                // Verify email and password match
                verifyPatient(username, hashPassword);

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment = new RegisterFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , registerFragment).commit();
            }
        });

//        btn_forgotPass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.wtf("Forgot password!", "Forgot password is clicked");
//            }
//        });

        return root;
    }
    // sample test :
    // email:test@mail.com
    // password: admin

    /**
     * When user enter their email and password in first place then click login button,
     * this function will be called.
     * We use Retrofit to call API, first it will
     * First, it will verify weather is first time login or not.
     * If it is first time will call creatNewContactDiaglog() function
     * otherwise, it will @GET patient's from API, if match will forward to HomeFragment
     * @param email
     * @param hashPassword
     */
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
                for(Patient patient: patientsList){
                    if(hashPassword.equals(patient.getPassword())){
                        // if get temp passwaord is false mean is already update new password
                        // or it is created by patient without caretaker
                        if(!patient.getPasswordTemporary()){
                            SharedPreferences sharedPreferencesLoginUserInformation = getActivity().getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor sharedPreferencesLoginUserInformationEditor = sharedPreferencesLoginUserInformation.edit();
                            sharedPreferencesLoginUserInformationEditor.putString("PatientId", patient.getPatientID().toString());
                            sharedPreferencesLoginUserInformationEditor.apply();
                            SharedPreferences sharedPreferencesLoginUserPatientObjectId = getActivity().getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor sharedPreferencesLoginUserPatientObjectIdEditor = sharedPreferencesLoginUserPatientObjectId.edit();
                            sharedPreferencesLoginUserPatientObjectIdEditor.putString("PatientObjectId", patient.getId());
                            sharedPreferencesLoginUserPatientObjectIdEditor.apply();
                            Toast.makeText(getActivity(), "Welcome " +patient.getFirstName(), Toast.LENGTH_LONG).show();
                            HomeFragment forwardToHomePage = new HomeFragment();
                            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , forwardToHomePage).commit();

                            return;
                        }else{
                            creatNewContactDiaglog(
                                    patient);
                            return;
                        }

                    }
                }
                Toast.makeText(getActivity(), "Not Found ", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage() , Toast.LENGTH_SHORT).show();
            }

        });
    }


    /**
     * This is pop up dialog, when user login with their email and password first time
     * which is given by their caretaker, then it will pop up dialog for patient
     * to reset their own password. (We set boolean true if first time login)
     *
     * In dialog will have verify check filed for user to entering their 'age' and 'phone no'
     * to ensure they are the right user can update their password.
     *
     * User enter their age and phone no., then click verify button.
     * If the information match will forward to update password page to enter their new password,
     * otherwise will show error message.
     * @param patient
     */
    public void creatNewContactDiaglog(Patient patient){
        verifyDialogBuilder = new AlertDialog.Builder(getContext());

        final View verifyPopupView = getLayoutInflater().inflate(R.layout.verify_popup,null);
        patienFirstName = verifyPopupView.findViewById(R.id.tvpatientFirstName);
        patienFirstName.setText(patient.getFirstName());
        patientLastName = verifyPopupView.findViewById(R.id.patientLastName);
        patientLastName.setText(patient.getLastName());
        patientAge = verifyPopupView.findViewById(R.id.patientAge);
        patienPhoneNo = verifyPopupView.findViewById(R.id.patientNumberNo);
        btnVerify = verifyPopupView.findViewById(R.id.btnVerifyMe);
        btnNotMe = verifyPopupView.findViewById(R.id.btnNotMe);

        verifyDialogBuilder.setView(verifyPopupView);
        verifyDialog = verifyDialogBuilder.create();
        verifyDialog.show();

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // will veriy user input age and phone number is same as in db
                if(Integer.valueOf(patientAge.getText().toString().trim()) == patient.getAge()
                && patient.getPhoneNum().equals(patienPhoneNo.getText().toString())){
                    SharedPreferences sharedPreferencesLoginUserInformation = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor sharedPreferencesLoginUserInformationEditor = sharedPreferencesLoginUserInformation.edit();
                    Gson gson = new Gson();
                    String patientLoginInfo = gson.toJson(patient);
                    sharedPreferencesLoginUserInformationEditor.putString("PatientLogInInfo", patientLoginInfo);
                    sharedPreferencesLoginUserInformationEditor.apply();
                    Toast.makeText(getActivity(), "Verify Successfully, Now Reset Password", Toast.LENGTH_LONG).show();
                    RegisterFragment registerFragment = new RegisterFragment();
                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , registerFragment).commit();
                    verifyDialog.dismiss();
                }else{
                    Toast.makeText(getActivity(), "Verify Unsuccessfully, Please Enter Again", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnNotMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyDialog.dismiss();
            }
        });
    }

}