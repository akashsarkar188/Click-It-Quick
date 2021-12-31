package akash.sarkar.clickquick.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import akash.sarkar.clickquick.R;
import akash.sarkar.clickquick.models.LevelSelectorModel;

public class LevelSelectorAdapter extends RecyclerView.Adapter<LevelSelectorAdapter.ViewHolder> {
    private List<LevelSelectorModel> list;
    private Context context;

    public LevelSelectorAdapter(List<LevelSelectorModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_level_selector, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LevelSelectorModel model = list.get(position);
        holder.level.setText(String.valueOf(model.level));
        holder.reqPoints.setText(String.valueOf(model.required_points));
        if (model.is_locked) {
            holder.cardBg.setBackgroundColor(context.getResources().getColor(R.color.light_grey, null));
            holder.icLockedUnlocked.setImageResource(R.drawable.ic_level_lock);
        } else {
            holder.cardBg.setBackgroundColor(context.getResources().getColor(R.color.green, null));
            holder.icLockedUnlocked.setImageResource(R.drawable.ic_level_unlock);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout cardBg;
        TextView level, reqPoints;
        ImageView icLockedUnlocked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardBg = itemView.findViewById(R.id.layCardBg);
            level = itemView.findViewById(R.id.txtLevel);
            reqPoints = itemView.findViewById(R.id.txtReqPoints);
            icLockedUnlocked = itemView.findViewById(R.id.imgLockedUnlocked);
        }
    }
}
