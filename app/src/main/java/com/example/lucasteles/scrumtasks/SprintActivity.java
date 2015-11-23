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

public class SprintActivity extends AppCompatActivity {

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
                SQLiteRepository repository = new SQLiteRepository(this);
                ArrayList<Sprint> sprints = repository.sprintRepository().findByProject(bundle.getLong("project_id"));

                SprintAdapter adapter = new SprintAdapter(this, sprints);

                ListView listView = (ListView) findViewById(R.id.list_sprint);
                listView.setAdapter(adapter);
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
                Intent intentProject = new Intent(SprintActivity.this, NewSprintActivity.class);
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
        return true;
    }

}
