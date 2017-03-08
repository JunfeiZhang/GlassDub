package edu.washington.glassdub.glassdub;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kumulos.android.Kumulos;
import com.kumulos.android.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JobPage extends AppCompatActivity {
    private Activity act = this;
    private TextView job, type,des;

    LinearLayout rating;
    ImageView logo;
    //TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_page);

        Intent intent = getIntent();
        final String jobID = intent.getStringExtra("jobID");
        String companyName = intent.getStringExtra("companyName");
        String typeJob = intent.getStringExtra("type");
        String jobName = intent.getStringExtra("title");

        Map<String,String> jobParams = new HashMap<>();
        jobParams.put("job",jobID);

        job = (TextView) findViewById(R.id.JPjob);
        type = (TextView) findViewById(R.id.JPtype);
        des = (TextView) findViewById(R.id.JPdescription);
        logo = (ImageView) findViewById(R.id.JPlogo);
        rating = (LinearLayout) findViewById(R.id.JPrating);

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
                        ListView companyReviewList = (ListView) findViewById(R.id.company_listview);
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
    }
    private void updateRating(int count) {
        int[] stars = {R.id.star_1, R.id.star_2, R.id.star_3, R.id.star_4, R.id.star_5};

        for (int i = 0; i < count; i++) {
            ((ImageView) rating.findViewById(stars[i])).setImageResource(R.drawable.ic_star_gold_24dp);
        }
    }




//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jobReviews);
//        ListView companyReviewList = (ListView) findViewById(R.id.company_listview);
//        companyReviewList.setAdapter(adapter);


}




