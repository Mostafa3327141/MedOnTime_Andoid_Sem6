package fingertiptech.medontime.ui.medicine;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.model.Medication;
import fingertiptech.medontime.ui.model.Patient;

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
//        SharedPreferences sharedPreferencesMedicationId = getActivity().getPreferences(Context.MODE_PRIVATE);
//        String medicationId = sharedPreferencesMedicationId.getString("addMedicationToDBGenerateIdRetrive", "");

        medicineViewModel =
                new ViewModelProvider(this).get(MedicineViewModel.class);
        if(MedicineFragment.resultQRScan != null){
            medicineViewModel.initGetMedicationByMedicationId(MedicineFragment.resultQRScan);
            medicineViewModel.getMedicationRepositoryWhenGet().observe(getViewLifecycleOwner(), medicationsResponse -> {
                // in here has 2 senerio one is patient already medicaion so it will has image bitmap already so i just display
                // another one is patient need to add their own, so they add basic in frag1 in frag2 is retrive medcaion object create by fragment1
                // and need to add image in fragment 2, of course when retrive theri own medecion id won't have image bitmap at first
                if(null != medicationsResponse.getMedicationImage()){
                    imageViewMedication.setImageBitmap(convertBase64ToBitmap(medicationsResponse.getMedicationImage()));
                }
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
//                MedicineFragment.resultQRScan = null;
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
                SharedPreferences sharedPreferencesAddMedication = getActivity().getPreferences(Context.MODE_PRIVATE);
                final Gson gson = new Gson();
                Medication medicationAdd =  gson.fromJson(sharedPreferencesAddMedication.getString("MedicationAdd", ""), Medication.class);
                medicationAdd.setMedicationImage(BitMapToString(bp));
                medicineViewModel.initUpdateMedication(medicationAdd);
//                medicineViewModel.initGetMedicationByMedicationId(MedicineFragment.resultQRScan);
//                medicineViewModel.getMedicationRepositoryWhenGet().observe(getViewLifecycleOwner(), medicationsResponse -> {
//                    // in here has 2 senerio one is patient already medicaion so it will has image bitmap already so i just display
//                    // another one is patient need to add their own, so they add basic in frag1 in frag2 is retrive medcaion object create by fragment1
//                    // and need to add image in fragment 2, of course when retrive theri own medecion id won't have image bitmap at first
//                    Medication medication = medicationsResponse;
//                    medication.setMedicationImage(BitMapToString(bp));
//                    medicineViewModel.initUpdateMedication(medication);
//                    medicineViewModel.getMedicationRepositoryWhenUpdate().observe(getViewLifecycleOwner(), medicationsResponse1 -> {
//
//                    });
//
//                });
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
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(MedicineFragmentStep2ViewModel.class);
//        // TODO: Use the ViewModel
//    }

}