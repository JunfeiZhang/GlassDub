package edu.washington.glassdub.glassdub;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.kumulos.android.*;

import java.util.*;
import java.util.zip.CheckedInputStream;

/**
 * TODO: Save all the data user inputted onto Kumulos server
 */

public class WriteReview extends Activity {
    private Button submit;
    final Context context = this;
    private String companyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        final EditText company = (EditText) findViewById(R.id.company);
        final EditText pjb = (EditText) findViewById(R.id.pjb);
        final EditText rating = (EditText) findViewById(R.id.rating);
        final EditText salary = (EditText) findViewById(R.id.salary);
        final EditText startDate = (EditText) findViewById(R.id.startDate);
        final EditText endDate = (EditText) findViewById(R.id.endDate);
        final EditText reviewTitle = (EditText) findViewById(R.id.title);
        final EditText reviewBody = (EditText) findViewById(R.id.body);
        final CheckBox anonymous = (CheckBox) findViewById(R.id.anonymous);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,String> companyParams = new HashMap<>();
                companyParams.put("name", company.getText().toString());

                //make request to get companyID based on input company
                Kumulos.call("getCompanyByName", companyParams, new ResponseHandler() {
                    @Override
                    public void didCompleteWithResult(Object result) {
                        ArrayList<LinkedHashMap<String, Object>> objects = (ArrayList<LinkedHashMap<String,Object>>) result;
                        if(objects.size() == 0) {
                            // tell them something went wrong
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    context);
                            alertDialogBuilder
                                    .setTitle("Incorrect Company")
                                    .setMessage("We don't have records for the company that you entered. If you want this company to be added to our system contact Dean at dean@ischool.edu.")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        } else {
                            Log.i("testing", objects.get(0).toString());
                            companyID = objects.get(0).get("companyID").toString();

                            Map<String, String> reviewParams = new HashMap<>();
                            reviewParams.put("companyID", companyID);
                            reviewParams.put("position", pjb.getText().toString());
                            reviewParams.put("rating", rating.getText().toString());
                            reviewParams.put("pay_rate", salary.getText().toString());
                            reviewParams.put("start_date", startDate.getText().toString());
                            reviewParams.put("end_date", endDate.getText().toString());
                            reviewParams.put("title", reviewTitle.getText().toString());
                            reviewParams.put("body", reviewBody.getText().toString());
                            // TODO: Get this from the application object once we have implemented login
                            reviewParams.put("employee", "1");
                            if(anonymous.isChecked()) {
                                reviewParams.put("anonymous", "true");
                            } else {
                                reviewParams.put("anonymous", "false");
                            }

                            Kumulos.call("createJobReview", reviewParams, new ResponseHandler() {
                                @Override
                                public void didCompleteWithResult(Object result) {
                                    Log.i("testing", result.toString());
                                    // Do updates to UI/data models based on result
                                    if (result.toString().equals("32") || result.toString().equals("64") || result.toString().equals("128")) {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                context);
                                        alertDialogBuilder
                                                .setTitle("Error")
                                                .setMessage("There was an error when creating your review. Try again.")
                                                .setCancelable(false)
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();
                                    } else {
                                        Intent intent = new Intent(WriteReview.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}