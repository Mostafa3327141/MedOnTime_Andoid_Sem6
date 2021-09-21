package fingertiptech.medontime.ui.medicine;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import fingertiptech.medontime.MainActivity;
import fingertiptech.medontime.R;

public class MedicineFragment extends Fragment {

    private MedicineViewModel medicineViewModel;

    EditText editText_medicine_name;
    EditText editText_unit;
    Spinner unitTypeSpinner;
    EditText editText_medicine_condition;
    Spinner frequencySpinner;
    EditText editText_hoursInBetween;
    TextView textView_medicine_setAlarm;
    Button btnScanQR;
    Button btnAdd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        medicineViewModel =
                new ViewModelProvider(this).get(MedicineViewModel.class);
        View root = inflater.inflate(R.layout.fragment_medicine, container, false);

        editText_medicine_name = root.findViewById(R.id.editText_medName);
        editText_unit = root.findViewById(R.id.unit);
        unitTypeSpinner = root.findViewById(R.id.unitTypeSpinner);
        editText_medicine_condition = root.findViewById(R.id.editText_condition);
        textView_medicine_setAlarm = root.findViewById(R.id.textView_displaySettingTime);
        frequencySpinner = root.findViewById(R.id.frequencySpinner);
        editText_hoursInBetween = root.findViewById(R.id.editText_hoursInBetween);
        btnAdd = root.findViewById(R.id.btnAdd);
        btnScanQR = root.findViewById(R.id.useQRbtn);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.wtf("Button Clicked","Add button clicked");
            }
        });

        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.wtf("Button Clicked","Scan QR button clicked");
            }
        });

        return root;
    }
}