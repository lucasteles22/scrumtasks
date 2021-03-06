package com.example.lucasteles.scrumtasks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {
    private LinearLayout containerHasNotTask;
    private LinearLayout containerSummary;
    private TextView textViewSummaryTasks;
    private Sprint sprint;
    private Project project;
    private int[] colors = new int[] { Color.parseColor("#F0F0F0"), Color.parseColor("#D2E4FC") };
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
                containerHasNotTask = (LinearLayout) findViewById(R.id.container_has_not_task);
                containerSummary = (LinearLayout) findViewById(R.id.container_summary_tasks);
                textViewSummaryTasks = (TextView) findViewById(R.id.text_summary_tasks);

                SQLiteRepository repository = new SQLiteRepository(this);
                ArrayList<Task> tasks = repository.taskRepository().findBySprint(bundle.getLong("sprint_id"));

                if(tasks.size() > 0) {
                    containerHasNotTask.setVisibility(View.GONE);
                    setContainerSummary(repository, bundle.getLong("sprint_id"));
                } else {
                    containerSummary.setVisibility(View.GONE);
                }

                TaskAdapter adapter = new TaskAdapter(this, tasks);

                ListView listView = (ListView) findViewById(R.id.list_task);
                listView.setAdapter(adapter);

                itWithExtras.putExtra("project_id", bundle.getString("project_id"));

                repository.close();
            }
        }
    }

    private void setContainerSummary(SQLiteRepository repository, long sprint_id) {
        sprint = repository.sprintRepository().findById(sprint_id);
        project = repository.projectRepository().findById(sprint.getProjectId());
        textViewSummaryTasks.setText("Projeto: " + project.getName() + " | Sprint: " + sprint.getName());
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
                        long projectId = bundle.getLong("project_id");
                        if(projectId == 0L) {
                            SQLiteRepository repository = new SQLiteRepository(this);
                            Sprint sprint = repository.sprintRepository().findById(bundle.getLong("sprint_id"));
                            projectId = sprint.getProjectId();
                        }
                        sprintActivity.putExtra("project_id", projectId);
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
