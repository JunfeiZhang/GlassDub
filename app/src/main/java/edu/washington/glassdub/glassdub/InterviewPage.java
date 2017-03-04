package edu.washington.glassdub.glassdub;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.kumulos.android.Kumulos;
import com.kumulos.android.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class InterviewPage extends AppCompatActivity {
    private TextView position, type, experience, difficulty, offer, body, user, created;
    private Activity act = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_page);

        position = (TextView) findViewById(R.id.Iposition);
        type = (TextView) findViewById(R.id.Itype);
        experience = (TextView) findViewById(R.id.Iexperience);
        difficulty = (TextView) findViewById(R.id.Idifficulty);
        offer = (TextView) findViewById(R.id.Ioffer);
        body = (TextView) findViewById(R.id.Ibody);
        user = (TextView) findViewById(R.id.Iuser);
        created = (TextView) findViewById(R.id.Icreated);

        // TODO: get info sent to fragment (comapny ID)
        int interviewID = 2; // getArguments().getInt("interviewID");

        Map<String,String> interviewParams = new HashMap<>();
        interviewParams.put("interview_reviewID", String.valueOf(interviewID));

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
                        position.setText(object.get("position").toString());
                        type.setText(object.get("type").toString());
                        experience.setText(object.get("experience").toString());
                        difficulty.setText(object.get("difficulty").toString());
                        offer.setText(object.get("received_offer").toString());
                        body.setText(object.get("body").toString());
                        user.setText(object.get("user").toString());
                        created.setText(object.get("timeCreated").toString());
                    }
                }
            }
        });
    }
}
