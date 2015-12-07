package com.example.lucasteles.scrumtasks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskAdapter  extends ArrayAdapter<Task> {
    private Context context;
    private ArrayList<Task> tasks;

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int auxPosition = position;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.item_task, null);

        TextView tv = (TextView) layout.findViewById(R.id.item_task_name);
        tv.setText(tasks.get(position).getName());

        final Task task = getItem(position);

        Button editBtn = (Button) layout.findViewById(R.id.btn_edit_task);
        editBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, NewTaskActivity.class);
                intent.putExtra("id", tasks.get(auxPosition).getId());
                intent.putExtra("name", tasks.get(auxPosition).getName());
                intent.putExtra("finished", tasks.get(auxPosition).getFinished());
                intent.putExtra("expectedTime", tasks.get(auxPosition).getExpectedTime());
                intent.putExtra("timeSpent", tasks.get(auxPosition).getTimeSpent());
                intent.putExtra("sprint_id", tasks.get(auxPosition).getSprintId());
                context.startActivity(intent);
            }
        });

        Button deleteBtn = (Button) layout.findViewById(R.id.btn_delete_task);
        deleteBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SQLiteRepository repository = new SQLiteRepository(context);
                repository.taskRepository().delete(tasks.get(auxPosition));
                tasks.remove(tasks.get(auxPosition));
                notifyDataSetChanged();
                Toast.makeText(context, "Tarefa excluída com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });
        return layout;
    }
}