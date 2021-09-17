package fingertiptech.medontime.ui.scanNFCtag;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScanNFCTagViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ScanNFCTagViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Scan NFC Tag Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}