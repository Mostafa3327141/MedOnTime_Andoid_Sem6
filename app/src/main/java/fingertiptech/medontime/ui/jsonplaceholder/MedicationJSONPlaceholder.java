package fingertiptech.medontime.ui.jsonplaceholder;

import java.util.List;

import fingertiptech.medontime.ui.model.Medication;
import fingertiptech.medontime.ui.model.Patient;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MedicationJSONPlaceholder {

    @GET("MedicationAPI?key=sH5O!2cdOqP1%5E")
    Call<List<Medication>> getMedicationList();

    @GET("MedicationAPI/{id}?key=sH5O!2cdOqP1%5E")
    Call<Medication> getMedication(@Path("id") String medicationId);

    @POST("MedicationAPI?key=sH5O!2cdOqP1%5E")
    Call<Medication> addMedication(@Body Medication medication);

    @POST("MedicationAPI?key=sH5O!2cdOqP1%5E")
    Call<Medication> updateMedication(@Body Medication medication);
}
