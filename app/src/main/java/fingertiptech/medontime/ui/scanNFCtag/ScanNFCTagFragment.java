package fingertiptech.medontime.ui.scanNFCtag;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fingertiptech.medontime.R;

public class ScanNFCTagFragment extends Fragment {

    private ScanNFCTagViewModel mViewModel;

    public static ScanNFCTagFragment newInstance() {
        return new ScanNFCTagFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scan_nfc_tag_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ScanNFCTagViewModel.class);
        // TODO: Use the ViewModel
    }

}