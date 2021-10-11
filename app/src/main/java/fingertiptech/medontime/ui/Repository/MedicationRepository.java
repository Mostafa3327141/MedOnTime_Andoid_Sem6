package fingertiptech.medontime.ui.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import fingertiptech.medontime.ui.RetrofitService.RetrofitService;
import fingertiptech.medontime.ui.jsonplaceholder.MedicationJSONPlaceholder;
import fingertiptech.medontime.ui.model.Medication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public MutableLiveData<Medication> getMedication(String scanQRId){
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

    public MutableLiveData<Medication> addMedication(Medication medication){
        MutableLiveData<Medication> medicationData = new MutableLiveData<>();
        medicationJSONPlaceholder.addMedication(medication).enqueue(new Callback<Medication>() {
            @Override
            public void onResponse(Call<Medication> call,
                                   Response<Medication> response) {
                if (response.isSuccessful()){
                    medicationData.setValue(response.body());
                    Log.wtf("object id nancy", String.valueOf(medicationData));

                    String s = "";
                }
            }

            @Override
            public void onFailure(Call<Medication> call, Throwable t) {
                medicationData.setValue(null);
            }
        });
        return medicationData;
    }

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
