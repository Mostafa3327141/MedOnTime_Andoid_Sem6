package fingertiptech.medontime.ui.scanNFCtag;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import fingertiptech.medontime.R;


/**
 * This fragment can be accessed at anytime to read the medication ID from the tag. It doesn't confirm anything
 * in our database but is here just to see if there is any data in the tag.
 *
 * We may remove this fragment completely in the future.
 */
public class ScanNFCTagFragment extends Fragment {

    OnDataPass dataPasser;

    // for passing data to activity
    public interface OnDataPass {
        public void onDataPass(boolean isVisible);
    }

    public ScanNFCTagFragment() {
        // Required empty public constructor
    }

    // connecting the containing class' implementation of the interface to the fragment in the onAttach method
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    // for when you need to handle the passing of data in this fragment
    public void passData(boolean isVisible) {
        dataPasser.onDataPass(isVisible);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_nfc_tag_fragment, container, false);

        passData(true);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        passData(false); // to hide NFC contents in the activity
    }

    //    private ScanNFCTagViewModel mViewModel;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        mViewModel =
//                new ViewModelProvider(this).get(ScanNFCTagViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//
//        return root;
//    }




}