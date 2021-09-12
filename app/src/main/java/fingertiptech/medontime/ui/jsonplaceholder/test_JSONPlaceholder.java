package fingertiptech.medontime.ui.jsonplaceholder;

import java.util.List;

import fingertiptech.medontime.ui.model.TestItem;
import retrofit2.Call;
import retrofit2.http.GET;

public interface test_JSONPlaceholder {

    @GET("posts")
    Call<List<TestItem>> getItems();
}
