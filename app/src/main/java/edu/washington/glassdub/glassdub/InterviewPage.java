package edu.washington.glassdub.glassdub;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import static android.view.View.VISIBLE;

public class InterviewPage extends AppCompatActivity {
    private static final String TAG = "InterviewPage";

    private TextView position, title, type, experience, difficulty, offer, body, user, created;
    private Activity act = this;
    private ProgressBar progressBar;
    private LinearLayout interviewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_page);

        position = (TextView) findViewById(R.id.Iposition);
        title = (TextView) findViewById(R.id.Ititle);
        //type = (TextView) findViewById(R.id.Itype);
        experience = (TextView) findViewById(R.id.Iexperience);
        difficulty = (TextView) findViewById(R.id.Idifficulty);
        offer = (TextView) findViewById(R.id.Ioffer);
        body = (TextView) findViewById(R.id.Ibody);
        user = (TextView) findViewById(R.id.Iuser);
        created = (TextView) findViewById(R.id.Icreated);


        interviewLayout = (LinearLayout) findViewById(R.id.interviewLayout);
        interviewLayout.setVisibility(View.INVISIBLE);
        int shortAnimTime = getResources().getInteger(android.R.integer.config_longAnimTime);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(VISIBLE);
        progressBar.animate().setDuration(shortAnimTime).alpha(true ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                interviewLayout.setVisibility(VISIBLE);
                super.onAnimationEnd(animation);
            }
        });

        // TODO: get info sent to fragment (comapny ID)
        //int interviewID = 2; // getArguments().getInt("interviewID");
        Intent intent = getIntent();
        String interviewID = intent.getStringExtra("interviewID");
        Map<String,String> interviewParams = new HashMap<>();
        interviewParams.put("interview_reviewID", interviewID);

        Kumulos.call("getInterview", interviewParams, new ResponseHandler() {
            @Override
            public void didCompleteWithResult(Object result) {
                if (result.toString().equals("32") || result.toString().equals("64") || result.toString().equals("128")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            act);
                    alertDialogBuilder
                            .setTitle("Error")
                            .setMessage("We were unable to retrieve information about the selected company. Try again.")
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
                        position.setText(object.get("job").toString());
                        title.setText(object.get("title").toString());
                        //type.setText(object.get("type").toString());
                        experience.setText(object.get("interview_rating").toString());
                        difficulty.setText(object.get("difficulty").toString());
                        offer.setText(object.get("received_offer").toString());
                        body.setText(object.get("body").toString());
                        user.setText(object.get("interviewee").toString());
                        created.setText(formatDate(object.get("timeCreated").toString()));

                    }
                }
            }
        });
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
