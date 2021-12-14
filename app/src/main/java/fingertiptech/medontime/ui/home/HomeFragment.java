package fingertiptech.medontime.ui.home;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.jsonplaceholder.LogJSONPlaceholder;
import fingertiptech.medontime.ui.login.LoginFragment;
import fingertiptech.medontime.ui.model.Medication;
import fingertiptech.medontime.ui.recycleadpoter.MedicationAdaptor;

/**
 * This java file is associated to Home fragment
 * This class uses ViewModel and live data for ease of maintaining
 * Home fragment prompts the user to log in if they haven't logged in already
 * It also shows the list of medications associated with the current user
 */

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public static final int PICK_IMAGE = 1;
    private RecyclerView medicationRecyclerViewItems;

    LogJSONPlaceholder logJSONPlaceholder;


    /**
     * HomeFragment is the first page when user launch application will show.
     * If users login to their account, home fragment will show, user name and
     * all the medication list in recycle view.
     * User can click recycle view and forward to detailed fragment to see more information
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button btnAddMed = root.findViewById(R.id.btnAddMed);

        // If user havn't login will show need to login message if already login will show the name
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

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // in this homefragment will show all the patient medication list
                // 1 . Already got the patient id when scan qr code, so will retrieve all data from api
                ArrayList<Medication> patientAllMedication = new ArrayList<>();
                // 2. get medication list match the patient id
                // 3. store in arraylist <medication> and populate to recycle view
                medicationRecyclerViewItems =root.findViewById(R.id.recycleView_medicine);
                medicationRecyclerViewItems.setHasFixedSize(true);
                medicationRecyclerViewItems.setLayoutManager(new LinearLayoutManager(getContext()));
                // In here will get all medication from our database and we need to filter out the medication id
                // match to our user medication id
                if(!"".equals(patientId)){
                    homeViewModel.initGetMedication();
                    homeViewModel.getMedicationListRepository().observe(getViewLifecycleOwner(), medicationsListResponse -> {
                        for (Medication medication: medicationsListResponse) {
                            if(patientId.equals(String.valueOf(medication.getPatientID()))){
                                patientAllMedication.add(medication);
                            }
                        }
                        // just filiter one same as patient id in sharedPreference
                        ArrayList<Medication> medicationList = patientAllMedication;
                        MedicationAdaptor medicationAdaptor = new MedicationAdaptor(getActivity() , medicationList);
                        medicationRecyclerViewItems.setAdapter(medicationAdaptor);
                    });
                }


            }
        }, 2000);




        btnAddMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginFragment loginFrag = new LoginFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , loginFrag).commit();
            }
        });



        return root;
    }



}

