package fingertiptech.medontime.ui.recycleadpoter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.model.Medication;

public class MedicationAdaptor extends RecyclerView.Adapter<MedicationAdaptor.MedicationViewHolder>  {

    List<Medication> medicationsList;
    Medication medications;
    Context context;

    public MedicationAdaptor(Context context, List<Medication> medicationsList){
        this.context = context;
        this.medicationsList = medicationsList;
    }
    public MedicationAdaptor(Context context, Medication medications){
        this.context = context;
        this.medications = medications;
    }
    @NonNull
    @NotNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_home , parent , false);
        return new MedicationViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MedicationAdaptor.MedicationViewHolder holder, int position) {
        Medication medicationItem = medicationsList.get(position);
//        Medication medicationItem = medications;

        holder.name.setText("Name : " + medicationItem.getMedicationName());
        holder.time.setText("Time : " + medicationItem.getFirstDoseTime());
        holder.dosage.setText("Dosage :" + medicationItem.getDosage());
        holder.quantity.setText("Quantity :" + medicationItem.getQuantity());
    }

    @Override
    public int getItemCount() {
        return medicationsList.size();
    }
//    @Override
//    public int getItemCount() {
//        return 1;
//    }

    public class MedicationViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name , time , dosage , quantity;
        public MedicationViewHolder(@NonNull View medicationItem){
            super(medicationItem);
            image = medicationItem.findViewById(R.id.image_view_medicine);
            name = medicationItem.findViewById(R.id.text_view_medicine_name);
            time = medicationItem.findViewById(R.id.text_view_time);
            dosage = medicationItem.findViewById(R.id.text_view_dosage);
            quantity = medicationItem.findViewById(R.id.text_view_quantity);
        }

    }
}
