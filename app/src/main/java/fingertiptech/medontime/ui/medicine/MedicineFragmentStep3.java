package fingertiptech.medontime.ui.medicine;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fingertiptech.medontime.R;

/**
 * This fragment is the 3rd step of the add medication process where the NFC tag needs to be setup.
 * At this point the patient can position their phone to write the medication ID to the NFC tag
 * and afterwards it will notify with a popup from MainActivity when it is written.
 *
 * If the phone does not have an NFC Adaptor, a pop-up message will be trigger right away to let them know
 * the medication's still added to our database, but will need to confirm with NFC scanning when the notification
 * appears.
 */
public class MedicineFragmentStep3 extends Fragment {

    private MedicineViewModel medicineViewModel;

    OnObjectIdPassToNFC objectIdPasser;

    // for passing data to activity
    public interface OnObjectIdPassToNFC {
        public void onObjectIdPassToNFC(boolean isVisible, String objectId);
    }

    // connecting the containing class' implementation of the interface to the fragment in the onAttach method
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        objectIdPasser = (OnObjectIdPassToNFC) context;
    }

    // for when you need to handle the passing of data in this fragment
    public void passData(boolean isVisible, String objectId) {
        objectIdPasser.onObjectIdPassToNFC(isVisible, objectId);
    }


    public static MedicineFragmentStep3 newInstance() {
        return new MedicineFragmentStep3();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        medicineViewModel =
                new ViewModelProvider(this).get(MedicineViewModel.class);

        // Sina here is medicationObjectId
        String medicationObjectId = MedicineFragment.resultQRScan;
        System.out.println(medicationObjectId);

//        if(medicationObjectId != null){
//            medicineViewModel.initGetMedicationByMedicationId(MedicineFragment.resultQRScan);
//            medicineViewModel.getMedicationRepositoryWhenGet().observe(getViewLifecycleOwner(), medicationsResponse -> {
//                System.out.println(medicationObjectId);
//                //imageViewMedication.setImageBitmap(convertBase64ToBitmap(medicationsResponse.getMedicationImage()));
//            });
//        }


        View view = inflater.inflate(R.layout.fragment_medicine_step3_nfc_scan, container, false);
        passData(true, MedicineFragment.resultQRScan);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        passData(false, ""); // to hide NFC contents in the activity
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(MedicineFragmentStep3ViewModel.class);
//
//    }
//
}