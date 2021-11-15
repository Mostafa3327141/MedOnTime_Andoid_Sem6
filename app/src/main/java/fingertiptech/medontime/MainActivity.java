package fingertiptech.medontime;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import fingertiptech.medontime.ui.scanNFCtag.ScanNFCTagFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import fingertiptech.medontime.ui.home.HomeFragment;
import fingertiptech.medontime.ui.medicine.MedicineFragmentStep3;

public class MainActivity extends AppCompatActivity implements MedicineFragmentStep3.OnObjectIdPassToNFC, ScanNFCTagFragment.OnDataPass {

    private AppBarConfiguration mAppBarConfiguration;
//    https://medontime.herokuapp.com/
//    https://medontime-api.herokuapp.com/api/caretakerapi?key=sH5O!2cdOqP1%5E
//    https://medontime-api.herokuapp.com/API/MedicationAPI?key=sH5O!2cdOqP1%5E
//    https://medontime-api.herokuapp.com/API/PatientAPI?key=sH5O!2cdOqP1%5E
    //https://medontime-api.herokuapp.com/api/logapi?key=sH5O!2cdOqP1%5E

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
    public void onObjectIdPassToNFC(boolean isVisible, String objectId) {
        System.out.println(objectId);

        if (isVisible) {
            // setting up NFC Writing

            toggle_nfc_ui = (LinearLayout) findViewById(R.id.toggle_nfc_ui);
            toggle_nfc_ui.setVisibility(View.VISIBLE);

//            edit_message = (TextView) findViewById(R.id.edit_message);
            nfc_contents = (TextView) findViewById(R.id.nfc_contents);

            ActivateButton = (Button) findViewById(R.id.ActivateButton);

            context = this;

            ActivateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (nfcAdapter == null) {
                        setupPopupDialog("NFC tag cannot be written");
                    } else {
                        try {
                            if (myTag == null) {
                                Toast.makeText(context, Error_Detected, Toast.LENGTH_LONG).show();
                            } else {
                                write(objectId, myTag);
                                setupPopupDialog(Write_Success);
                                Toast.makeText(context, Write_Success, Toast.LENGTH_LONG).show();
                            }
                        } catch (IOException e) {
                            setupPopupDialog(Write_Error);
                            Toast.makeText(context, Write_Error, Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        } catch (FormatException e) {
                            setupPopupDialog(Write_Error);
                            Toast.makeText(context, Write_Error, Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }
            });
            nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            if (nfcAdapter == null) {
                setupPopupDialog("This page is for setting up confirmation for taking a medication with NFC scanning, though this device does not support NFC. Please use QR code scanning for confirming pill intake instead. \n\nMedication's been added! Head back to the homepage or use the app later when notified.");
            } else {
                setupPopupDialog("Please place your phone to your empty NFC tag and click the Scan Here button to write medication data into the tag.");

                //readFromIntent(getIntent());
                pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
                IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);

                tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
                writingTagFilters = new IntentFilter[]{tagDetected};
            }
        } else {
            toggle_nfc_ui = (LinearLayout) findViewById(R.id.toggle_nfc_ui);
            toggle_nfc_ui.setVisibility(View.INVISIBLE);
        }
    }

    // setting up NFC reading only to confirm the medication was taken from ScanNFCTagFragment
    @Override
    public void onDataPass(boolean isVisible) {
        if (isVisible) {
            // setting up NFC Writing

            toggle_nfc_ui = (LinearLayout) findViewById(R.id.toggle_nfc_ui);
            toggle_nfc_ui.setVisibility(View.VISIBLE);

//            edit_message = (TextView) findViewById(R.id.edit_message);
            nfc_contents = (TextView) findViewById(R.id.nfc_contents);

            context = this;

            nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            if (nfcAdapter == null) {
                setupPopupDialog("Unfortunately, your device is incompatable for NFC scanning. \n\nPlease go to the QR code section of the app for scanning to confirm pill intake.");
            } else {
                setupPopupDialog("Please put your phone to the NFC tag attached to the pillbox and hold it steady to where the Scan Here frame is to confirm pill intake.");

                readFromIntent(getIntent());
                pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
                IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);

                tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
                writingTagFilters = new IntentFilter[]{tagDetected};
            }
        } else {
            toggle_nfc_ui = (LinearLayout) findViewById(R.id.toggle_nfc_ui);
            toggle_nfc_ui.setVisibility(View.INVISIBLE);
        }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        // erase Login account info sharedpreference first, since we keep use the same simulator for testing
        SharedPreferences sharedPreferencesLoginUserInformation = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesLoginUserInformationEditor = sharedPreferencesLoginUserInformation.edit();
        sharedPreferencesLoginUserInformationEditor.putString("PatientId", "");
        sharedPreferencesLoginUserInformationEditor.apply();
        SharedPreferences sharedPreferencesLoginUserPatientObjectId = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesLoginUserPatientObjectIdEditor = sharedPreferencesLoginUserPatientObjectId.edit();
        sharedPreferencesLoginUserPatientObjectIdEditor.putString("PatientObjectId", "");
        sharedPreferencesLoginUserPatientObjectIdEditor.apply();
        SharedPreferences sharedPreferencesPatientLogInInfo = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesPatientLogInInfoEditor = sharedPreferencesPatientLogInInfo.edit();
        sharedPreferencesLoginUserInformationEditor.putString("PatientLogInInfo", "");
        sharedPreferencesLoginUserInformationEditor.apply();
        SharedPreferences sharedPreferencesMedicationIdWrite = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesharedPreferencesMedicationIdWriteEditor = sharedPreferencesMedicationIdWrite.edit();
        sharedPreferencesharedPreferencesMedicationIdWriteEditor.putString("MedicationIdListClick", "");
        sharedPreferencesharedPreferencesMedicationIdWriteEditor.apply();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_medicine, R.id.nav_caretaker,
                R.id.nav_scanQR,
                R.id.nav_login,
                R.id.nav_scanQRCamera)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

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

        nfc_contents.setText("NFC Content: " + text);
        setupPopupDialog("Pill intake confirmed for medication ID: \n" + text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}