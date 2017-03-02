package edu.washington.glassdub.glassdub;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class WriteInterview extends AppCompatActivity {
    private Button submit;

    private static final String TAG = "WriteInterview";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_interview);

        submit = (Button) findViewById(R.id.interview_submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WriteInterview.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupDropdowns() {
        Log.d(TAG, "setupDropdowns");
        Spinner dropdown = (Spinner)findViewById(R.id.month_dropdown);
        String[] items = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug",
                "Sept", "Oct", "Nov", "Dec"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

}
