package fingertiptech.medontime.ui.home;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;


import java.util.ArrayList;

import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.medicine.MedicineFragment;
import fingertiptech.medontime.ui.model.Medication;
import fingertiptech.medontime.ui.recycleadpoter.MedicationAdaptor;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public static final int PICK_IMAGE = 1;
    private RecyclerView medicationRecyclerViewItems;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button btnAddMed = root.findViewById(R.id.btnAddMed);
        // we need to write medication id into sharedpreference to store in order to show in recycle view
        // 1. we need to retrive the one have been store in sharedpreference first


        // in this homefragment will show all the patient medicaion list
        // 1 . Already got the patient id when scan qr code, so will retrive all data from api
        SharedPreferences sharedPreferencesMedicationId = getActivity().getPreferences(Context.MODE_PRIVATE);
        String patientId = sharedPreferencesMedicationId.getString("PatientId", "");
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
                MedicineFragment stepToAddMedicine = new MedicineFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , stepToAddMedicine).commit();
            }
        });



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

        return root;
    }



    }

