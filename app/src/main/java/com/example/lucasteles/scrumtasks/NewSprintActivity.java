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

public class NewSprintActivity extends AppCompatActivity {
    private Sprint sprint = new Sprint();
    private EditText editTextSprintName;
    private EditText editTextSprintPosition;
    private EditText editTextSprintProjectId;
    private Button createBtn;
    private Button editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sprint);
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

        editTextSprintName = (EditText) findViewById(R.id.editText_name_sprint);
        editTextSprintPosition = (EditText) findViewById(R.id.editText_position_sprint);
        editTextSprintProjectId = (EditText) findViewById(R.id.editText_project_id_sprint);
        createBtn = (Button) findViewById(R.id.btn_create_sprint);
        editBtn = (Button) findViewById(R.id.btn_edit_sprint);

        createBtn.setVisibility(View.VISIBLE);
        editBtn.setVisibility(View.GONE);

        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null && bundle.containsKey("id") || bundle.containsKey("name") || bundle.containsKey("position") && bundle.containsKey("project_id")){
                sprint.setId(bundle.getLong("id"));
                sprint.setName(bundle.getString("name"));
                editTextSprintName.setText(sprint.getName());

                sprint.setPosition(bundle.getInt("position"));
                editTextSprintPosition.setText(String.valueOf(sprint.getPosition()));

                sprint.setProjectId(bundle.getLong("project_id"));
                editTextSprintProjectId.setText(String.valueOf(sprint.getProjectId()));

                createBtn.setVisibility(View.GONE);
                editBtn.setVisibility(View.VISIBLE);
            } else if(bundle.containsKey("project_id")){
                sprint.setProjectId(bundle.getLong("project_id"));
                editTextSprintProjectId.setText(String.valueOf(sprint.getProjectId()));
            } else {
                //Throw exception
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem createNewProject = menu.findItem(R.id.new_project);
        createNewProject.setVisible(false);

        MenuItem newTask = menu.findItem(R.id.new_task);
        newTask.setVisible(false);
        return true;
    }

    public void saveNewSprint(View view){
        String name = editTextSprintName.getText().toString();
        int position = Integer.parseInt(editTextSprintPosition.getText().toString());
        long projectId = Long.parseLong(editTextSprintProjectId.getText().toString());
        SQLiteRepository repository = new SQLiteRepository(this);

//      Verify if exists another sprint with same name
        if(!name.isEmpty() && position != 0){
            sprint.setName(name);
            sprint.setPosition(position);
            sprint.setProjectId(projectId);
            repository.sprintRepository().insert(sprint);

            Intent sprintList = new Intent(this, SprintActivity.class);
            sprintList.putExtra("project_id", sprint.getProjectId());

            startActivity(sprintList);

            Toast.makeText(this, "Sprint inserido com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Já existe projeto com este nome", Toast.LENGTH_SHORT).show();
        }
        repository.close();
    }

    public void editSprint(View view){
        String name = editTextSprintName.getText().toString();
        int position = Integer.parseInt(editTextSprintPosition.getText().toString());
        long projectId = Long.parseLong(editTextSprintProjectId.getText().toString());

        SQLiteRepository repository = new SQLiteRepository(this);

        if(!name.isEmpty()  && position != 0){
            sprint.setName(name);
            sprint.setPosition(position);
            sprint.setProjectId(projectId);

            repository.sprintRepository().update(sprint);
            Intent sprintList = new Intent(this, SprintActivity.class);
            sprintList.putExtra("project_id", sprint.getProjectId());

            startActivity(sprintList);

            Toast.makeText(this, "Projeto atualizado com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Já existe projeto com este nome", Toast.LENGTH_SHORT).show();
        }

        repository.close();
    }
}
