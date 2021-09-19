package fingertiptech.medontime.ui.testmockapidelete;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.jsonplaceholder.test_JSONPlaceholder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TestMockApiDelete extends Fragment {


    private RecyclerView recyclerViewDelete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.test_mock_api_delete_fragment, container, false);
        recyclerViewDelete =root.findViewById(R.id.recyclerViewDelete);
        recyclerViewDelete.setHasFixedSize(true);
        recyclerViewDelete.setLayoutManager(new LinearLayoutManager(getContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        test_JSONPlaceholder testJsonPlaceholder = retrofit.create(test_JSONPlaceholder.class);
        Call<Void> call = testJsonPlaceholder.deleteItem(2);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){
                    return;
                }
                Toast.makeText(getActivity(), "Deleted Successfully : " + response.code(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        return root;
    }

}