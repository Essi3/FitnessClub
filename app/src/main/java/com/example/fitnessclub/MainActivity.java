package com.example.fitnessclub;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import static com.example.fitnessclub.CourseSaveDialogFragment.TAG_DIALOG_COURSE_SAVE;
import static com.example.fitnessclub.TrainerSaveDialogFragment.TAG_DIALOG_TRAINER_SAVE;

public class MainActivity extends AppCompatActivity {

    private boolean COURSES_SHOWN = true;
    private Fragment shownFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar(getString(R.string.app_name));
        initView();

        if (savedInstanceState == null) {
            showFragment(CourseListFragment.newInstance());
        }
    }

    public void setToolbar(@NonNull String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

    private void initView() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_courses:
                        COURSES_SHOWN = true;
                        showFragment(CourseListFragment.newInstance());
                        return true;
                    case R.id.navigation_trainers:
                        COURSES_SHOWN = false;
                        showFragment(TrainerListFragment.newInstance());
                        return true;
                }
                return false;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSaveDialog();
            }
        });
    }

    private void showFragment(final Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentHolder, fragment);
        fragmentTransaction.commitNow();
        shownFragment = fragment;
    }

    private void showSaveDialog() {
        DialogFragment dialogFragment;
        String tag;
        if (COURSES_SHOWN) {
            dialogFragment = CourseSaveDialogFragment.newInstance(null, null);
            tag = TAG_DIALOG_COURSE_SAVE;
        } else {
            dialogFragment = TrainerSaveDialogFragment.newInstance(null);
            tag = TAG_DIALOG_TRAINER_SAVE;
        }

        dialogFragment.show(getSupportFragmentManager(), tag);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.action_delete_list_data) {
            deleteCurrentListData();
            return true;
        } else if (id == R.id.action_re_create_database) {
            reCreateDatabase();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteCurrentListData() {
        if (COURSES_SHOWN) {
            ((CourseListFragment) shownFragment).removeData();
        } else {
            ((TrainerListFragment) shownFragment).removeData();
        }
    }

    private void reCreateDatabase() {
        CourseAppDatabase.getDatabase(this).clearDb();
    }
}
