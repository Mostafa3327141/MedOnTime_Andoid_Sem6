package fingertiptech.medontime.ui.recycleadpoter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fingertiptech.medontime.R;
import fingertiptech.medontime.ui.model.TestItem;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {


    List<TestItem> itemList;
    Context context;

    public ItemAdapter(Context context, List<TestItem> itemList){
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.test_item , parent , false);
        return new ItemViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemViewHolder holder, int position) {
        TestItem testItem = itemList.get(position);
        holder.id.setText("id : " + testItem.getId());
        holder.userId.setText("userId : " + testItem.getUserId());
        holder.title.setText("title :" + testItem.getTitle());
        holder.body.setText("body :" + testItem.getBody());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView title , id , userId , body;
        public ItemViewHolder(@NonNull View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.title_tv);
            id = itemView.findViewById(R.id.id_tv);
            userId = itemView.findViewById(R.id.user_id_tv);
            body = itemView.findViewById(R.id.body_tv);
        }

    }
}
