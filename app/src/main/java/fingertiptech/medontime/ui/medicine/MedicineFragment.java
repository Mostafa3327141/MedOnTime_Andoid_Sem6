package fingertiptech.medontime.ui.medicine;

import static android.app.Activity.RESULT_OK;
import static fingertiptech.medontime.Notification.NOTIFICATION_CHANNEL_ID;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.Gson;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import fingertiptech.medontime.ConfirmActivity;
import fingertiptech.medontime.NotificationReceiver;
import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.home.HomeFragment;
import fingertiptech.medontime.ui.imageConvert.ImageBase64Convert;
import fingertiptech.medontime.ui.model.Medication;

public class MedicineFragment extends Fragment {

    private NotificationManagerCompat notificationManagerCompat;
    private MaterialTimePicker timePicker;
    Calendar firstDoseTime;

    private MedicineViewModel medicineViewModel;
    public static final int PICK_IMAGE = 1;
    public static String resultQRScan;

    EditText editText_medicine_name;
    EditText editText_unit;
    EditText editText_quantity;
    Spinner unitTypeSpinner;
    EditText editText_medicine_condition;
    Spinner frequencySpinner;
    EditText editText_hoursInBetween;
    Button btnScanQR;
    Button btnNext;
    Button btnSetTime;
    int hoursInBetween =1;
    Bitmap medicationPic;
    String medicationId;
    String medicationName;
    String patientId;


