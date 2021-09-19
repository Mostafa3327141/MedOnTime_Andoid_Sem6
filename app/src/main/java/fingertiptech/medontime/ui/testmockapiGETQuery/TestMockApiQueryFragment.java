package fingertiptech.medontime.ui.testmockapiGETQuery;

import android.os.Bundle;

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
import fingertiptech.medontime.ui.model.TestComment;
import fingertiptech.medontime.ui.recycleadpoter.CommentAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestMockApiQueryFragment extends Fragment {

    private RecyclerView recyclerViewComments;
    public static TestMockApiQueryFragment newInstance() {
        return new TestMockApiQueryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.test_mock_api_query_fragment, container, false);

        recyclerViewComments =root.findViewById(R.id.recyclerViewComment);
        recyclerViewComments.setHasFixedSize(true);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(getContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        test_JSONPlaceholder testJsonPlaceholder = retrofit.create(test_JSONPlaceholder.class);
        Call<List<TestComment>> callComments = testJsonPlaceholder.getComments(2);

        callComments.enqueue(new Callback<List<TestComment>>() {
            @Override
            public void onResponse(Call<List<TestComment>> call, Response<List<TestComment>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<TestComment> commentsList = response.body();
                CommentAdapter commentAdapter = new CommentAdapter(getActivity() , commentsList);
                recyclerViewComments.setAdapter(commentAdapter);
            }

            @Override
            public void onFailure(Call<List<TestComment>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}