package com.example.fitnessclub;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
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

public class TrainerListFragment extends Fragment {

    private TrainerListAdapter trainerListAdapter;
    private TrainerViewModel trainerViewModel;
    private Context context;

    public static TrainerListFragment newInstance() {
        return new TrainerListFragment();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        trainerListAdapter = new TrainerListAdapter(context);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_trainers);
        recyclerView.setAdapter(trainerListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }
    private void initData() {
        trainerViewModel = ViewModelProviders.of(this).get(TrainerViewModel.class);
        trainerViewModel.getTrainersList().observe(this, new Observer<List<Trainer>>() {
            @Override
            public void onChanged(@Nullable List<Trainer> trainers) {
                trainerListAdapter.setTrainerList(trainers);
            }
        });
    }
    public void removeData() {
        if (trainerViewModel != null) {
            trainerViewModel.deleteAll();
        }
    }
}