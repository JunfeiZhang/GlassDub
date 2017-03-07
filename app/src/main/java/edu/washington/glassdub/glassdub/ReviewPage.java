package edu.washington.glassdub.glassdub;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kumulos.android.Kumulos;
import com.kumulos.android.ResponseHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class ReviewPage extends AppCompatActivity {
    private Activity act = this;
    private TextView title, salary, position, start, end, review, created;
    private String anonymous;
    private LinearLayout rating;
    private static final String TAG = "ReviewPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);

        title = (TextView) findViewById(R.id.CRtitle);
        rating = (LinearLayout) findViewById(R.id.CRrating);
        salary = (TextView) findViewById(R.id.CRsalary);
        position = (TextView) findViewById(R.id.CRposition);
        start = (TextView) findViewById(R.id.CRstart);
        end = (TextView) findViewById(R.id.CRend);
        review = (TextView) findViewById(R.id.CRbody);
        anonymous = "";
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
                        Log.d(TAG, object.toString());
                        title.setText(object.get("title").toString());
                        review.setText(object.get("body").toString());
                        setRating(Integer.parseInt(object.get("rating").toString()));
                        salary.setText(object.get("pay_rate").toString());
                        position.setText(object.get("job").toString());
                        start.setText(object.get("start_date").toString());
                        // TODO: dont show this if the user didnt enter it
                        end.setText(object.get("end_date").toString());
                        // TODO: if it is anonymous just print anonymous, otherwise print username
                        //anonymous.setText(object.get("anonymous").toString());
                        created.setText(formatDate(object.get("timeCreated").toString()));
                    }
                }
            }
        });
    }

    private void setRating(int count) {
        int[] stars = {R.id.star_1, R.id.star_2, R.id.star_3, R.id.star_4, R.id.star_5};
        for (int i = 0; i < count; i++) {
            ((ImageView) rating.findViewById(stars[i])).setImageResource(R.drawable.ic_star_gold_24dp);
        }
    }

    private String formatDate(String original) {
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

        Date date = null;
        String result = "";

        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(original);
            result = format.format(date);
        } catch (ParseException e) {
            e.getStackTrace();
        }
        Log.d(TAG, "original: " + original + " new: " + result);
        return result;
    }
}
