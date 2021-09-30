package fingertiptech.medontime.ui.medicine;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fingertiptech.medontime.R;
import fingertiptech.medontime.databinding.FragmentRegisterBinding;
import fingertiptech.medontime.ui.jsonplaceholder.MedicationJSONPlaceholder;
import fingertiptech.medontime.ui.model.Medication;
import fingertiptech.medontime.ui.recycleadpoter.MedicationAdaptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MedicineFragment extends Fragment {

    private MedicineViewModel medicineViewModel;
    public static final int PICK_IMAGE = 1;
    String resultQRScan;

    EditText editText_medicine_name;
    EditText editText_unit;
    Spinner unitTypeSpinner;
    EditText editText_medicine_condition;
    Spinner frequencySpinner;
    EditText editText_hoursInBetween;
    TextView textView_medicine_setAlarm;
    Button btnScanQR;
    Button btnNext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        medicineViewModel =
                new ViewModelProvider(this).get(MedicineViewModel.class);
        View root = inflater.inflate(R.layout.fragment_medicine, container, false);

        editText_medicine_name = root.findViewById(R.id.editText_medName);
        editText_unit = root.findViewById(R.id.unit);
        unitTypeSpinner = root.findViewById(R.id.unitTypeSpinner);
        editText_medicine_condition = root.findViewById(R.id.editText_condition);
        textView_medicine_setAlarm = root.findViewById(R.id.textView_displaySettingTime);
        frequencySpinner = root.findViewById(R.id.frequencySpinner);
        editText_hoursInBetween = root.findViewById(R.id.editText_hoursInBetween);
        btnNext = root.findViewById(R.id.btnNext);
        btnScanQR = root.findViewById(R.id.useQRbtn);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicineFragmentStep2 stepTwoAddMedicine = new MedicineFragmentStep2();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , stepTwoAddMedicine).commit();

            }
        });

        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    startGallery();
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                }
            }
        });

        return root;
    }
    private void startGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    public void getMedicine(String medicatinId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://medontime-api.herokuapp.com/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MedicationJSONPlaceholder medicationJSONPlaceholder = retrofit.create(MedicationJSONPlaceholder.class);
//        Call<List<Medication>> callMedication = medicationJSONPlaceholder.getMedicationList(medicatinId);
        Call<Medication> callMedication = medicationJSONPlaceholder.getMedication(medicatinId);

        callMedication.enqueue(new Callback<Medication>() {
            @Override
            public void onResponse(Call<Medication> call, Response<Medication> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Medication> medicationsList = new ArrayList<Medication>();
                Medication medicationList = response.body();
                medicationsList.add(medicationList);
                MedicationAdaptor postAdapter = new MedicationAdaptor(getActivity() , medicationsList);
//                medicationRecyclerViewItems.setAdapter(postAdapter);
            }

            @Override
            public void onFailure(Call<Medication> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
//        callMedication.enqueue(new Callback<List<Medication>>() {
//            @Override
//            public void onResponse(Call<List<Medication>> call, Response<List<Medication>> response) {
//                if (!response.isSuccessful()){
//                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                List<Medication> medicationList = response.body();
//                MedicationAdaptor postAdapter = new MedicationAdaptor(getActivity() , medicationList);
//                medicationRecyclerViewItems.setAdapter(postAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<List<Medication>> call, Throwable t) {
//                Toast.makeText(getActivity(), t.getMessage() , Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {

        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            try {

                final Uri imageUri = data.getData();

                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);

                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                try {

                    Bitmap bMap = selectedImage;

                    resultQRScan = null;

                    int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];

                    bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

                    LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);

                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                    Reader reader = new MultiFormatReader();

                    Result result = reader.decode(bitmap);

                    resultQRScan = result.getText();


                    Toast.makeText(getActivity(),resultQRScan,Toast.LENGTH_LONG).show();
                    getMedicine(resultQRScan);
                    Thread.sleep(2000);
//                    Intent intent = new Intent(getActivity().getApplicationContext(),HomeActivity.class);
//                    intent.putExtra("resultQRScan",resultQRScan);
//                    startActivity(intent);

//                    getMedicationFromDB(resultQRScan);

                }catch (Exception e){

                    e.printStackTrace();

                }

                //  image_view.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {

                e.printStackTrace();

                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();

            }

        }else {

            Toast.makeText(getActivity(), "You haven't picked Image",Toast.LENGTH_LONG).show();

        }

    }
}