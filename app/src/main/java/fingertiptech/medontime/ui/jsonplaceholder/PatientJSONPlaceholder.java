package fingertiptech.medontime.ui.jsonplaceholder;

import java.util.List;

import fingertiptech.medontime.ui.model.Medication;
import fingertiptech.medontime.ui.model.Patient;
import fingertiptech.medontime.ui.model.TestItem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PatientJSONPlaceholder {

    @GET("PatientAPI?key=sH5O!2cdOqP1%5E")
    Call<List<Patient>> getAllPatients();

    @POST("PatientAPI?key=sH5O!2cdOqP1%5E")
    Call<Patient> addPatient(@Body Patient patient);

    @GET("PatientAPI/{id}?key=sH5O!2cdOqP1%5E")
    Call<Patient> getPatient(@Path("id") String patientObjectId);

    @PUT("PatientAPI?key=sH5O!2cdOqP1%5E")
    Call<Patient> updatePatientPassword(@Body Patient patient );

}
