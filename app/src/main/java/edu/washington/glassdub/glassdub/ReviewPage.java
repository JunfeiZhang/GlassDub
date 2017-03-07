package edu.washington.glassdub.glassdub;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.kumulos.android.Kumulos;
import com.kumulos.android.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReviewPage extends AppCompatActivity {
    private Activity act = this;
    private TextView title, rating, salary, position, start, end, review, anonymous, created;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);

        title = (TextView) findViewById(R.id.CRtitle);
        rating = (TextView) findViewById(R.id.CRrating);
        salary = (TextView) findViewById(R.id.CRsalary);
        position = (TextView) findViewById(R.id.CRposition);
        start = (TextView) findViewById(R.id.CRstart);
        end = (TextView) findViewById(R.id.CRend);
        review = (TextView) findViewById(R.id.CRbody);
        anonymous = (TextView) findViewById(R.id.CRanonymous);
        created = (TextView) findViewById(R.id.CRcreated);

        // TODO: get info sent to fragment (comapny ID)
        Intent intent = getIntent();

        String companyRevID = intent.getStringExtra("reviewID");
        Map<String,String> revParams = new HashMap<>();
        revParams.put("job_reviewID", companyRevID);

        Kumulos.call("getCompanyReview", revParams, new ResponseHandler() {
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
                    ArrayList<LinkedHashMap<String, Object>> objects = (ArrayList<LinkedHashMap<String, Object>>) result;
                    // TODO: go through and update all the fields
                    if (objects.size() > 0) {
                        LinkedHashMap<String, Object> object = objects.get(0);
                        title.setText(object.get("title").toString());
                        review.setText(object.get("body").toString());
                        rating.setText(object.get("rating").toString());
                        salary.setText(object.get("pay_rate").toString());
                        position.setText(object.get("job").toString());
                        start.setText(object.get("start_date").toString());
                        // TODO: dont show this if the user didnt enter it
                        end.setText(object.get("end_date").toString());
                        // TODO: if it is anonymous just print anonymous, otherwise print username
                        anonymous.setText(object.get("anonymous").toString());
                        created.setText(object.get("timeCreated").toString());
                    }
                }
            }
        });
    }
}
