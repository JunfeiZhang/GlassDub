package edu.washington.glassdub.glassdub;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kumulos.android.Kumulos;
import com.kumulos.android.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class JobPage extends AppCompatActivity {
    private Activity act = this;
    private ProgressBar progressBar;
    private LinearLayout jobLayout;
    private BottomNavigationView botNavigation;
    private TextView job, type,des;
    private String companyName;

    LinearLayout rating;
    ImageView logo;
    //TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_page);

        Intent intent = getIntent();
        final String jobID = intent.getStringExtra("jobID");
        companyName = intent.getStringExtra("companyName");
        String typeJob = intent.getStringExtra("type");
        String jobName = intent.getStringExtra("title");

        Map<String,String> jobParams = new HashMap<>();
        jobParams.put("job",jobID);

        job = (TextView) findViewById(R.id.JPjob);
        type = (TextView) findViewById(R.id.JPtype);
//        des = (TextView) findViewById(R.id.JPdescription);
        logo = (ImageView) findViewById(R.id.JPlogo);
        rating = (LinearLayout) findViewById(R.id.JPrating);

        jobLayout = (LinearLayout) findViewById(R.id.jobLayout);
        jobLayout.setVisibility(View.INVISIBLE);
        int shortAnimTime = getResources().getInteger(android.R.integer.config_longAnimTime);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.animate().setDuration(shortAnimTime).alpha(true ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                progressBar.setVisibility(GONE);
                jobLayout.setVisibility(VISIBLE);
            }
        });

        type.setText(typeJob);
        job.setText(companyName + " - " + jobName);
        updateRating(Integer.parseInt(intent.getStringExtra("rating")));
        //type.setText(intent.getStringExtra("type"));
        //description = (TextView) findViewById(R.id.JPdescription);

        Kumulos.call("getReviewsForJob", jobParams, new ResponseHandler() {
            @Override
            public void didCompleteWithResult(Object result) {
                if (result.toString().equals("32") || result.toString().equals("64") || result.toString().equals("128")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            act);
                    alertDialogBuilder
                            .setTitle("Error")
                            .setMessage("We were unable to retrieve information about the selected review. Try again.")
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
                    final ArrayList<LinkedHashMap<String, Object>> objects = (ArrayList<LinkedHashMap<String, Object>>) result;
                    // TODO: go through and update all the fields
                    if (objects.size() > 0) {
                        String[] jobReviews = new String[objects.size()];
                        int i =0;
                        for(LinkedHashMap object: objects){
                            jobReviews[i++] = object.get("title").toString();
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(act, android.R.layout.simple_list_item_1, jobReviews);
                        ListView companyReviewList = (ListView) findViewById(R.id.job_listview);
                        companyReviewList.setAdapter(adapter);


                        companyReviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Intent intent = new Intent(JobPage.this, ReviewPage.class);
                                intent.putExtra("reviewID", objects.get(position).get("job_reviewID").toString());
//                                intent.putExtra("reviewID",jobID);
                                startActivity(intent);

                            }
                        });
                    }
                }
            }
        });

        botNavigation = (BottomNavigationView) findViewById(R.id.bottomBar);
        botNavigation.getMenu().getItem(1).setChecked(true);
        botNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.jobItem) {
                    Intent intent = new Intent(JobPage.this, WriteReview.class);
                    intent.putExtra("company", companyName);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.homeItem) {
                    Intent intent = new Intent(JobPage.this, MainActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.interviewItem) {
                    Intent intent = new Intent(JobPage.this, WriteInterview.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
    private void updateRating(int count) {
        int[] stars = {R.id.star_1, R.id.star_2, R.id.star_3, R.id.star_4, R.id.star_5};

        for (int i = 0; i < count; i++) {
            ((ImageView) rating.findViewById(stars[i])).setImageResource(R.drawable.ic_star_gold_24dp);
        }
    }

}
