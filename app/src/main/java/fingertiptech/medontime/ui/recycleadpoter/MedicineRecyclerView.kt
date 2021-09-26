package fingertiptech.medontime.ui.recycleadpoter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fingertiptech.medontime.databinding.MedicineItemBinding
import fingertiptech.medontime.ui.model.Medication

class MedicineRecyclerView (private val medicationList: List<Medication>)
    : RecyclerView.Adapter<MedicineRecyclerView.myViewHolder>(){

    class myViewHolder(val binding : MedicineItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(
            MedicineItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
//        var current = medicationList[position]
//
//        holder.binding.imageView.setImageResource(current.imageResource)
//        holder.binding.textView1.text = current.productName
//        holder.binding.textView2.text = "\$CAD. " + current.productPrice.toString()
//        holder.binding.textView3.text = current.productNumber.toString()
//        holder.binding.textView4.text = current.productWeight.toString() + " lb"
//
//        holder.binding.layout.setOnClickListener(View.OnClickListener {
//            val totalPrice : Double = current.productPrice * current.productNumber
//            Toast.makeText(holder.binding.layout.context, "Total price of ${current.productName}: $totalPrice", Toast.LENGTH_SHORT).show()
//        })
    }

    override fun getItemCount() = medicationList.size


}