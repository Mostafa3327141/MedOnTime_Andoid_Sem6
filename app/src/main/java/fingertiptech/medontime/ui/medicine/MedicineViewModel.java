package fingertiptech.medontime.ui.medicine;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fingertiptech.medontime.ui.Repository.MedicationRepository;
import fingertiptech.medontime.ui.model.Medication;

public class MedicineViewModel extends ViewModel {

    private MutableLiveData<Medication> medicationMutableLiveData;
    private MedicationRepository medicationRepository;
    public void init(){
        if (medicationMutableLiveData != null){
            return;
        }
        medicationRepository = MedicationRepository.getInstance();
        medicationMutableLiveData = medicationRepository.getPatient();

    }

    public LiveData<Medication> getPatientRepository() {
        return medicationMutableLiveData;
    }

//    public MedicineViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is Medicine fragment");
//    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }
}