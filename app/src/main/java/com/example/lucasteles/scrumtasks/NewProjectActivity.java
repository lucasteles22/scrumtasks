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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewProjectActivity extends AppCompatActivity {
    private Project project = new Project();
    private EditText editTextProjectName;
    private Button createBtn;
    private Button editBtn;


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

        editTextProjectName = (EditText) findViewById(R.id.editText_name_project);
        createBtn = (Button) findViewById(R.id.btn_create_project);
        editBtn = (Button) findViewById(R.id.btn_edit_project);
        createBtn.setVisibility(View.VISIBLE);
        editBtn.setVisibility(View.GONE);

        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                project.setId(bundle.getLong("id"));
                project.setName(bundle.getString("name"));
                editTextProjectName.setText(project.getName());
                createBtn.setVisibility(View.GONE);
                editBtn.setVisibility(View.VISIBLE);
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
            case R.id.new_project:
                return true;
            case R.id.go_back:
                super.finish();
            case R.id.new_sprint:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem createNewProject = menu.findItem(R.id.new_project);
        createNewProject.setVisible(false);

        MenuItem newSprint = menu.findItem(R.id.new_sprint);
        newSprint.setVisible(false);
        return true;
    }

    public void saveNewProject(View view){
        String projectName = editTextProjectName.getText().toString();
        SQLiteRepository repository = new SQLiteRepository(this);

//      Verify if exists another project with same name
        if(!projectName.isEmpty() && repository.projectRepository().findByName(projectName).size() == 0){
            project.setName(projectName);
            repository.projectRepository().insert(project);

            Intent homepage = new Intent(this, MainActivity.class);
            startActivity(homepage);

            Toast.makeText(this, "Projeto inserido com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Já existe projeto com este nome", Toast.LENGTH_SHORT).show();
        }
    }

    public void editProject(View view){
        String projectName = editTextProjectName.getText().toString();
        SQLiteRepository repository = new SQLiteRepository(this);

        if(!projectName.isEmpty() && repository.projectRepository().findByName(projectName).size() == 0){
            project.setName(projectName);
            repository.projectRepository().update(project);

            Intent homepage = new Intent(this, MainActivity.class);
            startActivity(homepage);

            Toast.makeText(this, "Projeto atualizado com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Já existe projeto com este nome", Toast.LENGTH_SHORT).show();
        }
    }
}