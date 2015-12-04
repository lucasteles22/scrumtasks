package com.example.lucasteles.scrumtasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
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

        Intent itWithExtras = getIntent();
        if(itWithExtras != null){
            Bundle bundle = itWithExtras.getExtras();
            if(bundle != null){

                SQLiteRepository repository = new SQLiteRepository(this);
                ArrayList<Task> tasks = repository.taskRepository().findBySprint(bundle.getLong("sprint_id"));

                TaskAdapter adapter = new TaskAdapter(this, tasks);

                ListView listView = (ListView) findViewById(R.id.list_task);
                listView.setAdapter(adapter);

                repository.close();
            }
        }
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
                Intent sprintActivity = new Intent(TaskActivity.this, SprintActivity.class);
                Intent itProjectWithExtras = getIntent();
                if(itProjectWithExtras != null){
                    Bundle bundle = itProjectWithExtras.getExtras();
                    if(bundle != null){
                        sprintActivity.putExtra("project_id", bundle.getLong("project_id"));
                    }
                }
                startActivity(sprintActivity);
                super.finish();
                return true;

            case R.id.new_task:
                Intent intentNewTask = new Intent(TaskActivity.this, NewTaskActivity.class);
                Intent itWithExtras = getIntent();
                if(itWithExtras != null){
                    Bundle bundle = itWithExtras.getExtras();
                    if(bundle != null){
                        intentNewTask.putExtra("sprint_id", bundle.getLong("sprint_id"));
                    }
                }
                startActivity(intentNewTask);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem goBack = menu.findItem(R.id.new_project);
        goBack.setVisible(false);

        MenuItem newSprint = menu.findItem(R.id.new_sprint);
        newSprint.setVisible(false);
        return true;
    }
}
