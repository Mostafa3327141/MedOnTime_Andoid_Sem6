package fingertiptech.medontime.ui.testmockapi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestMockApiViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public TestMockApiViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Test Mock Api fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

