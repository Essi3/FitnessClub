package com.example.fitnessclub;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class CourseListFragment extends Fragment {

    private CourseListAdapter courseListAdapter;
    private CourseViewModel courseViewModel;
    private Context context;

    public static CourseListFragment newInstance() {
        return new CourseListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        courseListAdapter = new CourseListAdapter(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_courses);
        recyclerView.setAdapter(courseListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        return view;
    }

    private void initData() {
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getCourseList().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable List<Course> courses) {
                courseListAdapter.setCourseList(courses);
            }
        });
    }

    public void removeData() {
        if (courseListAdapter != null) {
            courseViewModel.deleteAll();
        }
    }
}


