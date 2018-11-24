package com.example.navkaran.easyattendance;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AttendanceHistoryAdapter extends ArrayAdapter<Lecture> {
    private List<Lecture> LectureList;
    private DateFormat df = new SimpleDateFormat("EEE MMM dd");

    public AttendanceHistoryAdapter(Context context, int resource, List<Lecture> LectureList) {
        super(context, resource, LectureList);
        this.LectureList = LectureList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        // check to see if view is null. If it is, inflate it
        // inflating means to show the view
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.attendance_history_list_item, null);
        }

        Lecture i = LectureList.get(position);

        if (i != null) {
            TextView attenDate = v.findViewById(R.id.tv_attenDate);
            TextView attenCount = v.findViewById(R.id.tv_attenCount);
            TextView attenTimestamp = v.findViewById(R.id.tv_attenTimestamp);

            attenDate.setText(i.getDate().toString());
            attenCount.setText(String.valueOf(i.getNumAttendee()) + " Attendances");
            attenTimestamp.setText(df.format(i.getDate()));
        }
        return v;
    }

}
