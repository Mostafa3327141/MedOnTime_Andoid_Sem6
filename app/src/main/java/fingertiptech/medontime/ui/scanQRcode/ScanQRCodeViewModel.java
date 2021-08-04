package fingertiptech.medontime.ui.scanQRcode;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScanQRCodeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ScanQRCodeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Scan QR fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}