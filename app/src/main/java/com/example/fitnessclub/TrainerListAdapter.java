package com.example.fitnessclub;


import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static com.example.fitnessclub.TrainerSaveDialogFragment.TAG_DIALOG_TRAINER_SAVE;

public class TrainerListAdapter extends RecyclerView.Adapter<TrainerListAdapter.TrainerViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Trainer> trainerList;
    private Context context;

    public TrainerListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setTrainerList(List<Trainer> trainerList) {
        this.trainerList = trainerList;
        notifyDataSetChanged();
    }

    @Override
    public TrainerListAdapter.TrainerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = layoutInflater.inflate(R.layout.item_list_trainer, parent, false);
        return new TrainerListAdapter.TrainerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrainerListAdapter.TrainerViewHolder holder, int position) {
        if (trainerList == null) {
            return;
        }

        final Trainer trainer = trainerList.get(position);
        if (trainer != null) {
            holder.trainerText.setText(trainer.fullName);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment dialogFragment = TrainerSaveDialogFragment.newInstance(trainer.fullName);
                    dialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), TAG_DIALOG_TRAINER_SAVE);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (trainerList == null) {
            return 0;
        } else {
            return trainerList.size();
        }
    }

    static class TrainerViewHolder extends RecyclerView.ViewHolder {
        private TextView trainerText;

        public TrainerViewHolder(View itemView) {
            super(itemView);
            trainerText = itemView.findViewById(R.id.tvTrainer);
        }
    }
}




