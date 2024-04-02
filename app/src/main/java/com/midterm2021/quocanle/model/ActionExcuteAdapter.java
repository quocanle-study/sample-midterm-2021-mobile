package com.midterm2021.quocanle.model;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.midterm2021.quocanle.HistoryActivity;
import com.midterm2021.quocanle.MainActivity;
import com.midterm2021.quocanle.R;
import com.midterm2021.quocanle.databaseUtil.AppDatabase;
import com.midterm2021.quocanle.databinding.ActionRowItemBinding;

import java.util.ArrayList;

public class ActionExcuteAdapter extends RecyclerView.Adapter<ActionExcuteAdapter.ViewHolder> {
    private ArrayList<ActionExcute> actionExcutes;
    private Context context;

    public ActionExcuteAdapter(ArrayList<ActionExcute> actionExcutes, Context context) {
        this.actionExcutes = actionExcutes;
        this.context = context;
    }

    public ActionExcuteAdapter(ArrayList<ActionExcute> actionExcutes) {
        this.actionExcutes = actionExcutes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.action_row_item, parent, false);
        ActionRowItemBinding binding = ActionRowItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvPosition.setText(String.valueOf(position + 1));
        holder.binding.setActionExcute(actionExcutes.get(position));

        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase.getInstance(context).actionDAO().delete(actionExcutes.get(holder.getAdapterPosition()));
                        actionExcutes.remove(holder.getAdapterPosition());
//                        actionExcutes.remove(position);
//                        notifyItemRemoved(position);
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                notifyItemRemoved(holder.getAdapterPosition());
                                HistoryActivity.getInstance().adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return actionExcutes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ActionRowItemBinding binding;
        public ViewHolder(ActionRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
