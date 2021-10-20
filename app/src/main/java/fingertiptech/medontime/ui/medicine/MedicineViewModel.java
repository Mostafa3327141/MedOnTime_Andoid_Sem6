package fingertiptech.medontime.ui.medicine;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fingertiptech.medontime.ui.Repository.MedicationRepository;
import fingertiptech.medontime.ui.model.Medication;

public class MedicineViewModel extends ViewModel {

    private MutableLiveData<Medication> medicationMutableLiveDataWhenAdd;
    private MutableLiveData<Medication> medicationMutableLiveDataWhenGet;
//    private MutableLiveData<List<Medication>> medicationMutableLiveDataList;
    private MedicationRepository medicationRepository;

    public void initGetMedicationByMedicationId(String medicationID){
        if (medicationMutableLiveDataWhenGet != null){
            return;
        }
        medicationRepository = MedicationRepository.getInstance();
        medicationMutableLiveDataWhenGet = medicationRepository.getMedicationById(medicationID);
    }
//    public void initGetMedication(){
//        if (medicationMutableLiveData != null){
//            return;
//        }
//        medicationRepository = MedicationRepository.getInstance();
//        medicationMutableLiveDataList = medicationRepository.getMedicationById();
//    }
    public void initAddMedication(Medication medication){
        if (medicationMutableLiveDataWhenAdd != null){
            return;
        }
        medicationRepository = MedicationRepository.getInstance();
        medicationMutableLiveDataWhenAdd = medicationRepository.addMedication(medication);
    }

    public LiveData<Medication> getMedicationRepositoryWhenAdd() {
        return medicationMutableLiveDataWhenAdd;
    }
    public LiveData<Medication> getMedicationRepositoryWhenGet() {
        return medicationMutableLiveDataWhenGet;
    }
//    public LiveData<List<Medication>> getMedicationListRepository() {
//        return medicationMutableLiveDataList;
//    }
}