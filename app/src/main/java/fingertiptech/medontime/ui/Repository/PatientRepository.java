package fingertiptech.medontime.ui.Repository;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import fingertiptech.medontime.ui.RetrofitService.RetrofitService;
import fingertiptech.medontime.ui.jsonplaceholder.MedicationJSONPlaceholder;
import fingertiptech.medontime.ui.jsonplaceholder.PatientJSONPlaceholder;
import fingertiptech.medontime.ui.model.Medication;
import fingertiptech.medontime.ui.model.Patient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientRepository {

    private static PatientRepository patientRepository;

    public static PatientRepository getInstance(){
        if (patientRepository == null){
            patientRepository = new PatientRepository();
        }
        return patientRepository;
    }
    private PatientJSONPlaceholder patientJSONPlaceholder;

    public PatientRepository(){
        patientJSONPlaceholder = RetrofitService.cteateService(PatientJSONPlaceholder.class);
    }

    public MutableLiveData<Patient> getPatient(String patientObjectId){
        MutableLiveData<Patient> patientData = new MutableLiveData<>();
        patientJSONPlaceholder.getPatient(patientObjectId).enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call,
                                   Response<Patient> response) {
                if (response.isSuccessful()){
                    patientData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                patientData.setValue(null);
            }
        });
        return patientData;
    }

    public MutableLiveData<List<Patient>> getAllPatient(){
        MutableLiveData<List<Patient>> patientListData = new MutableLiveData<>();
        patientJSONPlaceholder.getAllPatients().enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call,
                                   Response<List<Patient>> response) {
                if (response.isSuccessful()){
                    patientListData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                patientListData.setValue(null);
            }
        });
        return patientListData;
    }
}
