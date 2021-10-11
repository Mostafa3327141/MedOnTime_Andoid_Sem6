package fingertiptech.medontime.ui.medicine;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fingertiptech.medontime.R;

public class MedicineFragmentStep3 extends Fragment {

    private MedicineViewModel medicineViewModel;

    public static MedicineFragmentStep3 newInstance() {
        return new MedicineFragmentStep3();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        medicineViewModel =
                new ViewModelProvider(this).get(MedicineViewModel.class);
        View root = inflater.inflate(R.layout.fragment_medicine_step3_nfc_scan, container, false);

        // Sina here is medicationObjectId
        String medicationObjectId = MedicineFragment.resultQRScan;

        return root;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(MedicineFragmentStep3ViewModel.class);
//        // TODO: Use the ViewModel
//    }
//
}