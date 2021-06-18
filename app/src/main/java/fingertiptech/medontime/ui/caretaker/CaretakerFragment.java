package fingertiptech.medontime.ui.caretaker;

import                                                                                           android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.caretaker.CaretakerViewModel;

public class CaretakerFragment extends Fragment {

    private CaretakerViewModel caretakerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        caretakerViewModel =
                new ViewModelProvider(this).get(CaretakerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_caretaker, container, false);
        final TextView textView = root.findViewById(R.id.text_caretaker);
        caretakerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}