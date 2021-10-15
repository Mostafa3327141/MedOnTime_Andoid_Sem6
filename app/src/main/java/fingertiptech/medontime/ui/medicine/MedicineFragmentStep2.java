package fingertiptech.medontime.ui.medicine;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import fingertiptech.medontime.R;

public class MedicineFragmentStep2 extends Fragment {

//    private MedicineFragmentStep2ViewModel mViewModel;

    Button btnNext2;
    Button btnCamera;
    ImageView imageViewMedication;
    private MedicineViewModel medicineViewModel;
    private static final int Image_Capture_Code = 1;


    public static MedicineFragmentStep2 newInstance() {
        return new MedicineFragmentStep2();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_medicine_step2_camera_image, container, false);

        btnNext2 = root.findViewById(R.id.btnNext2);
        btnCamera = root.findViewById(R.id.btnOpenCamera);
        imageViewMedication = root.findViewById(R.id.imageMedicationView);

        medicineViewModel =
                new ViewModelProvider(this).get(MedicineViewModel.class);
        if(MedicineFragment.resultQRScan != null){
            medicineViewModel.initGetMedicationByMedicationId(MedicineFragment.resultQRScan);
            medicineViewModel.getMedicationRepository().observe(getViewLifecycleOwner(), medicationsResponse -> {
                imageViewMedication.setImageBitmap(convertBase64ToBitmap(medicationsResponse.getMedicationImage()));
//                // we need to write medication into sharedpreference to store in order to show in recycle view
//                // 1. we need to retrive the one have been store in sharedpreference first
//                SharedPreferences sharedPreferencesMedicationId = getActivity().getPreferences(Context.MODE_PRIVATE);
//                final Gson gson = new Gson();
//                String[] medicationIdList = sharedPreferencesMedicationId.getString("MedicationStored", "").split(",");
//                Log.wtf("Nancy test sharedPreferencesMedicationId.getString 01" , sharedPreferencesMedicationId.getString("MedicationStored", ""));
//                ArrayList<String> medicationArrayList = new ArrayList<>(Arrays.asList(medicationIdList));
//                ArrayList<Medication> covertStringtoMedicationList = new ArrayList<>();
//                // need to read into medicaion model json format, and save as arraylist<medication>
//                for (String medictionList: medicationArrayList) {
//                    if(!"".equals(medictionList)){
//                        covertStringtoMedicationList.add(gson.fromJson(medictionList, Medication.class));
//                    }
//                }
//                Log.wtf("Nancy test covertStringtoMedicationList.size", String.valueOf(covertStringtoMedicationList.size()));
//                // 2. after we retrive then we need to add new medicaion we need to store the medicaon we just scan
//                // check if already in the list if not append, otherwise skip
//                SharedPreferences.Editor sharedPreferencesMedicationIdEditor = getActivity().getPreferences(Context.MODE_PRIVATE).edit();
//                StringBuilder sb = new StringBuilder();
//                for (int i = 0; i < covertStringtoMedicationList.size(); i++) {
//                    if(MedicineFragment.resultQRScan.equals(covertStringtoMedicationList.get(i).getId())){
//                        HomeFragment forwardHomefragment = new HomeFragment();
//                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , forwardHomefragment).commit();
//                        Toast.makeText(getActivity(), "Already scan this QR code", Toast.LENGTH_LONG).show();
//                        return;
//                    }
//                }
//                covertStringtoMedicationList.add(medicationsResponse);
//                String covertStringtoMedicationListToString = gson.toJson(covertStringtoMedicationList);
//
//                sharedPreferencesMedicationIdEditor.putString("MedicationStored", covertStringtoMedicationListToString);
//                sharedPreferencesMedicationIdEditor.apply();
//                Log.wtf("Nancy test sharedPreferencesMedicationId.getString 02" , sharedPreferencesMedicationId.getString("MedicationStored", ""));

            });
        }


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt,Image_Capture_Code);
            }
        });


        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicineFragmentStep3 stepThreeAddMedicine = new MedicineFragmentStep3();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , stepThreeAddMedicine).commit();

            }
        });

        return root;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                imageViewMedication.setImageBitmap(bp);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }
    //this is covert base 64 store from web app to image to app imageview
    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(MedicineFragmentStep2ViewModel.class);
//        // TODO: Use the ViewModel
//    }

}