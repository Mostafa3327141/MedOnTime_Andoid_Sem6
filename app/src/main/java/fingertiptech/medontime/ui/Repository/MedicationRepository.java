package fingertiptech.medontime.ui.Repository;

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

    public MutableLiveData<Medication> getPatient(String scanQRId){
        MutableLiveData<Medication> patientData = new MutableLiveData<>();
        medicationJSONPlaceholder.getMedication(scanQRId).enqueue(new Callback<Medication>() {
            @Override
            public void onResponse(Call<Medication> call,
                                   Response<Medication> response) {
                if (response.isSuccessful()){
                    patientData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Medication> call, Throwable t) {
                patientData.setValue(null);
            }
        });
        return patientData;
    }
}
