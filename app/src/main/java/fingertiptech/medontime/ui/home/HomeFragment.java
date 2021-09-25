package fingertiptech.medontime.ui.home;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.FileNotFoundException;
import java.io.InputStream;

import fingertiptech.medontime.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public static final int PICK_IMAGE = 1;
    String resultQRScan;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button btnAddMed = root.findViewById(R.id.btnAddMed);
        Button btnScanQRGallery = root.findViewById(R.id.btnScanQRGallary);

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

        btnScanQRGallery.setOnClickListener(new View.OnClickListener() {
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


                    Toast.makeText(getActivity().getApplicationContext(),resultQRScan,Toast.LENGTH_LONG).show();

                    Thread.sleep(2000);

//                    Intent intent = new Intent(getActivity().getApplicationContext(),HomeActivity.class);
//                    intent.putExtra("resultQRScan",resultQRScan);
//                    startActivity(intent);

//                    getMedicationFromDB(resultQRScan);

                    //after scan qr image and forward to home


                }catch (Exception e){

                    e.printStackTrace();

                }

                //  image_view.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {

                e.printStackTrace();

//                Toast.makeText(GallaryScanQRActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }

        }else {

//            Toast.makeText(GallaryScanQRActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();

        }

    }

}