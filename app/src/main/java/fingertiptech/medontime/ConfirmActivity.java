package fingertiptech.medontime;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
    TextView textView2;
    LogJSONPlaceholder logJSONPlaceholder;
    MedicationJSONPlaceholder medicationJSONPlaceholder;
    Medication medication;

    // adding messages for NFC tagging
    public static final String Error_Detected = "No NFC Tag Detected";
    public static final String Write_Success = "You're all set! \n\nPlease attach the NFC tag to the pillbox & scan to confirm when a notification appears.";
    public static final String Write_Error = "Error during Writing";

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writingTagFilters[];
    boolean writeMode;
    Tag myTag;
    Context context;

    //    TextView edit_message;
    TextView nfc_contents;
    Button ActivateButton;
    LinearLayout toggle_nfc_ui; // this property controls visibility of the NFC widgets in the activity layout.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        textView2 = findViewById(R.id.textView2);

        // setting up NFC Writing

        toggle_nfc_ui = (LinearLayout) findViewById(R.id.toggle_nfc_ui);
        toggle_nfc_ui.setVisibility(View.VISIBLE);

//            edit_message = (TextView) findViewById(R.id.edit_message);
        nfc_contents = (TextView) findViewById(R.id.nfc_contents);

        context = this;

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            setupPopupDialog("Unfortunately, your device is incompatable for NFC scanning. \n\nYou will need to simply click on the button to confirm without verifying the medication's tag ID with the one in our database.");
        } else {
            setupPopupDialog("Please put your phone to the NFC tag attached to the pillbox and hold it steady to where the Confirm Here frame is to confirm pill intake.");

            readFromIntent(getIntent());
            pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);

            tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
            writingTagFilters = new IntentFilter[]{tagDetected};
        }


        btn_confirm_log = findViewById(R.id.btnLog);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://medontime-api.herokuapp.com/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        logJSONPlaceholder= retrofit.create(LogJSONPlaceholder.class);
        medicationJSONPlaceholder = retrofit.create(MedicationJSONPlaceholder.class);
        Call<Medication> createCall = medicationJSONPlaceholder.getMedication(MedicineFragment.resultQRScan);
        createCall.enqueue(new Callback<Medication>() {
            @Override
            public void onResponse(Call<Medication> call, Response<Medication> response) {
                if (!response.isSuccessful()){
                    android.util.Log.i("can find medication", "medication unsuccess" + response.code());
                    return;
                }
                //android.util.Log.i("can find medication", "medication unsuccess" + response.code());
                //Toast.makeText(getApplicationContext(),"Get Medication", Toast.LENGTH_LONG).show();
                medication = response.body();
                textView2.setText("Did you take the medication " + medication.getMedicationName() + "?");
                //logTest(String.valueOf(medication.getPatientID()), medication.getId(), medication.getMedicationName());

                //Toast.makeText(getApplicationContext(), "Medicine is taken!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Medication> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Medicine not added", Toast.LENGTH_SHORT).show();
            }
        });


        btn_confirm_log.setOnClickListener(v -> {

            Call<Medication> createCall2 = medicationJSONPlaceholder.getMedication(MedicineFragment.resultQRScan);
            createCall2.enqueue(new Callback<Medication>() {
                @Override
                public void onResponse(Call<Medication> call, Response<Medication> response) {
                    if (!response.isSuccessful()){
                        android.util.Log.i("can find medication", "medication unsuccess" + response.code());
                        return;
                    }
                    android.util.Log.i("can find medication", "medication unsuccess" + response.code());
                    //Toast.makeText(getApplicationContext(),"Get Medication", Toast.LENGTH_LONG).show();
                    medication = response.body();
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

    /**
     * To be reused for NFC reading and writing confirmation.
     */
    private void setupPopupDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
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

    // for NFC tag writing
    private void write(String text, Tag tag) throws IOException, FormatException {
        NdefRecord[] records = { createRecord(text) };
        NdefMessage message = new NdefMessage(records);
        // Get an instance of Ndef for the tag
        Ndef ndef = Ndef.get(tag);
        // Enable I / O
        ndef.connect();
        // write the message
        ndef.writeNdefMessage(message);
        // close the connection
        ndef.close();
    }

    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payload = new byte[1 + langLength + textLength];

        // set status byte (see NDEF spec for actual bits)
        payload[0] = (byte) langLength;

        // copy langbytes abd textbytes into the payload
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);

        return recordNFC;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        readFromIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        WriteModeOff();
    }

    @Override
    public void onResume() {
        super.onResume();
        WriteModeOn();
    }

    // enabling NFC writing
    private void WriteModeOn() {
        if (nfcAdapter != null) {
            writeMode = true;
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, writingTagFilters, null);
        }
    }

    // disabling NFC writing
    private void WriteModeOff() {
        if (nfcAdapter != null) {
            writeMode = false;
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    // for NFC tag reading
    private void readFromIntent(Intent intent) {
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }
    }

    // for setting up NFC tag views
    private void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) return;

        String text = "";

        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; // get the text encoding
        int languageCodeLength = payload[0] & 0063; // get the language code like en or fr

        text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1);

        Call<Medication> createCall = medicationJSONPlaceholder.getMedication(MedicineFragment.resultQRScan);
        String scannedID = text; // to be used for comparing
        createCall.enqueue(new Callback<Medication>() {
            @Override
            public void onResponse(Call<Medication> call, Response<Medication> response) {
                if (!response.isSuccessful()){
                    android.util.Log.i("can find medication", "medication unsuccess" + response.code());
                    return;
                }
                android.util.Log.i("can find medication", "medication unsuccess" + response.code());
                //Toast.makeText(getApplicationContext(),"Get Medication", Toast.LENGTH_LONG).show();
                medication = response.body();

                // Comparing NFC tag ID with the medication ID from our database.
                if (scannedID == medication.getId()) {

                    logTest(String.valueOf(medication.getPatientID()), medication.getId(), medication.getMedicationName());

                    Toast.makeText(getApplicationContext(), "Medicine is taken!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "This is the wrong medication.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Medication> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Medicine not added", Toast.LENGTH_SHORT).show();
            }
        });


        nfc_contents.setText("NFC Content: " + text);
        setupPopupDialog("Pill intake confirmed for medication ID: \n" + text);
    }

}