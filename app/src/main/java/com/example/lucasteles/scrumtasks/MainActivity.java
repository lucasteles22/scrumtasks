package com.example.lucasteles.scrumtasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SQLiteRepository repository = new SQLiteRepository(this);
        ArrayList<Project> projects = repository.projectRepository().findAll();

        ProjectAdapter adapter = new ProjectAdapter(this, projects);

        ListView listView = (ListView) findViewById(R.id.list_project);
        listView.setAdapter(adapter);
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
            case R.id.new_project:
                Intent intent = new Intent(MainActivity.this, NewProjectActivity.class);
                startActivity(intent);
            case R.id.go_back:
                return true;
            case R.id.new_sprint:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem goBack = menu.findItem(R.id.go_back);
        goBack.setVisible(false);

        MenuItem newSprint = menu.findItem(R.id.new_sprint);
        newSprint.setVisible(false);

        MenuItem newTask = menu.findItem(R.id.new_task);
        newTask.setVisible(false);
        return true;
    }

}
