package fingertiptech.medontime.ui.home;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;



import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.jsonplaceholder.LogJSONPlaceholder;

import java.util.ArrayList;

import fingertiptech.medontime.ui.medicationDetail.MedicationDetailedFragmentArgs;
import fingertiptech.medontime.ui.medicine.MedicineFragment;
import fingertiptech.medontime.ui.model.Medication;
import fingertiptech.medontime.ui.recycleadpoter.MedicationAdaptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import fingertiptech.medontime.ui.model.Log;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public static final int PICK_IMAGE = 1;
    private RecyclerView medicationRecyclerViewItems;

    LogJSONPlaceholder logJSONPlaceholder;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button btnAddMed = root.findViewById(R.id.btnAddMed);

        // If user havn't login will show need to login message if arleady login will show the name
        SharedPreferences sharedPreferencesMedicationId = getActivity().getPreferences(Context.MODE_PRIVATE);
        String patientId = sharedPreferencesMedicationId.getString("PatientId", "");
        SharedPreferences sharedPreferencesLoginUserPatientObjectId = getActivity().getPreferences(Context.MODE_PRIVATE);
        String patientObjectId = sharedPreferencesLoginUserPatientObjectId.getString("PatientObjectId", "");
        if ("".equals(patientId)){
            btnAddMed.setText("Please login first to use MedOnTime");
        }else{
            homeViewModel.initGetPatient(patientObjectId);
            homeViewModel.getPatientMutableLiveDataRepository().observe(getViewLifecycleOwner(), patientResponse -> {
                btnAddMed.setText("Welcome "+patientResponse.getFirstName());
            });

        }
        // we need to write medication id into sharedpreference to store in order to show in recycle view
        // 1. we need to retrive the one have been store in sharedpreference first




        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // in this homefragment will show all the patient medicaion list
                // 1 . Already got the patient id when scan qr code, so will retrive all data from api

                ArrayList<Medication> patientAllMedication = new ArrayList<>();
//        ArrayList<String> medicationIdArrayList = new ArrayList<>(Arrays.asList(medicationIdList));
                // 2. get medication list match the patient id
                // 3. store in arraylist <medication> and populate to recycle view
                medicationRecyclerViewItems =root.findViewById(R.id.recycleView_medicine);
                medicationRecyclerViewItems.setHasFixedSize(true);
                medicationRecyclerViewItems.setLayoutManager(new LinearLayoutManager(getContext()));
                if(!"".equals(patientId)){
                    homeViewModel.initGetMedication();
                    homeViewModel.getMedicationListRepository().observe(getViewLifecycleOwner(), medicationsListResponse -> {
                        for (Medication medication: medicationsListResponse) {
                            if(patientId.equals(String.valueOf(medication.getPatientID()))){
                                patientAllMedication.add(medication);
                            }
                        }
                        // just filiter one same as patient id in sharedpreferce
                        ArrayList<Medication> medicationList = patientAllMedication;
                        MedicationAdaptor medicationAdaptor = new MedicationAdaptor(getActivity() , medicationList);
                        medicationRecyclerViewItems.setAdapter(medicationAdaptor);
                    });
                }

                medicationRecyclerViewItems.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a = "";
                        HomeFragmentDirections.ActionHomeRecycleToMedicationDetailFragment action =
                                HomeFragmentDirections.actionHomeRecycleToMedicationDetailFragment();
                        action.setMedicationObjectId("0");
                        Navigation.findNavController(v).navigate(action);
                    }
                });
            }
        }, 2000);




        btnAddMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //https://stackoverflow.com/questions/5658675/replacing-a-fragment-with-another-fragment-inside-activity-group
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.setReorderingAllowed(true);
//                // Replace whatever is in the fragment_container view with this fragment
//                transaction.replace(R.id.fragment_container, ExampleFragment.class, null);
//                // Commit the transaction
//                transaction.commit();

                Snackbar.make(view, "Add Medicine", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //--------------------Test Log API-----------------
//
        Button btnLogTest = root.findViewById(R.id.btnLogTest);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://medontime-api.herokuapp.com/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        logJSONPlaceholder= retrofit.create(LogJSONPlaceholder.class);

        btnLogTest.setOnClickListener(v -> {
            logTest();
        });

        return root;
    }

    private void logTest() {
        android.util.Log.i("test", "logTest()");
        Call<Log> createCall = logJSONPlaceholder.addLog(new Log(null, "test", "some med id", "some med name"));
        createCall.enqueue(new Callback<Log>() {
            @Override
            public void onResponse(Call<Log> call, Response<Log> response) {
                if (!response.isSuccessful()){
                    android.util.Log.i("testLog", "logTest() unsuccess" + response.code());
                    return;
                }
                android.util.Log.i("testLog", "logTest() success" + response.code());
                Toast.makeText(getActivity(),"Log added", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Log> call, Throwable t) {
                Toast.makeText(getActivity(), "Log not added", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

