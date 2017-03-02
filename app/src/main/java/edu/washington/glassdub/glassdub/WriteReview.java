package edu.washington.glassdub.glassdub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WriteReview extends Activity {
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        submit = (Button) findViewById(R.id.job_submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WriteReview.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
