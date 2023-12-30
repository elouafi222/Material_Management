package com.example.planificationmobile2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Task implements Comparable<Task> {
    static private int  id;
    private String title;
    private String description;
    private Calendar dueDate;

    public Task(String s, String s1, Calendar date) {
        id = id + 1;
        this.title = s;
        this.description = s1;
        this.dueDate = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(this.dueDate.getTime());
        return this.getTitle() + "::" + this.getDescription() + "::" + formattedDate;
    }

    @Override
    public int compareTo(Task task) {
        return this.getDueDate().compareTo(task.getDueDate());
    }
}
