package akash.sarkar.clickquick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ColorsViewHolder> {

    Context context;
    List<ColorsModel> list;
    colorBlockClickListener callback;

    public ColorsAdapter(Context context, List<ColorsModel> list, colorBlockClickListener callback) {
        this.context = context;
        this.list = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ColorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_colors_layout, parent, false);
        return new ColorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorsViewHolder holder, int position) {
        ColorsModel data = list.get(position);

        holder.colorLayout.setBackgroundColor(context.getResources().getColor(data.colorId));
        holder.colorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.OnColorClicked(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<ColorsModel> newList) {
        list.clear();

        list.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ColorsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout colorLayout;
        public ColorsViewHolder(@NonNull View itemView) {
            super(itemView);
            colorLayout = itemView.findViewById(R.id.colorLayout);
        }
    }

    public interface colorBlockClickListener {
        void OnColorClicked(ColorsModel data);
    }
}
