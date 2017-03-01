package edu.washington.glassdub.glassdub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kumulos.android.*;

import java.util.*;

public class WriteReview extends Activity {
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        final EditText company = (EditText) findViewById(R.id.company);
        final EditText pjb = (EditText) findViewById(R.id.pjb);
        final EditText rating = (EditText) findViewById(R.id.rating);
        final EditText salary = (EditText) findViewById(R.id.salary);
        final EditText startDate = (EditText) findViewById(R.id.startDate);
        final EditText endDate = (EditText) findViewById(R.id.endDate);
        final EditText reviewTitle = (EditText) findViewById(R.id.title);
        final EditText reviewBody = (EditText) findViewById(R.id.body);

        submit = (Button) findViewById(R.id.button9);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WriteReview.this, MainActivity.class);
                startActivity(intent);
                Map<String,String> params = new HashMap<>();

                Kumulos.call("createJobReview", params, new ResponseHandler() {
                    @Override
                    public void didCompleteWithResult(Object result) {
                        // Do updates to UI/data models based on result
                    }
                });



            }
        });
    }
}