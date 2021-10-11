package fingertiptech.medontime.ui.medicine;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fingertiptech.medontime.ui.Repository.MedicationRepository;
import fingertiptech.medontime.ui.model.Medication;

public class MedicineViewModel extends ViewModel {

    private MutableLiveData<Medication> medicationMutableLiveData;
    private MedicationRepository medicationRepository;

    public void init(String medicationID){
        if (medicationMutableLiveData != null){
            return;
        }
        medicationRepository = MedicationRepository.getInstance();
        medicationMutableLiveData = medicationRepository.getPatient(medicationID);
    }

    public LiveData<Medication> getMedicationRepository() {
        return medicationMutableLiveData;
    }

}