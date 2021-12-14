package fingertiptech.medontime.ui.medicationDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fingertiptech.medontime.ui.Repository.MedicationRepository;
import fingertiptech.medontime.ui.model.Medication;

/**
 * This class is viewModel for medication detail fragment
 * It uses LiveData to actively access and use data
 */


public class MedicationDetailedViewModel extends ViewModel {
    private MutableLiveData<Medication> medicationMutableLiveDataWhenGet;

    private MedicationRepository medicationRepository;

    public void initGetMedicationByMedicationId(String medicationID){
        if (medicationMutableLiveDataWhenGet != null){
            return;
        }
        medicationRepository = MedicationRepository.getInstance();
        medicationMutableLiveDataWhenGet = medicationRepository.getMedicationByQRID(medicationID);
    }

    public LiveData<Medication> getMedicationRepositoryWhenGet() {
        return medicationMutableLiveDataWhenGet;
    }
}