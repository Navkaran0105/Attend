package com.example.navkaran.easyattendance;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AttendanceDetailsActivity extends AppCompatActivity{

    private ListView lvAttendanceList;
    private Button btnDone;
    private TextView tvCourseId, tvCourseName, tvStudentCount, tvAttendanceSummary;

    private ArrayList<AttendanceItem> attendanceItemArrayList;
    private AttendanceAdapter attendanceAdapter;
    private Runnable runnable;

    private Intent intent;
    private String courseId,courseName;
    private int courseKey, studentCount, attendanceCount;

    private LectureRepository lectureRepository;
    private AttendanceItemRepository attendanceRepository;

    public static final String ATTENDANCE_COUNT = "ATTENDANCE_COUNT";

    private final String TAG = "AttendanceDetails";
    private final String FETCH_URL = "https://web.cs.dal.ca/~stang/csci5708/end_attendance.php?class_id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_attendance_list);

        intent = getIntent();
        courseKey = intent.getIntExtra(EasyAttendanceConstants.COURSE_KEY, -1);
        courseId = intent.getStringExtra(EasyAttendanceConstants.COURSE_ID);
        courseName = intent.getStringExtra(EasyAttendanceConstants.COURSE_NAME);
        //TODO: Change Default Values back to 0
        studentCount = intent.getIntExtra(EasyAttendanceConstants.COURSE_STUDENT_COUNT, 0);
        attendanceCount = intent.getIntExtra(ATTENDANCE_COUNT, 0);

        //TODO: Remove next 2 Temp Lines
        //courseId = courseId == null ? "CSCI-5708" : courseId;
        //courseName = courseName == null ? "Not Mobile Computing" : courseName;

        lvAttendanceList = findViewById(R.id.lvAttendanceList);
        btnDone = findViewById(R.id.btnDone);
        tvCourseId = findViewById(R.id.tvCourseId);
        tvCourseName = findViewById(R.id.tvCourseName);
        tvStudentCount = findViewById(R.id.tvStudentCount);
        tvAttendanceSummary = findViewById(R.id.tvAttendanceSummary);

        tvCourseId.setText(courseId);
        tvCourseName.setText(courseName);
        tvStudentCount.setText(String.format(getString(R.string.formatString_students_registered), studentCount));
        tvAttendanceSummary.setText(String.format(getString(R.string.formatString_in_attendance), attendanceCount, studentCount));

        attendanceItemArrayList = new ArrayList<>();
        attendanceAdapter = new AttendanceAdapter(this, R.layout.attendance_list_item, attendanceItemArrayList);
        lvAttendanceList.setAdapter(attendanceAdapter);

        lectureRepository = new LectureRepository(getApplication());
        attendanceRepository = new AttendanceItemRepository(getApplication());

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CourseListActivity.class);
                startActivity(i);
            }
        });

        runnable = new Runnable() {
            @Override
            public void run() {
                fetchAttendanceLog();
            }
        };

        new Thread(null, runnable, "Runnable_AttendanceDetails").start();

    }

    private void fetchAttendanceLog() {
        String url = FETCH_URL + courseId;
        Log.d(TAG, "Request URL: " + url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String studentId;

                for(int i = 0; i <response.length(); i++ ){
                    try {
                        studentId = response.getJSONObject(i).getString("student_id");
                        Log.d(TAG, "JSONResponse Loop: Student ID: " + studentId);
                        attendanceItemArrayList.add(new AttendanceItem(studentId, true));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                tvAttendanceSummary.setText(String.format(getString(R.string.formatString_in_attendance), attendanceCount, studentCount));
                attendanceAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "No Student attend this class", Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueueSingleton.getmInstance(getApplicationContext()).addToRequestQueue(request);
    }
}

