package fingertiptech.medontime.ui.testmockapiPost;

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
import fingertiptech.medontime.ui.model.TestComment;
import fingertiptech.medontime.ui.model.TestItem;
import fingertiptech.medontime.ui.recycleadpoter.ItemAdapter;
import kotlin.collections.ArrayDeque;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;


public class TestMockApiPostFragment extends Fragment {
    private RecyclerView recyclerViewItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.test_mock_api_post_fragment, container, false);
        recyclerViewItems =root.findViewById(R.id.recyclerViewPostItem);
        recyclerViewItems.setHasFixedSize(true);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(getContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        test_JSONPlaceholder testJsonPlaceholder = retrofit.create(test_JSONPlaceholder.class);

        TestItem testItem = new TestItem("18","test", "body test");

        // for Call<TestItem> createItem(@Body TestItem testItem);
//        Call<TestItem> call = testJsonPlaceholder.createItem(testItem);


//       for Call<TestItem> createItem(@Field("userId") String userId,@Field("title") String title, @Field("body") String text);
        Call<TestItem> call = testJsonPlaceholder.createItem("20","test", "body test");

        call.enqueue(new Callback<TestItem>() {
            @Override
            public void onResponse(Call<TestItem> call, Response<TestItem> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<TestItem> itemList = new ArrayList<>();
                itemList.add(response.body());

                ItemAdapter postAdapter = new ItemAdapter(getActivity() , itemList);
                recyclerViewItems.setAdapter(postAdapter);

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