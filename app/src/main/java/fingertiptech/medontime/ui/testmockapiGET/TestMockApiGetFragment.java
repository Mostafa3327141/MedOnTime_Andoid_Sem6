package fingertiptech.medontime.ui.testmockapiGET;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.jsonplaceholder.test_JSONPlaceholder;
import fingertiptech.medontime.ui.model.TestItem;
import fingertiptech.medontime.ui.recycleadpoter.ItemAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestMockApiGetFragment extends Fragment {

    private TestMockApiGetViewModel testMockApiViewModel;
    private RecyclerView recyclerViewItems;

    public static TestMockApiGetFragment newInstance() {
        return new TestMockApiGetFragment();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testMockApiViewModel =
                new ViewModelProvider(this).get(TestMockApiGetViewModel.class);
        View root = inflater.inflate(R.layout.test_mock_api_get_fragment, container, false);

        recyclerViewItems =root.findViewById(R.id.recyclerViewItems);
        recyclerViewItems.setHasFixedSize(true);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(getContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        test_JSONPlaceholder testJsonPlaceholder = retrofit.create(test_JSONPlaceholder.class);
        Call<List<TestItem>> callItems = testJsonPlaceholder.getItems();

        callItems.enqueue(new Callback<List<TestItem>>() {
            @Override
            public void onResponse(Call<List<TestItem>> call, Response<List<TestItem>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<TestItem> itemList = response.body();
                ItemAdapter postAdapter = new ItemAdapter(getActivity() , itemList);
                recyclerViewItems.setAdapter(postAdapter);
            }

            @Override
            public void onFailure(Call<List<TestItem>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

//    private TestMockApiViewModel mViewModel;
//
//    private RecyclerView recyclerViewItems;
//
//    public static TestMockApiFragment newInstance() {
//        return new TestMockApiFragment();
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//
////        recyclerViewItems =getView().findViewById(R.id.recyclerViewItems);
////        recyclerViewItems.setHasFixedSize(true);
////        recyclerViewItems.setLayoutManager(new LinearLayoutManager(getContext()));
////
////        Retrofit retrofit = new Retrofit.Builder()
////                .baseUrl("https://jsonplaceholder.typicode.com/")
////                .addConverterFactory(GsonConverterFactory.create())
////                .build();
////
////        test_JSONPlaceholder testJsonPlaceholder = retrofit.create(test_JSONPlaceholder.class);
////        Call<List<TestItem>> callItems = testJsonPlaceholder.getItems();
////        callItems.enqueue(new Callback<List<TestItem>>() {
////            @Override
////            public void onResponse(Call<List<TestItem>> call, Response<List<TestItem>> response) {
////                if (!response.isSuccessful()){
////                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
////                    return;
////                }
////                List<TestItem> itemList = response.body();
////                ItemAdapter postAdapter = new ItemAdapter(getActivity() , itemList);
////                recyclerViewItems.setAdapter(postAdapter);
////            }
////
////            @Override
////            public void onFailure(Call<List<TestItem>> call, Throwable t) {
////                Toast.makeText(getActivity(), t.getMessage() , Toast.LENGTH_SHORT).show();
////            }
////        });
//        return inflater.inflate(R.layout.test_mock_api_fragment, container, false);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(TestMockApiViewModel.class);
//        // TODO: Use the ViewModel
//    }

}