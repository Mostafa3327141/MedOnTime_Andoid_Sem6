package fingertiptech.medontime.ui.caretaker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * This class is viewModel for Caretakers fragment
 * It uses LiveData to actively access and use data
 */

public class CaretakerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CaretakerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Caretaker fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}