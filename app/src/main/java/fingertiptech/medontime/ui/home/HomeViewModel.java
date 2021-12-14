package fingertiptech.medontime.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fingertiptech.medontime.ui.Repository.MedicationRepository;
import fingertiptech.medontime.ui.Repository.PatientRepository;
import fingertiptech.medontime.ui.model.Medication;
import fingertiptech.medontime.ui.model.Patient;

/**
 * This class is viewModel for home fragment
 * It uses LiveData to actively access and use medication data
 */

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Medication>> medicationMutableLiveDataList;

    private MutableLiveData<Patient> patientMutableLiveData;

    private MedicationRepository medicationRepository;

    private PatientRepository patientRepository;

    /**
     * Livedata will retrieve all medication from our API, and observe to our home fragment
      */
    public void initGetMedication(){
        if (medicationMutableLiveDataList != null){
            return;
        }
        medicationRepository = MedicationRepository.getInstance();
        medicationMutableLiveDataList = medicationRepository.getMedicationByQRID();
    }
    public void initGetPatient(String patientId){
        if (patientMutableLiveData != null){
            return;
        }
        patientRepository = PatientRepository.getInstance();
        patientMutableLiveData = patientRepository.getPatient(patientId);
    }
    public LiveData<List<Medication>> getMedicationListRepository() {
        return medicationMutableLiveDataList;
    }
    public MutableLiveData<Patient> getPatientMutableLiveDataRepository() {
        return patientMutableLiveData;
    }
}