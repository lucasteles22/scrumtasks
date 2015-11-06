package com.example.lucasteles.scrumtasks;

import android.content.Context;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.Toast;

public class ProjectAdapter extends ArrayAdapter<Project>{
    private Context context;
    private ArrayList<Project> projects;

    public ProjectAdapter(Context context, ArrayList<Project> projects) {
        super(context, 0, projects);
        this.context = context;
        this.projects = projects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int auxPosition = position;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.item_project, null);

        TextView tv = (TextView) layout.findViewById(R.id.item_project_name);
        tv.setText(projects.get(position).getName());

        final Project project = getItem(position);

        TextView projectName = (TextView) layout.findViewById(R.id.item_project_name);
        projectName.setText(project.getName());

        Button editBtn = (Button) layout.findViewById(R.id.btn_edit_project);
        editBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0){
                Toast.makeText(context, "Projeto inserido com sucesso!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, EditProjectActivity.class);
//                intent.putExtra("nome", projects.get(auxPosition).getName());
//                intent.putExtra("id", projects.get(auxPosition).getId());
//                context.startActivity(intent);
            }
        });

        Button deleteBtn = (Button) layout.findViewById(R.id.btn_delete_project);
        deleteBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0){
                DataBase dataBase = new DataBase(context);
                dataBase.delete(projects.get(auxPosition));
                projects.remove(projects.get(auxPosition));
                notifyDataSetChanged();
                Toast.makeText(context, "Projeto excluído sucesso!", Toast.LENGTH_SHORT).show();
            }
        });
        return layout;
    }
}
