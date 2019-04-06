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

import static com.example.fitnessclub.CourseSaveDialogFragment.TAG_DIALOG_COURSE_SAVE;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Course> courseList;
    private Context context;

    public CourseListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
        notifyDataSetChanged();
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = layoutInflater.inflate(R.layout.item_list_course, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        if (courseList == null) {
            return;
        }

        final Course course  = courseList.get(position);
        if (course != null) {
            holder.courseTitleText.setText(course.title);

            final Trainer trainer = CourseAppDatabase.getDatabase(context).trainerDao().findTrainerById(course.trainerId);
            final String trainerFullName;
            if (trainer != null) {
                holder.trainerText.setText(trainer.fullName);
                trainerFullName = trainer.fullName;
            } else {
                trainerFullName = "";
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment dialogFragment = CourseSaveDialogFragment.newInstance(course.title, trainerFullName);
                    dialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), TAG_DIALOG_COURSE_SAVE);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (courseList == null) {
            return 0;
        } else {
            return courseList.size();
        }
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        private TextView courseTitleText;
        private TextView trainerText;

        public CourseViewHolder(View itemView) {
            super(itemView);

            courseTitleText = itemView.findViewById(R.id.tvCourseTitle);
            trainerText = itemView.findViewById(R.id.tvCourseTitleFullName);
        }
    }
}


