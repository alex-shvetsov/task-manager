package com.example.lab3.task;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public class Task implements Parcelable {

    private String name;
    private String comment;
    private Date date;
    private boolean done;

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public Task(String name, Date date, String comment, boolean done) {
        this.name = name;
        this.date = date;
        this.comment = comment;
        this.done = done;

    }

    public Task(Parcel in) {
        super();
        readFromParcel(in);
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public Date getDate() {
        return date;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeSerializable(date);
        dest.writeString(comment);
        dest.writeByte((byte) (done ? 1 : 0));
    }

    public void readFromParcel(Parcel in) {
        name = in.readString();
        date = (Date)in.readSerializable();
        comment = in.readString();
        done = in.readByte() != 0;
    }
}
