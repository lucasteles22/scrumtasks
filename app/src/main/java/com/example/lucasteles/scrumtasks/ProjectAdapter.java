package com.example.lucasteles.scrumtasks;

import android.content.Context;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;

public class ProjectAdapter extends ArrayAdapter<Project>{

    public ProjectAdapter(Context context, ArrayList<Project> projects) {
        super(context, 0, projects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Project project = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_project, parent, false);
        }
        // Lookup view for data population
        TextView projectName = (TextView) convertView.findViewById(R.id.item_project_name);

        // Populate the data into the template view using the data object

        projectName.setText(project.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}
