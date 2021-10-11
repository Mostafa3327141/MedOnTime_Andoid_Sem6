package fingertiptech.medontime.ui.medicine;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fingertiptech.medontime.ui.Repository.MedicationRepository;
import fingertiptech.medontime.ui.model.Medication;

public class MedicineViewModel extends ViewModel {

    private MutableLiveData<Medication> medicationMutableLiveData;
    private MedicationRepository medicationRepository;

    public void initGetMedication(String medicationID){
        if (medicationMutableLiveData != null){
            return;
        }
        medicationRepository = MedicationRepository.getInstance();
        medicationMutableLiveData = medicationRepository.getMedication(medicationID);
    }
    public void initAddMedication(Medication medication){
        if (medicationMutableLiveData != null){
            return;
        }
        medicationRepository = MedicationRepository.getInstance();
        medicationMutableLiveData = medicationRepository.addMedication(medication);
    }
    public LiveData<Medication> getMedicationRepository() {
        return medicationMutableLiveData;
    }

}