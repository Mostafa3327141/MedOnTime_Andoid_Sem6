package fingertiptech.medontime.ui.register;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fingertiptech.medontime.R;
import fingertiptech.medontime.databinding.FragmentRegisterBinding;
import fingertiptech.medontime.ui.Hashing.HashingPassword;
import fingertiptech.medontime.ui.home.HomeFragment;
import fingertiptech.medontime.ui.jsonplaceholder.MedicationJSONPlaceholder;
import fingertiptech.medontime.ui.jsonplaceholder.PatientJSONPlaceholder;
import fingertiptech.medontime.ui.medicine.MedicineFragmentStep2;
import fingertiptech.medontime.ui.model.Patient;
import fingertiptech.medontime.ui.model.TestItem;
import fingertiptech.medontime.ui.recycleadpoter.ItemAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends Fragment {
    // 1. User enter information
    // 2. Hashing password
    // check patient id and create 1 count +1
    // 3. create patient ID wirte into Patient
    // 3. Post to patient api
    private RegisterViewModel mViewModel;
    private FragmentRegisterBinding fragmentRegisterBinding;
    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }
    PatientJSONPlaceholder patientJSONPlaceholder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentRegisterBinding = FragmentRegisterBinding.inflate(getLayoutInflater());
        View view = fragmentRegisterBinding.getRoot();
//        return inflater.inflate(R.layout.fragment_register, container, false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://medontime-api.herokuapp.com/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        patientJSONPlaceholder= retrofit.create(PatientJSONPlaceholder.class);

        fragmentRegisterBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSharedPreferencepatient() != null){
                    // this is for login first time user forward to here
                    updatePassword(getSharedPreferencepatient(), fragmentRegisterBinding.textviewPasswordRegister.getText().toString());
                }else{
                    // before write patient into api we need to create patientID
                    // patient id will create from the total of patient in db and + 1
                    getAllPatient();
                }
            }
        });

        // When user login need fill this filed auto
        SharedPreferences sharedPreferencesUserLoginInfo = getActivity().getPreferences(Context.MODE_PRIVATE);
        final Gson gson = new Gson();
        Patient patientLogin =  getSharedPreferencepatient();
        if(getSharedPreferencepatient() != null){
            fragmentRegisterBinding.txtFirstName.setText(patientLogin.getFirstName());
            fragmentRegisterBinding.txtLastName.setText(patientLogin.getLastName());
            fragmentRegisterBinding.textviewAgeRegister.setText(String.valueOf(patientLogin.getAge()));
            fragmentRegisterBinding.textviewPhoneNumber.setText(patientLogin.getPhoneNum());
            fragmentRegisterBinding.textviewEmail.setText(patientLogin.getEmail());
        }
        return view;
    }

    private void updatePassword(Patient patient, String resetPassword) {
        Patient updatePatientPass = new Patient(patient.getId(), patient.getPatientID(),patient.getCaretakerID(),
                patient.getFirstName(),patient.getLastName(),patient.getEmail(),
                new HashingPassword().getHash(
                        resetPassword,
                        patient.getEmail().toLowerCase(Locale.ROOT).trim()),
                patient.getPhoneNum(),patient.getAge(), false,
                patient.getUnSelectedShapes());

        Call<Patient> updateCall = patientJSONPlaceholder.updatePatientPassword(updatePatientPass);
        updateCall.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (!response.isSuccessful()){
                    return;
                }
                HomeFragment towardHomeFragment = new HomeFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , towardHomeFragment).commit();
                Toast.makeText(getActivity(),"Update sucessfully", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(getActivity(), "Update unsucessfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllPatient() {
        // need to know how many pantients in db
        Call<List<Patient>> callPatient = patientJSONPlaceholder.getAllPatients();
        callPatient.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                }

                List<Patient> patientsList = response.body();
                // check is email is already register
                for(Patient patient: patientsList){
                    if(fragmentRegisterBinding.textviewEmail.getText().toString().equals(patient.getEmail())){
                        Toast.makeText(getActivity(), fragmentRegisterBinding.textviewEmail.getText().toString() + " already registered", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                Log.e("Total patient in db", String.valueOf(patientsList.size()));
                addPatient(patientsList.size());
            }
            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage() , Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void addPatient(int totalPatient) {
        // add patient to db
        Patient patient = new Patient(
                totalPatient+1,
                0,
                fragmentRegisterBinding.txtFirstName.getText().toString(),
                fragmentRegisterBinding.txtLastName.getText().toString(),
                fragmentRegisterBinding.textviewEmail.getText().toString().toLowerCase(Locale.ROOT).trim()
                ,new HashingPassword().getHash(
                        fragmentRegisterBinding.textviewPasswordRegister.getText().toString(),
                fragmentRegisterBinding.textviewEmail.getText().toString().toLowerCase(Locale.ROOT).trim()),
                fragmentRegisterBinding.textviewPhoneNumber.getText().toString(),
                Integer.valueOf(fragmentRegisterBinding.textviewAgeRegister.getText().toString()),
                false,
                null);

        Call<Patient> call = patientJSONPlaceholder.addPatient(patient);

        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Patient> patientsList = new ArrayList<>();
                patientsList.add(response.body());
                Toast.makeText(getActivity(),"Register sucessefully", Toast.LENGTH_LONG).show();
                HomeFragment forwardToHomePage = new HomeFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , forwardToHomePage).commit();
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Patient getSharedPreferencepatient(){
        // When user login need fill this filed auto
        SharedPreferences sharedPreferencesUserLoginInfo = getActivity().getPreferences(Context.MODE_PRIVATE);
        final Gson gson = new Gson();
        Patient patientLogin =  gson.fromJson(sharedPreferencesUserLoginInfo.getString("PatientLogInInfo", ""), Patient.class);
        return patientLogin;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        // TODO: Use the ViewModel
    }

}