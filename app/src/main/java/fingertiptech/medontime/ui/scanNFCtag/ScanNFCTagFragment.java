package fingertiptech.medontime.ui.scanNFCtag;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import fingertiptech.medontime.R;


public class ScanNFCTagFragment extends Fragment {

    OnDataPass dataPasser;

    // for passing data to activity
    public interface OnDataPass {
        public void onDataPass(String data);
    }

    public ScanNFCTagFragment() {
        // Required empty public constructor
    }

    // connecting the containing class' implementation of the interface to the fragment in the onAttach method
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    // for when you need to handle the passing of data in this fragment
    public void passData(String data) {
        dataPasser.onDataPass(data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        passData("NFC Fragment Active");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.scan_nfc_tag_fragment,
                container, false);
    }


//    private ScanNFCTagViewModel mViewModel;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        mViewModel =
//                new ViewModelProvider(this).get(ScanNFCTagViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//
//        return root;
//    }




}