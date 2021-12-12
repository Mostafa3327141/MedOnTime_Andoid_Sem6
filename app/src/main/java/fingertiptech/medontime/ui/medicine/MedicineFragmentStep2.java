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
import fingertiptech.medontime.ui.imageConvert.ImageBase64Convert;
import fingertiptech.medontime.ui.model.Medication;
import fingertiptech.medontime.ui.model.Patient;

public class MedicineFragmentStep2 extends Fragment {


    Button btnNext2;
    Button btnCamera;
    ImageView imageViewMedication;
    private MedicineViewModel medicineViewModel;
    private static final int Image_Capture_Code = 1;


    public static MedicineFragmentStep2 newInstance() {
        return new MedicineFragmentStep2();
    }

    /**
     * If patient scan QR code will forward to MedicineFragment then image will show when forward to fragment 2
     * Otherwise user will open camera take pill image.
     * Here is step 2, in step 1 we saved the medication information to our sharedPreference first,
     * for MedicineFragmentStep2 user open camera to take pill picture.
     * Image bitmap will convert base64 string to store to database.
     */
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
            medicineViewModel.getMedicationRepositoryWhenGet().observe(getViewLifecycleOwner(), medicationsResponse -> {
                // in here has 2 senerio one is patient already medicaion so it will has image bitmap already so i just display
                // another one is patient need to add their own, so they add basic in frag1 in frag2 is retrive medcaion object create by fragment1
                // and need to add image in fragment 2, of course when retrive theri own medecion id won't have image bitmap at first
                if(null != medicationsResponse.getMedicationImage()){
                    imageViewMedication.setImageBitmap(ImageBase64Convert.convertBase64ToBitmap(medicationsResponse.getMedicationImage()));
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
            }
        });

        return root;
    }

    /**
     *  In this is open camera take picture. When user take picture, it will convert to code base 64
     *  and store to database.
     */
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
                medicationAdd.setMedicationImage(ImageBase64Convert.BitMapToString(bp));
                medicineViewModel.initUpdateMedication(medicationAdd);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }



}