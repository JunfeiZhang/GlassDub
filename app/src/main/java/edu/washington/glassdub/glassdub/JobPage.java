package edu.washington.glassdub.glassdub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class JobPage extends AppCompatActivity {
    private String[] jobReviews = new String[] {
            "Job Review 1", "Job Review 2", "Job Review 3"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_page);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jobReviews);
        ListView companyReviewList = (ListView) findViewById(R.id.listView4);
        companyReviewList.setAdapter(adapter);

        companyReviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent(JobPage.this, ReviewPage.class);
                startActivity(intent);

            }
        });
    }
}
