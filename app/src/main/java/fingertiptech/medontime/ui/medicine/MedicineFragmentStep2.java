package fingertiptech.medontime.ui.medicine;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fingertiptech.medontime.R;

public class MedicineFragmentStep2 extends Fragment {

    private MedicineFragmentStep2ViewModel mViewModel;

    Button btnNext2;

    public static MedicineFragmentStep2 newInstance() {
        return new MedicineFragmentStep2();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_medicine_step2, container, false);

        btnNext2 = root.findViewById(R.id.btnNext2);
        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicineFragmentStep3 stepThreeAddMedicine = new MedicineFragmentStep3();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , stepThreeAddMedicine).commit();

            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MedicineFragmentStep2ViewModel.class);
        // TODO: Use the ViewModel
    }

}