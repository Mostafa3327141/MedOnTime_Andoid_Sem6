package fingertiptech.medontime.ui.jsonplaceholder;

import java.util.List;

import fingertiptech.medontime.ui.model.Patient;
import fingertiptech.medontime.ui.model.TestItem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PatientJSONPlaceholder {

    @GET("PatientAPI?key=sH5O!2cdOqP1%5E")
    Call<List<Patient>> getAllPatients();

    @POST("PatientAPI?key=sH5O!2cdOqP1%5E")
    Call<Patient> addPatient(@Body Patient patient);
}
