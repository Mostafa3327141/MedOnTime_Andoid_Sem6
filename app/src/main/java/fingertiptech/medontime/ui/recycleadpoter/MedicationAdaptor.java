package fingertiptech.medontime.ui.recycleadpoter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.home.HomeFragmentDirections;
import fingertiptech.medontime.ui.imageConvert.ImageBase64Convert;
import fingertiptech.medontime.ui.medicationDetail.MedicationDetailedFragment;
import fingertiptech.medontime.ui.medicine.MedicineFragment;
import fingertiptech.medontime.ui.medicine.MedicineFragmentStep2;
import fingertiptech.medontime.ui.medicine.MedicineFragmentStep3;
import fingertiptech.medontime.ui.model.Medication;

public class MedicationAdaptor extends RecyclerView.Adapter<MedicationAdaptor.MedicationViewHolder>  {

    List<Medication> medicationsList;
    Context context;

    public MedicationAdaptor(Context context, List<Medication> medicationsList){
        this.context = context;
        this.medicationsList = medicationsList;
    }

    @NonNull
    @NotNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.medicine_item , parent , false);
        return new MedicationViewHolder(view);    }

    /**
     *
     */
    @Override
    public void onBindViewHolder(@NonNull @NotNull MedicationAdaptor.MedicationViewHolder holder, int position) {
        Medication medicationItem = medicationsList.get(position);
        holder.image.setImageBitmap(ImageBase64Convert.convertBase64ToBitmap(medicationItem.getMedicationImage()));
        holder.name.setText("Name : " + medicationItem.getMedicationName());
        holder.time.setText("Time : " + medicationItem.getFirstDoseTime());
        holder.dosage.setText("Dosage :" + medicationItem.getUnit());
        holder.quantity.setText("Quantity :" + medicationItem.getQuantity());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicationDetailedFragment towardMedicationDetailedFragmentfragment = new MedicationDetailedFragment();
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment , towardMedicationDetailedFragmentfragment).addToBackStack(null).commit();
                SharedPreferences sharedPreferencesClickListMedicationId = ((AppCompatActivity)context).getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor sharedPreferencesClickListMedicationIdEditor = sharedPreferencesClickListMedicationId.edit();
                sharedPreferencesClickListMedicationIdEditor.putString("MedicaitonListClickId", medicationItem.getId());
                sharedPreferencesClickListMedicationIdEditor.apply();
            }
        });

    }

    @Override
    public int getItemCount() {
        return medicationsList.size();
    }

    /**
     * In this function, it will bind UI field id to our variable.
     */
    public class MedicationViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name , time , dosage , quantity;
        View layout;
        public MedicationViewHolder(@NonNull View medicationItem){
            super(medicationItem);
            image = medicationItem.findViewById(R.id.image_view_medicine);
            name = medicationItem.findViewById(R.id.textView_medication_name);
            time = medicationItem.findViewById(R.id.textView_Medication_time);
            dosage = medicationItem.findViewById(R.id.textView_Medication_dosage);
            quantity = medicationItem.findViewById(R.id.textView_Medication_quantity);
            layout = medicationItem.findViewById(R.id.layout);
        }

    }

}
