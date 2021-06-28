package huji.postpc.y2021.giladtal.roots;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{


    private Holder local_holder;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calculate,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Calculation cur_calculation = local_holder.getCurrentItems().get(position);
        holder.num.setText(String.valueOf(cur_calculation.rootToCal));
        if (cur_calculation.getState().equals("Done"))
        {
            if (cur_calculation.isPrime){
                holder.root1.setText("is prime");
                holder.root2.setVisibility(View.GONE);
            }
            else {
                holder.root1.setText(String.valueOf(cur_calculation.root1));
                holder.root2.setText(String.valueOf(cur_calculation.root2));
                holder.root2.setVisibility(View.VISIBLE);
            }
            holder.progressBar.setVisibility(View.GONE);
        }
        else
        {
            holder.progressBar.setProgress(cur_calculation.progress);
            holder.progressBar.setVisibility(View.VISIBLE);
        }
        holder.deleteButton.setOnClickListener(v -> {
            local_holder.deleteItem(cur_calculation);
            this.notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return local_holder.getCurrentItems().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        Button deleteButton;
        TextView root1;
        TextView root2;
        TextView num;
        ProgressBar progressBar;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            root1 = view.findViewById(R.id.root1);
            root2 = view.findViewById(R.id.root2);
            deleteButton = view.findViewById(R.id.deletebootn);
            deleteButton.setVisibility(View.VISIBLE);
            num = view.findViewById(R.id.num);
            progressBar = view.findViewById(R.id.progress);


        }
    }
    Adapter(Holder input){
        local_holder =input;
    }
    public Holder getLocal_holder() {
        return local_holder;
    }

    public void setLocal_holder(Holder input) {
        this.local_holder = input;
    }
}
