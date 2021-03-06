package fingertiptech.medontime.ui.scanQRCodeCamera;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
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
import fingertiptech.medontime.ui.scanQRcode.ScanQRCodeFragment;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQRCodeCameraFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ScanQRCodeCameraViewModel mViewModel;
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    public static ScanQRCodeCameraFragment newInstance() {
        return new ScanQRCodeCameraFragment();
    }
    private ZXingScannerView mScannerView;

    /**
     *  We use the library ZXingScannerView for scan QR code and fetch the data store in QR code
     */
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

    /**
     * Wait 2 seconds to resume the preview. When camera scan QR code will show ID in bar
     * @param rawResult
     */
    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(getActivity(), "Contents = " + rawResult.getText() +
                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ScanQRCodeCameraFragment.this);
            }
        }, 2000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

}