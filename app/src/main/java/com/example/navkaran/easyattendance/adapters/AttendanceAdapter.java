package com.example.navkaran.easyattendance.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.navkaran.easyattendance.models.AttendanceItem;
import com.example.navkaran.easyattendance.R;

import java.util.ArrayList;

/**
 * @author Samson Maconi
 * Nov 2018
 * This class handles the display of the attendanceList
 * in the ListView.
 */
public class AttendanceAdapter extends ArrayAdapter<AttendanceItem> {

    private ArrayList<AttendanceItem> attendanceList;

    public AttendanceAdapter(Context context, int resource, ArrayList<AttendanceItem> attendanceList) {
        super(context, resource, attendanceList);
        this.attendanceList = attendanceList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        // check to see if view is null. If it is, inflate it
        // inflating means to show the view
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.attendance_list_item, null);
        }

        AttendanceItem i = attendanceList.get(position);

        if (i != null) {
            TextView tvStudentId = v.findViewById(R.id.tvStudentId);
            TextView tvStatus = v.findViewById(R.id.tvStatus);
            TextView tvSN = v.findViewById(R.id.tvSN);

            tvStudentId.setText(i.getStudentId());
            tvStatus.setText(i.getStatus());
            tvSN.setText(String.valueOf(position + 1));

            if (i.hasCheckedIn()) {
                tvStatus.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGreen));
            } else
                tvStatus.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
        }

        return v;
    }

}
