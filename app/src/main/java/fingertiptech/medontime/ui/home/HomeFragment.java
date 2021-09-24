package fingertiptech.medontime.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import fingertiptech.medontime.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public HomeFragment() {
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button btnAddMed = root.findViewById(R.id.btnAddMed);

        btnAddMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //https://stackoverflow.com/questions/5658675/replacing-a-fragment-with-another-fragment-inside-activity-group
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.setReorderingAllowed(true);
//                // Replace whatever is in the fragment_container view with this fragment
//                transaction.replace(R.id.fragment_container, ExampleFragment.class, null);
//                // Commit the transaction
//                transaction.commit();

                Snackbar.make(view, "Add Medicine", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return root;
    }
}