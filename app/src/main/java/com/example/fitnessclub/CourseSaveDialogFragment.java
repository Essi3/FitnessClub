package com.example.fitnessclub;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class CourseSaveDialogFragment extends DialogFragment {

    private Context context;
    private String courseTitleExtra;
    private String courseTrainerFullNameExtra;

    private static final String EXTRA_COURSE_TITLE = "course_title";
    private static final String EXTRA_COURSE_TRAINER_FULL_NAME = "course_trainer_full_name";
    public static final String TAG_DIALOG_COURSE_SAVE = "dialog_course_save";

    public static CourseSaveDialogFragment newInstance(final String courseTitle, final String courseTrainerFullName) {
        CourseSaveDialogFragment fragment = new CourseSaveDialogFragment();

        Bundle args = new Bundle();
        args.putString(EXTRA_COURSE_TITLE, courseTitle);
        args.putString(EXTRA_COURSE_TRAINER_FULL_NAME, courseTrainerFullName);
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
        courseTitleExtra = args.getString(EXTRA_COURSE_TITLE);
        courseTrainerFullNameExtra = args.getString(EXTRA_COURSE_TRAINER_FULL_NAME);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_course, null);
        final EditText courseEditText = view.findViewById(R.id.etCourseTitle);
        final EditText courseTrainerEditText = view.findViewById(R.id.etCourseTrainerFullName);
        if (courseTitleExtra != null) {
            courseEditText.setText(courseTitleExtra);
            courseEditText.setSelection(courseTitleExtra.length());
        }
        if (courseTrainerFullNameExtra != null) {
            courseTrainerEditText.setText(courseTrainerFullNameExtra);
            courseTrainerEditText.setSelection(courseTrainerFullNameExtra.length());
        }

        alertDialogBuilder.setView(view)
                .setTitle(getString(R.string.dialog_course_title))
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveCourse(courseEditText.getText().toString(), courseTrainerEditText.getText().toString());
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

    private void saveCourse(String courseTitle, String courseTrainerFullName) {
        if (TextUtils.isEmpty(courseTitle) || TextUtils.isEmpty(courseTrainerFullName)) {
            return;
        }

        TrainerDao trainerDao = CourseAppDatabase.getDatabase(context).trainerDao();
        CourseDao courseDao = CourseAppDatabase.getDatabase(context).courseDao();

        int trainerId = -1;
        if (courseTrainerFullNameExtra!= null) {
            // clicked on item row -> update
            Trainer trainerToUpdate = trainerDao.findTrainerByName(courseTrainerFullNameExtra);
            if (trainerToUpdate != null) {
                trainerId = trainerToUpdate.id;

                if (!trainerToUpdate.fullName.equals(courseTrainerFullName)) {
                    trainerToUpdate.fullName = courseTrainerFullName;
                    trainerDao.update(trainerToUpdate);
                }
            }
        } else {
            // we need trainer id for course object; in case trainer is already in DB,
            // insert() would return -1, so we manually check if it exists and get
            // the id of already saved trainer
            Trainer newTrainer = trainerDao.findTrainerByName(courseTrainerFullName);
            if (newTrainer == null) {
                trainerId = (int) trainerDao.insert(new Trainer(courseTrainerFullName));
            } else {
                trainerId = newTrainer.id;
            }
        }

        if (courseTitleExtra != null) {
            // clicked on item row -> update
            Course courseToUpdate = courseDao.findCourseByTitle(courseTitleExtra);
            if (courseToUpdate != null) {
                if (!courseToUpdate.title.equals(courseTitle)) {
                    courseToUpdate.title = courseTitle;
                    if (trainerId != -1) {
                        courseToUpdate.trainerId = trainerId;
                    }
                    courseDao.update(courseToUpdate);
                }
            }
        } else {
            // we can have many courses with same title but different trainer
            Course newCourse = courseDao.findCourseByTitle(courseTitle);
            if (newCourse == null) {
                courseDao.insert(new Course(courseTitle, trainerId));
            } else {
                if (newCourse.trainerId != trainerId) {
                    newCourse.trainerId = trainerId;
                    courseDao.update(newCourse);
                }
            }
        }
    }
}
