package fingertiptech.medontime.ui.RetrofitService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    /**
     * Here we create RetrofitService to make connect to our database
     * we don't need to create connect everytime, we can reuse this function.
     */
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://medontime-api.herokuapp.com/API/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <T> T cteateService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
