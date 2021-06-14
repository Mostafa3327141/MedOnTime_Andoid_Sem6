package fingertiptech.medontime.ui.scanQRcode;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQRCodeFragment extends Fragment implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());
        return mScannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(getActivity(), "Contents = " + rawResult.getText() +
                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ScanQRCodeFragment.this);
            }
        }, 2000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
//    private ScanQRCodeViewModel scanQRCodeViewModel;
//
//    private static final int REQUEST_CAMERA = 1;
//    private ZXingScannerView scannerView;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        scanQRCodeViewModel =
//                new ViewModelProvider(this).get(ScanQRCodeViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_scan_qr_code, container, false);
//        final TextView textView = root.findViewById(R.id.text_scanqrcode);
//        scanQRCodeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
//    }
//    private boolean checkPermission(){
//        return (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
//    }
//    private void requestPermission()
//    {
//        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
//    }
//    public void onRequestPermissionsResult(int requestCode, String permission[], int grantResults[])
//    {
//        switch (requestCode)
//        {
//            case REQUEST_CAMERA :
//                if(grantResults.length > 0)
//                {
//                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    if(cameraAccepted)
//                    {
//                        Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();
//                    }
//                    else
//                    {
//                        Toast.makeText(getActivity(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
//                        if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
//                            displayAlertMessage("You need to allow access to both the permissions",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                requestPermissions(new String[]{Manifest.permission.CAMERA},
//                                                        REQUEST_CAMERA);
//                                            }                                        }
//                                    });
//                            return;
//                        }
//                    }
//                }
//                break;
//        }
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        scannerView.stopCamera();
//    }
//    private void displayAlertMessage(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(getContext())
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
//        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
//            if (checkPermission()) {
//                if(scannerView == null) {
//                    scannerView = new ZXingScannerView(getContext());
//                    onCreateView(scannerView);
//                }
//                scannerView.setResultHandler(this);
//                scannerView.startCamera();
//            } else {
//                requestPermission();
//            }
//        }
//    }
//    @Override
//    public void handleResult(Result rawResult) {
//
//        final String myResult = rawResult.getText();
//        Log.d("QRCodeScanner", rawResult.getText());
//        Log.d("QRCodeScanner", rawResult.getBarcodeFormat().toString());
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Scan Result");
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                scannerView.resumeCameraPreview(CameraScanQRActivity.this);
//            }
//        });
//        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myResult));
//                startActivity(browserIntent);
//            }
//        });
//        builder.setMessage(rawResult.getText());
//        AlertDialog alert1 = builder.create();
//        alert1.show();
//    }
}