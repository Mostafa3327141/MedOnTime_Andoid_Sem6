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
        View root = inflater.inflate(R.layout.fragment_medicine_step2, container, false);

        btnNext2 = root.findViewById(R.id.btnNext2);
        btnCamera = root.findViewById(R.id.btnOpenCamera);
        imageViewMedication = root.findViewById(R.id.imageMedicationView);

        medicineViewModel =
                new ViewModelProvider(this).get(MedicineViewModel.class);
        String s = MedicineFragment.resultQRScan;
        if(MedicineFragment.resultQRScan != null){
            medicineViewModel.init(MedicineFragment.resultQRScan);
            medicineViewModel.getMedicationRepository().observe(getViewLifecycleOwner(), medicationsResponse -> {
                imageViewMedication.setImageBitmap(convertBase64ToBitmap(medicationsResponse.getMedicationImage()));
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