package fingertiptech.medontime.ui.jsonplaceholder;

import java.util.List;

import fingertiptech.medontime.ui.model.Log;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LogJSONPlaceholder {

    @GET("LogAPI?key=sH5O!2cdOqP1%5E")
    Call<List<Log>> getAllLog();

    @POST("LogAPI?key=sH5O!2cdOqP1%5E")
    Call<Log> addLog(@Body Log log);

    @GET("LogAPI/{id}?key=sH5O!2cdOqP1%5E")
    Call<Log> getLog(@Path("id") String logObjectId);
}
