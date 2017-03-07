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
    private TextView company, type,des;

//    private String[] jobReviews = new String[] {
//            "Job Review 1", "Job Review 2", "Job Review 3"
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_page);

        Intent intent = getIntent();
        final String jobID = intent.getStringExtra("jobID");
        String companyName = intent.getStringExtra("companyName");

        Map<String,String> jobParams = new HashMap<>();
        jobParams.put("job",jobID);

        company = (TextView) findViewById(R.id.company);
        type = (TextView) findViewById(R.id.type);
        des = (TextView) findViewById(R.id.des);

        company.setText(companyName);
        type.setText(intent.getStringExtra("type"));


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



//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jobReviews);
//        ListView companyReviewList = (ListView) findViewById(R.id.company_listview);
//        companyReviewList.setAdapter(adapter);


    }




}
