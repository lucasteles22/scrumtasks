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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SprintActivity extends AppCompatActivity {
    private LinearLayout containerHasNotSprint;
    private LinearLayout containerSummary;
    private TextView textViewSummarySprints;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint);
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
                containerHasNotSprint = (LinearLayout) findViewById(R.id.container_has_not_sprint);
                containerSummary = (LinearLayout) findViewById(R.id.container_summary_sprint);
                textViewSummarySprints = (TextView) findViewById(R.id.text_summary_sprints);

                SQLiteRepository repository = new SQLiteRepository(this);
                ArrayList<Sprint> sprints = repository.sprintRepository().findByProject(bundle.getLong("project_id"));

                if(sprints.size() > 0) {
                    containerHasNotSprint.setVisibility(View.GONE);
                    setContainerSummary(repository, bundle.getLong("project_id"));
                } else {
                    containerSummary.setVisibility(View.GONE);
                }

                SprintAdapter adapter = new SprintAdapter(this, sprints);

                ListView listView = (ListView) findViewById(R.id.list_sprint);
                listView.setAdapter(adapter);
            }
        }
    }

    private void setContainerSummary(SQLiteRepository repository, long project_id) {
        project = repository.projectRepository().findById(project_id);
        textViewSummarySprints.setText("Projeto: " + project.getName());
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
                Intent intentProject = new Intent(SprintActivity.this, MainActivity.class);
                startActivity(intentProject);
                super.finish();
                return true;

            case R.id.new_sprint:
                Intent intentNewSprint = new Intent(SprintActivity.this, NewSprintActivity.class);
                Intent itWithExtras = getIntent();
                if(itWithExtras != null){
                    Bundle bundle = itWithExtras.getExtras();
                    if(bundle != null){
                        intentNewSprint.putExtra("project_id", bundle.getLong("project_id"));
                    }
                }
                startActivity(intentNewSprint);
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
        return true;
    }
}
