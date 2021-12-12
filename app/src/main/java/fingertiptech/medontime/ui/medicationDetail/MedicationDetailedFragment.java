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

import com.google.gson.Gson;

import java.util.Locale;

import fingertiptech.medontime.R;
import fingertiptech.medontime.databinding.MedicationDetailedFragmentBinding;
import fingertiptech.medontime.ui.home.HomeFragment;
import fingertiptech.medontime.ui.jsonplaceholder.MedicationJSONPlaceholder;
import fingertiptech.medontime.ui.jsonplaceholder.PatientJSONPlaceholder;
import fingertiptech.medontime.ui.medicine.MedicineFragment;
import fingertiptech.medontime.ui.medicine.MedicineFragmentStep2;

public class MedicationDetailedFragment extends Fragment {

    private MedicationDetailedViewModel medicationDetailedViewModel;
    private MedicationDetailedFragmentBinding medicationDetailedFragmentBinding;

    public static MedicationDetailedFragment newInstance() {
        return new MedicationDetailedFragment();
    }

    /**
     * In Home Page will show all patient's medication in recycle view in list,
     * when user click one medication will forward to here "MedicationDetailedFragment"
     * Store "medicationID" in SharedPreferences when user click on list,
     * In here we get medication from Repository
     */
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
            SharedPreferences sharedPreferencesMedicationIdWrite = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor sharedPreferencesharedPreferencesMedicationIdWriteEditor = sharedPreferencesMedicationIdWrite.edit();
            sharedPreferencesharedPreferencesMedicationIdWriteEditor.putString("MedicationIdListClick", medicationsResponse.getId());
            sharedPreferencesharedPreferencesMedicationIdWriteEditor.apply();
        });

        medicationDetailedFragmentBinding.btnBackDetailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment backToHomeFrag = new HomeFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , backToHomeFrag).commit();            }
        });
        medicationDetailedFragmentBinding.btnEditDetailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicineFragment forwardMedicationFrag1 = new MedicineFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , forwardMedicationFrag1).commit();
            }
        });
        return view;
    }


    /**
     * This function will go to the right spinner position when we fetch data from API
     * e.g. Our medication is take 'Every day', then when we fetch from our API and populate data
     * to spinner will go to the correct drop down 'Every day'
     * @param s This is string we fetch from our medication like 'Every day', 'Specific day, and etc
     * @param spinner
     */
    public void setSpinner(String s, Spinner spinner){
        for (int position = 0; position < spinner.getCount(); position++) {
            if(spinner.getItemAtPosition(position).toString().toLowerCase(Locale.ROOT).trim().equals(s.toLowerCase(Locale.ROOT).trim())) {
                spinner.setSelection(position);
                return;
            }
        }
    }

    /**
     * In database we store image as base64, this function is covert base 64 string
     * to Bitmap to show imageView
     * @param b64 is the image base 64 string
     * @return
     */
    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

}