package com.example.lucasteles.scrumtasks;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class NewTaskActivity extends AppCompatActivity {
    final SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private Task task = new Task();
    private Button btnSetTimePlannet;
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


        createBtn = (Button) findViewById(R.id.btn_create_task);
        editBtn = (Button) findViewById(R.id.btn_edit_task);

        createBtn.setVisibility(View.VISIBLE);
        editBtn.setVisibility(View.GONE);

        linearLayoutContainerTimeSpent = (LinearLayout) findViewById(R.id.container_time_spent);
        linearLayoutContainerTimeSpent.setVisibility(View.GONE);

        addListenerOnButtonSetTimePlanned();

        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null && bundle.containsKey("id") && bundle.containsKey("name") && bundle.containsKey("finished")
                    && bundle.containsKey("expectedTime")  && bundle.containsKey("timeSpent")  && bundle.containsKey("sprint_id") ) {

                task.setId(bundle.getLong("id"));
                task.setName(bundle.getString("name"));
                task.setFinished(bundle.getBoolean("finished"));
                task.setExpectedTime(Timestamp.valueOf(bundle.getString("expectedTime")));
                task.setTimeSpent(Timestamp.valueOf(bundle.getString("timeSpent")));
                task.setSprintId(bundle.getLong("sprint_id"));

//                sprint.setId(bundle.getLong("id"));
//                sprint.setName(bundle.getString("name"));
//                editTextSprintName.setText(sprint.getName());
//
//                sprint.setPosition(bundle.getInt("position"));
//                editTextSprintPosition.setText(String.valueOf(sprint.getPosition()));
//
//                sprint.setProjectId(bundle.getLong("project_id"));
//                editTextSprintProjectId.setText(String.valueOf(sprint.getProjectId()));
                linearLayoutContainerTimeSpent.setVisibility(View.VISIBLE);
                createBtn.setVisibility(View.GONE);
                editBtn.setVisibility(View.VISIBLE);
            } else if(bundle.containsKey("project_id")){
//                sprint.setProjectId(bundle.getLong("project_id"));
//                editTextSprintProjectId.setText(String.valueOf(sprint.getProjectId()));
            } else {
                //Throw exception
            }
        }
    }

    public void addListenerOnButtonSetTimePlanned() {
        btnSetTimePlannet = (Button) findViewById(R.id.set_time_planned);
        btnSetTimePlannet.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewTimePlanned = (TextView) findViewById(R.id.text_view_time_planned);
                String[] splitTime = textViewTimePlanned.getText().toString().split(":");
                int hour, minute;

                try {
                    hour = Integer.parseInt(splitTime[0]);
                    minute = Integer.parseInt(splitTime[1]);
                }
                catch(Exception e) {
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
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

}
