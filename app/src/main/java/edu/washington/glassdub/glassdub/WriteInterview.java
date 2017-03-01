package edu.washington.glassdub.glassdub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class WriteInterview extends AppCompatActivity {
    private Button submit;

    private static final String TAG = "WriteInterview";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_interview);

        Log.d(TAG, "onCreate");

        setupDropdowns();
        submit = (Button) findViewById(R.id.button3);
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
