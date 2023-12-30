package com.example.planificationmobile2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecycleView extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView taskName, taskDescription, taskDate;
    public MyRecycleView(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        taskName = itemView.findViewById(R.id.taskTitle);
        taskDescription = itemView.findViewById(R.id.taskDescription);
        taskDate = itemView.findViewById(R.id.taskDateTime);

    }
}