    /**
     * If patient scan QR code will forward to MedicineFragment then in here
     * will call writeIntoField() function.
     * Otherwise, Patient need enter their medication information (Name, Unit, Frequency, Quantity, Condition, Unit Type)
     * will save to our sharedPreference first then go to next step for taking picture of medication
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_medicine_step1_info, container, false);

        notificationManagerCompat = NotificationManagerCompat.from(getContext());
        firstDoseTime = Calendar.getInstance();

        editText_medicine_name = root.findViewById(R.id.editText_medName);
        editText_unit = root.findViewById(R.id.unit);
        unitTypeSpinner = root.findViewById(R.id.unitTypeSpinner);
        editText_quantity = root.findViewById(R.id.editText_quantity);
        editText_medicine_condition = root.findViewById(R.id.editText_condition);
        frequencySpinner = root.findViewById(R.id.frequencySpinner);
        btnNext = root.findViewById(R.id.btnNext);
        btnScanQR = root.findViewById(R.id.useQRbtn);
        btnSetTime = root.findViewById(R.id.btnSetTime);

        editText_hoursInBetween = root.findViewById(R.id.editText_interval);


        medicineViewModel =
                new ViewModelProvider(this).get(MedicineViewModel.class);

        // this is for home fram recycle onclick -> detail -> edit
        SharedPreferences sharedPreferencesMedicationIdListOnclick = getActivity().getPreferences(Context.MODE_PRIVATE);
        String medicationListIdOnClick = sharedPreferencesMedicationIdListOnclick.getString("MedicationIdListClick", "");
        if (!"".equals(medicationListIdOnClick)){
            resultQRScan = medicationListIdOnClick;
            writeIntoFeild();
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferencesMedicationId = getActivity().getPreferences(Context.MODE_PRIVATE);
                String patientId = sharedPreferencesMedicationId.getString("PatientId", "");
                // if user not login cannot add medicine because no patient cannot save to db and generate patinet id
                if ("".equals(patientId)){
                    HomeFragment forwardToHomeFrag = new HomeFragment();
                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , forwardToHomeFrag).commit();
                    Toast.makeText(getActivity(), "Please login or create an account first before create medication", Toast.LENGTH_LONG).show();
                    return;
                }
                // if filed is blank still can forward to next page without curshing
                // but just don't need to save to db
                if("".equals(editText_medicine_name.getText().toString())
                        && "".equals(editText_medicine_condition.getText().toString())){
                    MedicineFragmentStep2 forwardMedicaionFrag2 = new MedicineFragmentStep2();
                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , forwardMedicaionFrag2).commit();
                    return;
                }else if (null == resultQRScan) {
                    // if patient without caretaker they need to add by themself
                    Medication addMedication = new Medication(null, Integer.parseInt(patientId),
                            editText_medicine_name.getText().toString(), null,
                            editText_unit.getText().toString() + unitTypeSpinner.getSelectedItem().toString(),
                            ("".equals(editText_quantity.getText().toString())) ? 0 : Integer.valueOf(editText_quantity.getText().toString()),
                            editText_medicine_condition.getText().toString(),
                            btnSetTime.getText().toString(),
                            ("".equals(editText_hoursInBetween.getText().toString())) ? 0 : Integer.valueOf(editText_hoursInBetween.getText().toString()),
                            frequencySpinner.getSelectedItem().toString(),
                            null);

                    SharedPreferences sharedPreferencesMedicationAdd = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor sharedPreferencesMedicationAddEditor = sharedPreferencesMedicationAdd.edit();
                    Gson gson = new Gson();
                    String medicationAddInfo = gson.toJson(addMedication);
                    sharedPreferencesMedicationAddEditor.putString("MedicationAdd", medicationAddInfo);
                    sharedPreferencesMedicationAddEditor.apply();

                }else{
                    MedicineFragmentStep2 forwardMedicaionFrag2 = new MedicineFragmentStep2();
                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , forwardMedicaionFrag2).commit();

                }

            }
        });

        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    startGallery();
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                }
            }
        });

        btnSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_hoursInBetween.getText().toString().matches("")){
                    editText_hoursInBetween.setError("Please enter the Interval before setting time!");
                    //Toast.makeText(getActivity(), "Please enter the Interval before setting time!",Toast.LENGTH_LONG).show();
                }else {
                    hoursInBetween = Integer.parseInt(editText_hoursInBetween.getText().toString());
                    showTimePicker();
                }
            }
        });


        return root;
    }

    private void setAlarm() {
        long intervalInMillis = hoursInBetween  * 60 * 60 *1000;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
        
                sendNotification("Medication Reminder", "It is time to take " + medicationName, medicationPic);
            }
        };
        timer.scheduleAtFixedRate(task, firstDoseTime.getTime(), intervalInMillis);
        Toast.makeText(getContext(), "Alarm is set for: " + firstDoseTime.getTime(), Toast.LENGTH_LONG).show();
    }

    private void showTimePicker() {
        Calendar currentTime = Calendar.getInstance();
        int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentTime.get(Calendar.MINUTE);
        timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(currentHour)
                .setMinute(currentMinute)
                .setTitleText("Select your first dose time")
                .build();

        timePicker.show(getParentFragmentManager(), "MedOnTime");
        timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timePicker.getHour() > 12){
                    btnSetTime.setText( timePicker.getHour() - 12  + " : " + timePicker.getMinute() + " PM");
                }else {
                    btnSetTime.setText( timePicker.getHour() + " : " + timePicker.getMinute() + " AM");
                }

                firstDoseTime.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                firstDoseTime.set(Calendar.MINUTE, timePicker.getMinute());
                firstDoseTime.set(Calendar.SECOND, 0);
                firstDoseTime.set(Calendar.MILLISECOND, 0);

                setAlarm();
            }
        });
    }

    /**
     * After we select our QR code image from our gallery, and get id from image
     * writeIntoFiled() will be called.
     * It will get medication information from API, then use liveData and viewModel to populate into field
     */
    public void writeIntoFeild(){
        medicineViewModel.initGetMedicationByMedicationId(resultQRScan);
        medicineViewModel.getMedicationRepositoryWhenGet().observe(getViewLifecycleOwner(), medicationsResponse -> {
            medicationId = medicationsResponse.getId();
            editText_medicine_name.setText(medicationsResponse.getMedicationName());
            medicationName = medicationsResponse.getMedicationName();
            patientId = String.valueOf(medicationsResponse.getPatientID());
            // get patient id and fetch medicaion info put into recycleview
            SharedPreferences sharedPreferencesPatientId = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor sharedPreferencesPatientIdEditor = sharedPreferencesPatientId.edit();
            sharedPreferencesPatientIdEditor.putString("PatientId",String.valueOf(medicationsResponse.getPatientID()));
            sharedPreferencesPatientIdEditor.apply();
            // for our unit from api will be "34 g" so we need split number and unit
            // number will be input the textfield and unit will be in spinner
            editText_unit.setText(medicationsResponse.getUnit().replaceAll("[^0-9]", ""));
            setSpinner(medicationsResponse.getUnit().replaceAll("[0-9]", ""),unitTypeSpinner);
            editText_quantity.setText(String.valueOf(medicationsResponse.getQuantity()));

            editText_medicine_condition.setText(medicationsResponse.getCondition());
            setSpinner(medicationsResponse.getFrequency(), frequencySpinner);
            editText_hoursInBetween.setText(String.valueOf(medicationsResponse.getHoursBetween()));
            btnSetTime.setText(medicationsResponse.getFirstDoseTime());

            medicationPic = (Bitmap) ImageBase64Convert.convertBase64ToBitmap(medicationsResponse.getMedicationImage());

            String strFirstDoseTime = medicationsResponse.getFirstDoseTime();
            int firstDoseTimeHour = Integer.parseInt(strFirstDoseTime.substring(0, 2));
            int firstDoseTimeMinute = Integer.parseInt(strFirstDoseTime.substring(3, 5));
            String firstDoseTimeAM_PM = strFirstDoseTime.substring(6, 8);

            if(firstDoseTimeAM_PM.equals("PM")){
                firstDoseTimeHour += 12;
            }
            firstDoseTime.set(Calendar.HOUR_OF_DAY, firstDoseTimeHour);
            firstDoseTime.set(Calendar.MINUTE, firstDoseTimeMinute);
            firstDoseTime.set(Calendar.SECOND, 0);
            firstDoseTime.set(Calendar.MILLISECOND, 0);
            setAlarm();

        });

    }


