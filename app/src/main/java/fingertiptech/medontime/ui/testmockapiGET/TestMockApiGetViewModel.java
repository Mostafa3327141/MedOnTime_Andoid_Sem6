package fingertiptech.medontime.ui.testmockapiGET;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestMockApiGetViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public TestMockApiGetViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Test Mock Api fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

