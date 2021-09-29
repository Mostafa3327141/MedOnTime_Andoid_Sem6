package fingertiptech.medontime.ui.testmockapiputpost;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestMockApiPutPatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestMockApiPutPatchFragment extends Fragment {

    private RecyclerView recyclerViewItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.test_mock_api_put_patch_fragment, container, false);

        recyclerViewItems =root.findViewById(R.id.recyclerViewPutPatch);
        recyclerViewItems.setHasFixedSize(true);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(getContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        test_JSONPlaceholder testJsonPlaceholder = retrofit.create(test_JSONPlaceholder.class);

        TestItem testItem = new TestItem("2","test", null);
        // When you use PATCH if body is null it mean won't change anything for body
        // for PUT if body is null it will update body as null to DB
//        Call<TestItem> call = testJsonPlaceholder.patchItem(2 , testItem);
        Call<TestItem> call = testJsonPlaceholder.putItem(2 , testItem);
        call.enqueue(new Callback<TestItem>() {
            @Override
            public void onResponse(Call<TestItem> call, Response<TestItem> response) {
                if (!response.isSuccessful()){
                    return;
                }

                List<TestItem> postList = new ArrayList<>();
                postList.add(response.body());
                ItemAdapter adapter = new ItemAdapter(getActivity(), postList);
                recyclerViewItems.setAdapter(adapter);
                Toast.makeText(getActivity(),response.code() + "Response", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<TestItem> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}