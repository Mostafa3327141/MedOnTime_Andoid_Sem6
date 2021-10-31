package fingertiptech.medontime.ui.medicationDetail;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.Locale;

import fingertiptech.medontime.R;
import fingertiptech.medontime.databinding.MedicationDetailedFragmentBinding;
import fingertiptech.medontime.ui.jsonplaceholder.MedicationJSONPlaceholder;
import fingertiptech.medontime.ui.jsonplaceholder.PatientJSONPlaceholder;

public class MedicationDetailedFragment extends Fragment {

    private MedicationDetailedViewModel medicationDetailedViewModel;
    private MedicationDetailedFragmentBinding medicationDetailedFragmentBinding;

    public static MedicationDetailedFragment newInstance() {
        return new MedicationDetailedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        medicationDetailedFragmentBinding = MedicationDetailedFragmentBinding.inflate(getLayoutInflater());
        View view = medicationDetailedFragmentBinding.getRoot();
        medicationDetailedViewModel = new ViewModelProvider(this).get(MedicationDetailedViewModel.class);

        SharedPreferences sharedPreferencesMedicationId = getActivity().getPreferences(Context.MODE_PRIVATE);
        String medicaitonListClickId = sharedPreferencesMedicationId.getString("MedicaitonListClickId", "");

        medicationDetailedViewModel.initGetMedicationByMedicationId(medicaitonListClickId);
        medicationDetailedViewModel.getMedicationRepositoryWhenGet().observe(getViewLifecycleOwner(), medicationsResponse ->{
            medicationDetailedFragmentBinding.editTextMedNameDetailed.setText(medicationsResponse.getMedicationName());
            medicationDetailedFragmentBinding.unitDetailed.setText(medicationsResponse.getUnit().replaceAll("[^0-9]", ""));
            setSpinner(medicationsResponse.getUnit().replaceAll("[0-9]", ""), medicationDetailedFragmentBinding.unitTypeSpinner);
            medicationDetailedFragmentBinding.editTextQuantityDetailed.setText(String.valueOf(medicationsResponse.getQuantity()));
            medicationDetailedFragmentBinding.editTextConditionDetailed.setText(medicationsResponse.getCondition());
            setSpinner(medicationsResponse.getFrequency(), medicationDetailedFragmentBinding.frequencySpinnerDetailed);
            medicationDetailedFragmentBinding.editTextHoursInBetweenDetailed.setText(String.valueOf(medicationsResponse.getHoursBetween()));
            medicationDetailedFragmentBinding.textViewDisplaySettingTimeDetailed.setText(medicationsResponse.getFirstDoseTime());
            medicationDetailedFragmentBinding.imageMedicationViewDetailed.setImageBitmap(convertBase64ToBitmap(medicationsResponse.getMedicationImage()));

        });
        return view;
    }


//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        medicationDetailedViewModel = new ViewModelProvider(this).get(MedicationDetailedViewModel.class);
//        // TODO: Use the ViewModel
//    }
    public void setSpinner(String s, Spinner spinner){
        for (int position = 0; position < spinner.getCount(); position++) {
            if(spinner.getItemAtPosition(position).toString().toLowerCase(Locale.ROOT).trim().equals(s.toLowerCase(Locale.ROOT).trim())) {
                spinner.setSelection(position);
                return;
            }
        }
    }
    //this is covert base 64 store from web app to image to app imageview
    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

}