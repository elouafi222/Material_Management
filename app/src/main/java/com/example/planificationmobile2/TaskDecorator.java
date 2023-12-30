package com.example.planificationmobile2;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.google.android.material.datepicker.DayViewDecorator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public abstract class TaskDecorator extends DayViewDecorator {
   // private final List<Task> tasks;

    Calendar calendar = Calendar.getInstance();

/*
    public TaskDecorator(List<Task> tasks) {
        this.tasks = tasks;
    }


    public boolean shouldDecorate(CalendarDay day) {
        for (Task task : tasks) {
            if (task.dueDate.equals(day.getDate())) {
                return true;
            }
        }
        return false;
    }

    public void decorate(DayViewFacade view) {
        for (Task task : tasks) {
            if (task.dueDate.equals(view.getDate())) {
                int number = task.number;
                String title = task.title;
                int color = getColorForTask(task);

                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(String.valueOf(number)).append("\n");
                builder.append(title, new ForegroundColorSpan(color), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                view.setDaysDisabled(false);
                view.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                view.setSelectionDrawable(new ColorDrawable(color));
                view.setLabel(builder);
                break;
            }
        }
    }
*/
    private int getColorForTask(Task task) {
        // Determine the color based on the task's properties
        // For example:
        if (task.priority == TaskPriority.HIGH) {
            return Color.RED;
        } else if (task.priority == TaskPriority.MEDIUM) {
            return Color.YELLOW;
        } else {
            return Color.GREEN;
        }
    }

    public static class Task {
        public final Date dueDate;
        public final int number;
        public final String title;
        public final TaskPriority priority;

        public Task(Date dueDate, int number, String title, TaskPriority priority) {
            this.dueDate = dueDate;
            this.number = number;
            this.title = title;
            this.priority = priority;
        }
    }

    public enum TaskPriority {
        HIGH,
        MEDIUM,
        LOW
    }




}