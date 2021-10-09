package fingertiptech.medontime.ui.RetrofitService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MedicationRetrofitService {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://medontime-api.herokuapp.com/API/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S cteateService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
