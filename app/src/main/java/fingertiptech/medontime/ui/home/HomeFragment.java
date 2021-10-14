package fingertiptech.medontime.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;


import java.util.Date;

import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.jsonplaceholder.LogJSONPlaceholder;
import fingertiptech.medontime.ui.jsonplaceholder.PatientJSONPlaceholder;
import fingertiptech.medontime.ui.model.Log;
import fingertiptech.medontime.ui.model.Patient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public static final int PICK_IMAGE = 1;
    private RecyclerView medicationRecyclerViewItems;

    LogJSONPlaceholder logJSONPlaceholder;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button btnAddMed = root.findViewById(R.id.btnAddMed);

        medicationRecyclerViewItems =root.findViewById(R.id.recycleView_medicine);
        medicationRecyclerViewItems.setHasFixedSize(true);
        medicationRecyclerViewItems.setLayoutManager(new LinearLayoutManager(getContext()));


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

        //--------------------Test Log API-----------------
//
//        Button btnLogTest = root.findViewById(R.id.btnLogTest);
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://medontime-api.herokuapp.com/API/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        logJSONPlaceholder= retrofit.create(LogJSONPlaceholder.class);
//
//        btnLogTest.setOnClickListener(v -> {
//            logTest();
//        });

        return root;
    }

//    private void logTest() {
//        android.util.Log.i("test", "logTest()");
//        Call<Log> createCall = logJSONPlaceholder.addLog(new Log(null, "test", "some med id", "some med name"));
//        createCall.enqueue(new Callback<Log>() {
//            @Override
//            public void onResponse(Call<Log> call, Response<Log> response) {
//                if (!response.isSuccessful()){
//                    android.util.Log.i("testLog", "logTest() unsuccess" + response.code());
//                    return;
//                }
//                android.util.Log.i("testLog", "logTest() success" + response.code());
//                Toast.makeText(getActivity(),"Log added", Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<Log> call, Throwable t) {
//                Toast.makeText(getActivity(), "Log not added", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}

