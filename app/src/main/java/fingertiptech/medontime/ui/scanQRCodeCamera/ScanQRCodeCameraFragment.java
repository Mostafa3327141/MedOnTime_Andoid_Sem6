package fingertiptech.medontime.ui.scanQRCodeCamera;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;

import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.medicine.MedicineFragment;
import fingertiptech.medontime.ui.medicine.MedicineFragmentStep2;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQRCodeCameraFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ScanQRCodeCameraViewModel mViewModel;
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    public static ScanQRCodeCameraFragment newInstance() {
        return new ScanQRCodeCameraFragment();
    }
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
        SharedPreferences sharedPreferencesMedicationIdWrite = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesharedPreferencesMedicationIdWriteEditor = sharedPreferencesMedicationIdWrite.edit();
        sharedPreferencesharedPreferencesMedicationIdWriteEditor.putString("MedicationIdListClick", rawResult.getText());
        sharedPreferencesharedPreferencesMedicationIdWriteEditor.apply();
        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ScanQRCodeCameraFragment.this);
            }
        }, 2000);

        MedicineFragment forwardMedicaion = new MedicineFragment();
        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , forwardMedicaion).commit();

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

}