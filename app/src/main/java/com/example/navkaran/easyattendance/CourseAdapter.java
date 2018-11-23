package com.example.navkaran.easyattendance;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

// David Cui B00788648 Nov 2018
// adapter for course item list in course list activity
public class CourseAdapter extends ArrayAdapter<CourseItem> {

    private List<CourseItem> courseList;

    public CourseAdapter(Context context, int textViewResourceId, List<CourseItem> courseList) {
        super(context, textViewResourceId, courseList);
        this.courseList = courseList;
    }

    public void setCourseList(List<CourseItem> courses) {
        courseList = courses;
        notifyDataSetChanged();
    }

    public List<CourseItem> getCourseList() {
        return courseList;
    }

    //display each view in the list of course items
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        //if view is null, inflate/show/render it
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.course_list_item, null);
        }

        //get course i from the courseList and set textviews of the list item view with values
        // of course
        CourseItem i = null;
        if(courseList != null) {
             i = courseList.get(position);
        }

        if (i != null) {
            TextView tvCourseId = v.findViewById(R.id.tvCourseId);
            TextView tvCourseName = v.findViewById(R.id.tvCourseName);
            TextView tvStudentCount = v.findViewById(R.id.tvStudentCount);

            tvCourseId.setText(i.getCourseID());
            tvCourseName.setText(i.getCourseName());
            tvStudentCount.setText(Integer.toString(i.getStudentCount()) + " Students");

            // alternate list item background color
            if(position % 2 == 0) {
                v.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
            } else {
                v.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorWhiteFaded));
            }
        }

        return v;
    }

    @Override
    public int getCount() {
        if (courseList != null)
            return courseList.size();
        else return 0;
    }

}