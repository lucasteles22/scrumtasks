package com.example.lucasteles.scrumtasks;

import android.app.TimePickerDialog;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewTaskActivity extends AppCompatActivity {
    final SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");
    private Task task = new Task();
    private Button btnSetTimePlanned;
    private Button btnSetTimeSpent;
    private EditText editTextNameTask;
    private CheckBox checkBoxFinishedTask;
    private TextView textViewTimePlanned;
    private TextView textViewTimeSpent;
    private EditText editTextSprintId;
    private LinearLayout linearLayoutContainerTimeSpent;
    private Button createBtn;
    private Button editBtn;



    // Replace with KK:mma if you want 0-11 interval
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
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

        startActivityStructures();
        defineObjectsShouldBeDisplayedOnCreate();
        addListenerOnButtonSetTimePlanned();
        addListenerOnButtonSetTimeSpent();

        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null && bundle.containsKey("id") && bundle.containsKey("name") && bundle.containsKey("finished")
                    && bundle.containsKey("expectedTime")  && bundle.containsKey("timeSpent")  && bundle.containsKey("sprint_id") ) {

                fillTaskWithBundle(bundle);
                fillActivityStructuresWithTask();
                defineObjectsShouldBeDisplayedOnEdit();

            } else if(bundle.containsKey("sprint_id")){
                task.setSprintId(bundle.getLong("sprint_id"));
                editTextSprintId.setText(String.valueOf(task.getSprintId()));
            } else {
                //Throw exception
            }
        }
    }

    private void startActivityStructures() {
        editTextNameTask = (EditText) findViewById(R.id.editText_name_task);
        editTextSprintId = (EditText) findViewById(R.id.editText_sprint_id_task);
        textViewTimePlanned = (TextView) findViewById(R.id.text_view_time_planned);
        textViewTimeSpent = (TextView) findViewById(R.id.text_view_time_spent);
        checkBoxFinishedTask = (CheckBox) findViewById(R.id.checkbox_status_task);
        createBtn = (Button) findViewById(R.id.btn_create_task);
        editBtn = (Button) findViewById(R.id.btn_edit_task);
        linearLayoutContainerTimeSpent = (LinearLayout) findViewById(R.id.container_time_spent);
    }

    private void defineObjectsShouldBeDisplayedOnCreate() {
        createBtn.setVisibility(View.VISIBLE);
        editBtn.setVisibility(View.GONE);
        linearLayoutContainerTimeSpent.setVisibility(View.GONE);
        checkBoxFinishedTask.setVisibility(View.GONE);
    }

    private void defineObjectsShouldBeDisplayedOnEdit() {
        linearLayoutContainerTimeSpent.setVisibility(View.VISIBLE);
        createBtn.setVisibility(View.GONE);
        editBtn.setVisibility(View.VISIBLE);
        checkBoxFinishedTask.setVisibility(View.VISIBLE);
    }

    private void fillActivityStructuresWithTask() {
        editTextNameTask.setText(task.getName());
        editTextSprintId.setText(String.valueOf(task.getSprintId()));
        textViewTimePlanned.setText(String.format("%02d:%02d", getTimeFromTask(task.getExpectedTime(), Calendar.HOUR_OF_DAY), getTimeFromTask(task.getExpectedTime(), Calendar.MINUTE)));
        textViewTimeSpent.setText(String.format("%02d:%02d", getTimeFromTask(task.getTimeSpent(), Calendar.HOUR_OF_DAY), getTimeFromTask(task.getTimeSpent(), Calendar.MINUTE)));
        checkBoxFinishedTask.setChecked(task.getFinished());
    }

    private void fillTaskWithBundle(Bundle bundle) {
        task.setId(bundle.getLong("id"));
        task.setName(bundle.getString("name"));
        task.setFinished(bundle.getBoolean("finished"));
        task.setExpectedTime((Timestamp) bundle.get("expectedTime"));
        task.setTimeSpent((Timestamp) bundle.get("timeSpent"));
        task.setSprintId(bundle.getLong("sprint_id"));
    }

    public void saveNewTask(View view) {
        String name = editTextNameTask.getText().toString();
        long sprintId = Long.parseLong(editTextSprintId.getText().toString());
        Timestamp timeSpent = convertTimePickerToTimestamp(textViewTimeSpent.getText().toString());
        Timestamp expectedTime = convertTimePickerToTimestamp(textViewTimePlanned.getText().toString());
        boolean finished = false;
        SQLiteRepository repository = new SQLiteRepository(this);

//      Verify if exists another sprint with same name
        if(!name.isEmpty()){
            task.setName(name);
            task.setSprintId(sprintId);
            task.setTimeSpent(timeSpent);
            task.setExpectedTime(expectedTime);
            task.setFinished(finished);

            repository.taskRepository().insert(task);

            Intent taskList = new Intent(this, TaskActivity.class);
            taskList.putExtra("sprint_id", task.getSprintId());

            startActivity(taskList);

            Toast.makeText(this, "Task inserido com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nome da tarefa não pode ser vazio!", Toast.LENGTH_SHORT).show();
        }
        repository.close();
    }

    public void editTask(View view) {
        String name = editTextNameTask.getText().toString();
        long sprintId = Long.parseLong(editTextSprintId.getText().toString());
        Timestamp timeSpent = convertTimePickerToTimestamp(textViewTimeSpent.getText().toString());
        Timestamp expectedTime = convertTimePickerToTimestamp(textViewTimePlanned.getText().toString());
        boolean finished = checkBoxFinishedTask.isChecked();
        SQLiteRepository repository = new SQLiteRepository(this);

//      Verify if exists another sprint with same name
        if(!name.isEmpty()){
            task.setName(name);
            task.setSprintId(sprintId);
            task.setTimeSpent(timeSpent);
            task.setExpectedTime(expectedTime);
            task.setFinished(finished);

            repository.taskRepository().update(task);

            Intent taskList = new Intent(this, TaskActivity.class);
            taskList.putExtra("sprint_id", task.getSprintId());

            startActivity(taskList);

            Toast.makeText(this, "Task atualizada com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nome da tarefa não pode ser vazia!", Toast.LENGTH_SHORT).show();
        }
        repository.close();
    }

    public void addListenerOnButtonSetTimePlanned() {
        btnSetTimePlanned = (Button) findViewById(R.id.set_time_planned);
        btnSetTimePlanned.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewTimePlanned = (TextView) findViewById(R.id.text_view_time_planned);
                String[] splitTime = textViewTimePlanned.getText().toString().split(":");
                int hour, minute;

                try {
                    hour = Integer.parseInt(splitTime[0]);
                    minute = Integer.parseInt(splitTime[1]);
                } catch (Exception e) {
                    hour = 0;
                    minute = 0;
                }

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        textViewTimePlanned.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Ajustar tempo planejado");
                mTimePicker.show();
            }
        });
    }

    public void addListenerOnButtonSetTimeSpent() {
        btnSetTimeSpent = (Button) findViewById(R.id.set_time_spent);
        btnSetTimeSpent.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewTimeSpent = (TextView) findViewById(R.id.text_view_time_spent);
                String[] splitTime = textViewTimeSpent.getText().toString().split(":");
                int hour, minute;

                try {
                    hour = Integer.parseInt(splitTime[0]);
                    minute = Integer.parseInt(splitTime[1]);
                } catch (Exception e) {
                    hour = 0;
                    minute = 0;
                }

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        textViewTimeSpent.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Ajustar tempo gasto");
                mTimePicker.show();
            }
        });
    }

    private Timestamp convertTimePickerToTimestamp(String time){
        try {
            String[] portionOfTime = time.split(":");
            Calendar calendar=Calendar.getInstance();
            calendar.set(Calendar.HOUR, Integer.parseInt(portionOfTime[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(portionOfTime[1]));
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);

            java.util.Date now = calendar.getTime();
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
            return currentTimestamp;
        } catch (Exception e) {
            return null;
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

        MenuItem newSprint = menu.findItem(R.id.new_sprint);
        newSprint.setVisible(false);
        return true;
    }
}
