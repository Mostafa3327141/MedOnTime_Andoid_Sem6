package fingertiptech.medontime.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fingertiptech.medontime.ui.Repository.MedicationRepository;
import fingertiptech.medontime.ui.Repository.PatientRepository;
import fingertiptech.medontime.ui.model.Patient;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<Patient> patientMutableLiveData;
    private MutableLiveData<List<Patient>> patientListMutableLiveData;

    private PatientRepository patientRepository;

    public void getPatientInit(String patientObjectId){
        if (patientMutableLiveData != null){
            return;
        }
        patientRepository = PatientRepository.getInstance();
        patientMutableLiveData = patientRepository.getPatient(patientObjectId);
    }
    public void getAllPatientInit(String patientObjectId){
        if (patientListMutableLiveData != null){
            return;
        }
        patientRepository = PatientRepository.getInstance();
        patientListMutableLiveData = patientRepository.getAllPatient();
    }
    public LiveData<Patient> getPatient() {
        return patientMutableLiveData;
    }
    public LiveData<List<Patient>> getListPatient() {
        return patientListMutableLiveData;
    }
}