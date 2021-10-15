package fingertiptech.medontime.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fingertiptech.medontime.ui.Repository.MedicationRepository;
import fingertiptech.medontime.ui.model.Medication;

public class HomeViewModel extends ViewModel {

//    private MutableLiveData<String> mText;
    private MutableLiveData<List<Medication>> medicationMutableLiveDataList;
    private MedicationRepository medicationRepository;

    public void initGetMedication(){
        if (medicationMutableLiveDataList != null){
            return;
        }
        medicationRepository = MedicationRepository.getInstance();
        medicationMutableLiveDataList = medicationRepository.getMedicationById();
    }
    public LiveData<List<Medication>> getMedicationListRepository() {
        return medicationMutableLiveDataList;
    }
}