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

    /**
     * Create an implementation of the API endpoints defined by the service interface.
     * The relative path for a given method is obtained from an annotation on the method describing the request type. The built-in methods are GET, PUT, POST, PATCH, HEAD, DELETE and OPTIONS.
     * @param serviceClass for interface of Retrofit for action @GET, @POST, @POST
     * @return
     */
    public static <T> T cteateService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
