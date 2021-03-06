package fingertiptech.medontime;

import static io.realm.Realm.getApplicationContext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import fingertiptech.medontime.ui.jsonplaceholder.LogJSONPlaceholder;
import fingertiptech.medontime.ui.jsonplaceholder.MedicationJSONPlaceholder;
import fingertiptech.medontime.ui.medicine.MedicineFragment;
import fingertiptech.medontime.ui.model.Log;
import fingertiptech.medontime.ui.model.Medication;
import fingertiptech.medontime.ui.scanNFCtag.ScanNFCTagFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class extends BroadcastReceiver interface and it gets executed when the user clicks on the Notification
 * It is used to confirm that the patient took the medication and send logs to the API.
 * The caretaker can then see what time each dose of medication is taken and keep track of everything
 * It user Intent class to open ConfirmActivity to process the NFC functionality to confirm that this is the right medication based on the medication id
 */
public class NotificationReceiver extends BroadcastReceiver {

    LogJSONPlaceholder logJSONPlaceholder;
    MedicationJSONPlaceholder medicationJSONPlaceholder;


    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("toastMessage");
        String medicationId = intent.getStringExtra("medicationId");
        String patientId = intent.getStringExtra("patientId");
        String medicationName = intent.getStringExtra("medicationName");
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://medontime-api.herokuapp.com/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        logJSONPlaceholder= retrofit.create(LogJSONPlaceholder.class);
        medicationJSONPlaceholder = retrofit.create(MedicationJSONPlaceholder.class);

        Call<Log> createCall = logJSONPlaceholder.addLog(new Log(null, patientId, medicationId, medicationName));

        createCall.enqueue(new Callback<Log>() {
            @Override
            public void onResponse(Call<Log> call, Response<Log> response) {
                if (!response.isSuccessful()){
                    android.util.Log.i("testLog", "logTest() unsuccess" + response.code());
                    return;
                }
                android.util.Log.i("testLog", "logTest() success" + response.code());
                Toast.makeText(getApplicationContext(),"Log added", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Log> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Log not added", Toast.LENGTH_SHORT).show();
            }
        });

        Intent i=new Intent(context,ConfirmActivity.class);
        context.startActivity(i);


//        btn_confirm_log.setOnClickListener(v -> {
//            Call<Medication> createCall = medicationJSONPlaceholder.getMedication(MedicineFragment.resultQRScan);
//            createCall.enqueue(new Callback<Medication>() {
//                @Override
//                public void onResponse(Call<Medication> call, Response<Medication> response) {
//                    if (!response.isSuccessful()){
//                        android.util.Log.i("can find medication", "medication unsuccess" + response.code());
//                        return;
//                    }
//                    android.util.Log.i("can find medication", "medication unsuccess" + response.code());
//                    //Toast.makeText(getApplicationContext(),"Get Medication", Toast.LENGTH_LONG).show();
//                    Medication medication = response.body();
//                    logTest(String.valueOf(medication.getPatientID()), medication.getId(), medication.getMedicationName());
//
//                    Toast.makeText(context, "Medicine is taken!", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onFailure(Call<Medication> call, Throwable t) {
//                    Toast.makeText(context, "Medicine not added", Toast.LENGTH_SHORT).show();
//                }
//            });
//        });
    }

//    private void logTest(String patientId, String medicationId, String medicationName) {
//        android.util.Log.i("test", "logTest()");
//
//        Call<Log> createCall = logJSONPlaceholder.addLog(new Log(null, patientId, medicationId, medicationName));
//
//        createCall.enqueue(new Callback<Log>() {
//            @Override
//            public void onResponse(Call<Log> call, Response<Log> response) {
//                if (!response.isSuccessful()){
//                    android.util.Log.i("testLog", "logTest() unsuccess" + response.code());
//                    return;
//                }
//                android.util.Log.i("testLog", "logTest() success" + response.code());
//                Toast.makeText(getApplicationContext(),"Log added", Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<Log> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "Log not added", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
