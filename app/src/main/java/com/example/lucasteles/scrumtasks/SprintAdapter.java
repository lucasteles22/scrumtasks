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

public class SprintAdapter extends ArrayAdapter<Sprint> {
    private Context context;
    private ArrayList<Sprint> sprints;

    public SprintAdapter(Context context, ArrayList<Sprint> sprints) {
        super(context, 0, sprints);
        this.context = context;
        this.sprints = sprints;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int auxPosition = position;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.item_sprint, null);

        TextView tv = (TextView) layout.findViewById(R.id.item_sprint_name);
        tv.setText(sprints.get(position).getName());

        final Sprint sprint = getItem(position);

        Button editBtn = (Button) layout.findViewById(R.id.btn_edit_sprint);
        editBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0){
                Intent intent = new Intent(context, NewSprintActivity.class);
                intent.putExtra("name", sprints.get(auxPosition).getName());
                intent.putExtra("id", sprints.get(auxPosition).getId());
                intent.putExtra("position", sprints.get(auxPosition).getPosition());
                intent.putExtra("project_id", sprints.get(auxPosition).getProjectId());
                context.startActivity(intent);
            }
        });

        Button deleteBtn = (Button) layout.findViewById(R.id.btn_delete_sprint);
        deleteBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0){
                SQLiteRepository repository = new SQLiteRepository(context);
                repository.sprintRepository().delete(sprints.get(auxPosition));
                sprints.remove(sprints.get(auxPosition));
                notifyDataSetChanged();
                Toast.makeText(context, "Sprint excluída com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });

        Button addTaskBtn = (Button) layout.findViewById(R.id.btn_list_tasks);
        addTaskBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TaskActivity.class);
                intent.putExtra("sprint_id", sprints.get(auxPosition).getId());
                intent.putExtra("project_id", sprints.get(auxPosition).getProjectId());
                context.startActivity(intent);
            }
        });
        return layout;
    }
}