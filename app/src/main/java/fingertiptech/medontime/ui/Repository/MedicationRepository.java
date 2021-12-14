package fingertiptech.medontime.ui.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fingertiptech.medontime.ui.RetrofitService.RetrofitService;
import fingertiptech.medontime.ui.jsonplaceholder.MedicationJSONPlaceholder;
import fingertiptech.medontime.ui.model.Medication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Here is our Medication repository to connect our Retrofit service action and our livedata
 */
public class MedicationRepository {

    private static MedicationRepository medicationRepository;

    public static MedicationRepository getInstance(){
        if (medicationRepository == null){
            medicationRepository = new MedicationRepository();
        }
        return medicationRepository;
    }

    private MedicationJSONPlaceholder medicationJSONPlaceholder;

    public MedicationRepository(){
        medicationJSONPlaceholder = RetrofitService.cteateService(MedicationJSONPlaceholder.class);
    }

    /**
     * In this function will call @GET action from our interface and fetch medication and store to
     * our liveData, according to our qrcode ID
     * @param scanQRId QR code ID
     * @return
     */
    public MutableLiveData<Medication> getMedicationByQRID(String scanQRId){
        MutableLiveData<Medication> medicationData = new MutableLiveData<>();
        medicationJSONPlaceholder.getMedication(scanQRId).enqueue(new Callback<Medication>() {
            @Override
            public void onResponse(Call<Medication> call,
                                   Response<Medication> response) {
                if (response.isSuccessful()){
                    medicationData.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<Medication> call, Throwable t) {
                medicationData.setValue(null);
            }
        });
        return medicationData;
    }

    /**
     * In this function will call @GET action from our interface and fetch medication and store to
     * our liveData will store all the medication list
     */
    public MutableLiveData<List<Medication>> getMedicationByQRID(){
        MutableLiveData<List<Medication>> medicationData = new MutableLiveData<>();
        medicationJSONPlaceholder.getMedicationList().enqueue(new Callback<List<Medication>>() {
            @Override
            public void onResponse(Call<List<Medication>> call,
                                   Response<List<Medication>> response) {
                if (response.isSuccessful()){
                    medicationData.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<Medication>> call, Throwable t) {
                medicationData.setValue(null);
            }
        });
        return medicationData;
    }

    /**
     * Here will add medication to our database
     * @param medication
     * @return
     */
    public MutableLiveData<Medication> addMedication(Medication medication){
        MutableLiveData<Medication> medicationData = new MutableLiveData<>();
        medicationJSONPlaceholder.addMedication(medication).enqueue(new Callback<Medication>() {
            @Override
            public void onResponse(Call<Medication> call,
                                   Response<Medication> response) {
                if (response.isSuccessful()){
                    medicationData.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<Medication> call, Throwable t) {
                medicationData.setValue(null);
            }
        });
        return medicationData;
    }


    /**
     * Here will update medication to our database
     * @param medication
     * @return
     */
    public MutableLiveData<Medication> updateMedication(Medication medication){
        MutableLiveData<Medication> medicationData = new MutableLiveData<>();
        medicationJSONPlaceholder.addMedication(medication).enqueue(new Callback<Medication>() {
            @Override
            public void onResponse(Call<Medication> call,
                                   Response<Medication> response) {
                if (response.isSuccessful()){
                    medicationData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Medication> call, Throwable t) {
                medicationData.setValue(null);
            }
        });
        return medicationData;
    }
}
