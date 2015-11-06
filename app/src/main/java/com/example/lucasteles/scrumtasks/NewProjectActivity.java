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
import android.widget.EditText;
import android.widget.Toast;

public class NewProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
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
                return true;
            case R.id.go_back:
                super.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem createNewProject = menu.findItem(R.id.new_project);
        createNewProject.setVisible(false);
        return true;
    }

    public void saveNewProject(View view){
        EditText editText = (EditText) findViewById(R.id.editText_name_project);
        String projectName = editText.getText().toString();
        DataBase db = new DataBase(this);

//      Verify if exists another project with same name
        if(db.findByName(projectName).size() == 0){
            Project project = new Project();
            project.setName(projectName);
            db.insert(project);

            Intent homepage = new Intent(this, MainActivity.class);
            startActivity(homepage);

            Toast.makeText(this, "Projeto inserido com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Já existe projeto com este nome", Toast.LENGTH_SHORT).show();
        }
    }
}
