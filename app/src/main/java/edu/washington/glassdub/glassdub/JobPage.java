package edu.washington.glassdub.glassdub;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kumulos.android.Kumulos;
import com.kumulos.android.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JobPage extends AppCompatActivity {
    private static final String TAG = "JobPage";

    private String[] jobReviews = new String[] {
            "Job Review 1", "Job Review 2", "Job Review 3"
    };

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Activity act = this;

    TextView title;
    TextView rating;
    ImageView logo;
    //TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_page);

        Intent intent = getIntent();
        String jobID = intent.getStringExtra("jobID");

        /*

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jobReviews);
        ListView companyReviewList = (ListView) findViewById(R.id.JPlistview);
        companyReviewList.setAdapter(adapter);

        companyReviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent(JobPage.this, ReviewPage.class);
                startActivity(intent);

            }
        });

        */
        // initialize with job data (title/rating/logo/description)



        mViewPager = (ViewPager) findViewById(R.id.job_container);

        Bundle b = new Bundle();
        b.putString("jobID", jobID);

        /*
        Fragment reviewList = new ReviewList();
        reviewList.setArguments(b);

        Fragment interviewList = new InterviewList();
        interviewList.setArguments(b);
        */

        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        vpAdapter.addFragments(new BlankFragment(), "Reviews");
        vpAdapter.addFragments(new BlankFragment(), "Interviews");
        mViewPager.setAdapter(vpAdapter);

        tabLayout = (TabLayout) findViewById(R.id.job_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        title = (TextView) findViewById(R.id.JPtitle);
        //description = (TextView) findViewById(R.id.JPdescription);
        logo = (ImageView) findViewById(R.id.JPlogo);
        rating = (TextView) findViewById(R.id.JPrating);

        Map<String,String> jobParams = new HashMap<>();
        jobParams.put("jobID", jobID);

        Kumulos.call("getJob", jobParams, new ResponseHandler() {
            @Override
            public void didCompleteWithResult(Object result) {
                if (result.toString().equals("32") || result.toString().equals("64") || result.toString().equals("128")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            act);
                    alertDialogBuilder
                            .setTitle("Error")
                            .setMessage("We We were unable to retrieve information about the selected company. Try again.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // TODO: send them back to list of companies
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    ArrayList<LinkedHashMap<String, Object>> objects = (ArrayList<LinkedHashMap<String,Object>>) result;
                    if (objects.size() > 0) {
                        LinkedHashMap<String, Object> object = objects.get(0);
                        Log.d(TAG, object.toString());
                        LinkedHashMap<String, Object> companyMap = (LinkedHashMap<String,Object>) object.get("company");
                        title.setText(companyMap.get("name").toString() + " - " +
                                object.get("title").toString());
                        /*description.setText(object.get("description").toString());
                        // TODO: Show rating with stars
                        //TODO: set image
                        rating.setText(object.get("rating").toString());*/
                        // TODO: Do image stuff here
                        //String imgUrl = object.get("logo_url").toString();

                    }
                }
            }
        });

    }



    // initialize with reviews
}

