package com.example.planificationmobile2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

public class MyAdabterRecycleTask extends RecyclerView.Adapter<MyRecycleView> {

    List<Task> list;
    Context context;

    public MyAdabterRecycleTask(Context applicationContext, List<Task> list) {
        this.list = list;
        this.context = applicationContext;
    }


    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to 
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public MyRecycleView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyRecycleView(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tache, parent, false) );
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the 
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MyRecycleView holder, int position) {

        holder.taskName.setText(list.get(position).getTitle());
        holder.taskDescription.setText(list.get(position).getDescription());
        // get HH:mm
        holder.taskDate.setText(list.get(position).getDueDate().get(Calendar.HOUR_OF_DAY) + ":" +
                list.get(position).getDueDate().get(Calendar.MINUTE) + ":"
                + list.get(position).getDueDate().get(Calendar.SECOND));

    }

    /**
     * @return 
     */
    @Override
    public int getItemCount() {
        return list.size();
    }
}