    /**
     * This function will go to the right spinner position when we fetch data from API
     * e.g. Our medication is take 'Every day', then when we fetch from our API and populate data
     * to spinner will go to the correct drop down 'Every day'
     * @param s This is string we fetch from our medication like 'Every day', 'Specific day, and etc
     * @param spinner
     */
    public void setSpinner(String s, Spinner spinner){
        for (int position = 0; position < spinner.getCount(); position++) {
            if(spinner.getItemAtPosition(position).toString().toLowerCase(Locale.ROOT).trim().equals(s.toLowerCase(Locale.ROOT).trim())) {
                spinner.setSelection(position);
                return;
            }
        }
    }

    /**
     * This function will open the android photo gallery.
     */
    private void startGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    /**
     *  This is the function when we select QR code image from our gallery,
     *  will decode image and give up the id that store in QR code.
     */
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {

        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            try {

                final Uri imageUri = data.getData();

                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);

                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                try {

                    Bitmap bMap = selectedImage;

                    resultQRScan = null;

                    int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];

                    bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

                    LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);

                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                    Reader reader = new MultiFormatReader();

                    Result result = reader.decode(bitmap);

                    resultQRScan = result.getText();

                    // we need to write medication id into sharedpreference to store in order to show in recycle view
                    // 1. we need to retrive the one have been store in sharedpreference first
                    SharedPreferences sharedPreferencesMedicationId = getActivity().getPreferences(Context.MODE_PRIVATE);
                    String[] medicationIdList = sharedPreferencesMedicationId.getString("MedicationIdStored", "").split(",");

                    ArrayList<String> medicationIdArrayList = new ArrayList<>();
                    if(!"".equals(medicationIdList[0])){
                        medicationIdArrayList = new ArrayList<>(Arrays.asList(medicationIdList));
                    }

                    // 2. after we retrive then we need to add new medicaion id we jsut scan
                    // check if already in the list if not append, otherwise skip
                    SharedPreferences.Editor sharedPreferencesMedicationIdEditor = getActivity().getPreferences(Context.MODE_PRIVATE).edit();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < medicationIdArrayList.size(); i++) {
                        if(resultQRScan.equals(medicationIdArrayList.get(i))){
//                            HomeFragment forwardHomefragment = new HomeFragment();
//                            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , forwardHomefragment).commit();
//                            Toast.makeText(getActivity(), "Already scan this QR code", Toast.LENGTH_LONG).show();
//                            return;
                        }
                    }
                    medicationIdArrayList.add(resultQRScan);
                    String covertArrayListtoString = TextUtils.join(", ", medicationIdArrayList);
                    sharedPreferencesMedicationIdEditor.putString("MedicationIdStored", covertArrayListtoString);
                    sharedPreferencesMedicationIdEditor.apply();
                    Log.wtf("nancy test medicationIdArrayList",String.valueOf(covertArrayListtoString));

                    Toast.makeText(getActivity(),resultQRScan,Toast.LENGTH_LONG).show();
                    writeIntoFeild();
                    Thread.sleep(2000);

                }catch (Exception e){

                    e.printStackTrace();

                }

            } catch (FileNotFoundException e) {

                e.printStackTrace();

                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();

            }

        }else {

            Toast.makeText(getActivity(), "You haven't picked Image",Toast.LENGTH_LONG).show();

        }

    }

    public void sendNotification(String title, String content, Bitmap medPic){

        Intent activityIntent = new Intent(getContext(), ConfirmActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, activityIntent, 0);

        Intent broadcastIntent = new Intent(getContext(), NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", "Medication is taken");
        broadcastIntent.putExtra("medicationId", medicationId);
        broadcastIntent.putExtra("medicationName", medicationName);
        broadcastIntent.putExtra("patientId", patientId);

        PendingIntent actionIntent = PendingIntent.getBroadcast(getContext(), 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(getContext(), NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(title)
                .setContentText(content)
                .setLargeIcon(medPic)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .build();
// .addAction(R.drawable.icon, "Confirm", actionIntent)
        notificationManagerCompat.notify(1, notification);
    }

}