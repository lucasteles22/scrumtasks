package com.example.lucasteles.scrumtasks;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class TaskManageActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        chronometer = (Chronometer) findViewById(R.id.chronometer_task);

        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null && bundle.containsKey("id")) {
                SQLiteRepository repository = new SQLiteRepository(this);
                task = repository.taskRepository().findById(bundle.getLong("id"));
                int hour = getTimeFromTask(task.getTimeSpent(), Calendar.HOUR) * 60 * 60 * 1000;
                int minute = getTimeFromTask(task.getTimeSpent(), Calendar.MINUTE) * 60 * 1000;
                int second = getTimeFromTask(task.getTimeSpent(), Calendar.SECOND) * 1000;
                chronometer.setBase(SystemClock.elapsedRealtime() - (hour + minute + second));
                repository.close();
            }
        }
    }

    private int getTimeFromTask(Timestamp t, int unit) {
        Date date = new Date(t.getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(unit);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.go_back:
                Intent taskActivity = new Intent(TaskManageActivity.this, TaskActivity.class);
                Intent itProjectWithExtras = getIntent();
                if(itProjectWithExtras != null){
                    Bundle bundle = itProjectWithExtras.getExtras();
                    if(bundle != null){
                        taskActivity.putExtra("sprint_id", bundle.getLong("sprint_id"));

                        long projectId = bundle.getLong("project_id");
                        if(projectId == 0L) {
                            SQLiteRepository repository = new SQLiteRepository(this);
                            Sprint sprint = repository.sprintRepository().findById(bundle.getLong("sprint_id"));
                            projectId = sprint.getProjectId();
                        }

                        taskActivity.putExtra("project_id", projectId);
                    }
                }
                startActivity(taskActivity);
                super.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem goBack = menu.findItem(R.id.new_project);
        goBack.setVisible(false);

        MenuItem newTask = menu.findItem(R.id.new_task);
        newTask.setVisible(false);

        MenuItem newSprint = menu.findItem(R.id.new_sprint);
        newSprint.setVisible(false);
        return true;
    }

    public void startChronometer(View view) {
        chronometer.start();
    }

    public void stopChronometer(View view) {
        chronometer.stop();
        SQLiteRepository repository = new SQLiteRepository(this);

        int hours = 0, minutes = 0, seconds = 0;
        String time = chronometer.getText().toString();
        String[] partsOfTime = time.split(":");

        hours = Integer.parseInt(partsOfTime[0]);
        minutes = Integer.parseInt(partsOfTime[1]);
        if(partsOfTime.length == 3) {
            seconds = Integer.parseInt(partsOfTime[2]);
        }

        Date now = getDateFromTime(hours, minutes, seconds);
        java.sql.Timestamp timeSpent = new java.sql.Timestamp(now.getTime());
        task.setTimeSpent(timeSpent);

        repository.taskRepository().update(task);
        repository.close();
    }

    private java.util.Date getDateFromTime(int hours, int minutes, int seconds){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}