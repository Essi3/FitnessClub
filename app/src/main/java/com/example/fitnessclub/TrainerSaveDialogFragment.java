package com.example.fitnessclub;

import android.support.v4.app.DialogFragment;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class TrainerSaveDialogFragment extends DialogFragment {

    private Context context;
    private String trainerFullNameExtra;

    private static final String EXTRA_TRAINER_FULL_NAME = "trainer_full_name";
    public static final String TAG_DIALOG_TRAINER_SAVE = "dialog_trainer_save";

    public static TrainerSaveDialogFragment newInstance(String trainerFullName) {
        TrainerSaveDialogFragment fragment = new TrainerSaveDialogFragment();

        Bundle args = new Bundle();
        args.putString(EXTRA_TRAINER_FULL_NAME, trainerFullName);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        trainerFullNameExtra = args.getString(EXTRA_TRAINER_FULL_NAME);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_trainer, null);
        final EditText trainerEditText = view.findViewById(R.id.etCourseTrainerFullName);
        if (trainerFullNameExtra != null) {
            trainerEditText.setText(trainerFullNameExtra);
            trainerEditText.setSelection(trainerFullNameExtra.length());
        }

        alertDialogBuilder.setView(view)
                .setTitle(getString(R.string.dialog_trainer_title))
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveTrainer(trainerEditText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        return alertDialogBuilder.create();
    }

    private void saveTrainer(String fullName) {
        if (TextUtils.isEmpty(fullName)) {
            return;
        }

        TrainerDao trainerDao = CourseAppDatabase.getDatabase(context).trainerDao();

        if (trainerFullNameExtra != null) {
            // clicked on item row -> update
            Trainer trainerToUpdate = trainerDao.findTrainerByName(fullName);
            if (trainerToUpdate != null) {
                if (!trainerToUpdate.fullName.equals(fullName)) {
                    trainerToUpdate.fullName = fullName;
                    trainerDao.update(trainerToUpdate);
                }
            }
        } else {
            trainerDao.insert(new Trainer(fullName));
        }
    }
}

