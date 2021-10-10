package fingertiptech.medontime.ui.medicine;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.scanNFCtag.ScanNFCTagFragment;

public class MedicineFragmentStep3 extends Fragment {

    private MedicineViewModel medicineViewModel;

    MedicineFragmentStep3.OnDataPass dataPasser;

    // for passing data to activity
    public interface OnDataPass {
        public void onDataPass(String data, View view);
    }

    // connecting the containing class' implementation of the interface to the fragment in the onAttach method
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    // for when you need to handle the passing of data in this fragment
    public void passData(String data, View view) {
        dataPasser.onDataPass(data, view);
    }

    public static MedicineFragmentStep3 newInstance() {
        return new MedicineFragmentStep3();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        System.out.println("See me??");
        System.out.println(MedicineFragment.resultQRScan);
        medicineViewModel =
                new ViewModelProvider(this).get(MedicineViewModel.class);

        if(MedicineFragment.resultQRScan != null){
            medicineViewModel.init(MedicineFragment.resultQRScan);
            medicineViewModel.getMedicationRepository().observe(getViewLifecycleOwner(), medicationsResponse -> {
                System.out.println(medicationsResponse.getId());
                //imageViewMedication.setImageBitmap(convertBase64ToBitmap(medicationsResponse.getMedicationImage()));
            });
        }


        View view = inflater.inflate(R.layout.fragment_medicine_step3_nfc_scan, container, false);
        passData("NFC Fragment Active", view);
        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(MedicineFragmentStep3ViewModel.class);
//        // TODO: Use the ViewModel
//    }

}