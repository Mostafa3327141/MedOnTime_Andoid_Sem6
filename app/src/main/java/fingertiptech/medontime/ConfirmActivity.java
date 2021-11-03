package fingertiptech.medontime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import fingertiptech.medontime.ui.jsonplaceholder.LogJSONPlaceholder;
import fingertiptech.medontime.ui.jsonplaceholder.MedicationJSONPlaceholder;
import fingertiptech.medontime.ui.medicine.MedicineFragment;
import fingertiptech.medontime.ui.model.Log;
import fingertiptech.medontime.ui.model.Medication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfirmActivity extends AppCompatActivity {

    Button btn_confirm_log;
    LogJSONPlaceholder logJSONPlaceholder;
    MedicationJSONPlaceholder medicationJSONPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        btn_confirm_log = findViewById(R.id.btnLog);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://medontime-api.herokuapp.com/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        logJSONPlaceholder= retrofit.create(LogJSONPlaceholder.class);
        medicationJSONPlaceholder = retrofit.create(MedicationJSONPlaceholder.class);

        btn_confirm_log.setOnClickListener(v -> {
            Call<Medication> createCall = medicationJSONPlaceholder.getMedication(MedicineFragment.resultQRScan);
            createCall.enqueue(new Callback<Medication>() {
                @Override
                public void onResponse(Call<Medication> call, Response<Medication> response) {
                    if (!response.isSuccessful()){
                        android.util.Log.i("can find medication", "medication unsuccess" + response.code());
                        return;
                    }
                    android.util.Log.i("can find medication", "medication unsuccess" + response.code());
                    //Toast.makeText(getApplicationContext(),"Get Medication", Toast.LENGTH_LONG).show();
                    Medication medication = response.body();
                    logTest(String.valueOf(medication.getPatientID()), medication.getId(), medication.getMedicationName());

                    Toast.makeText(getApplicationContext(), "Medicine is taken!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Medication> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Medicine not added", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }


    private void logTest(String patientId, String medicationId, String medicationName) {
        android.util.Log.i("test", "logTest()");

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
    }

}