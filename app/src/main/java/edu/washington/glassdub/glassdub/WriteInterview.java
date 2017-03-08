package edu.washington.glassdub.glassdub;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kumulos.android.Kumulos;
import com.kumulos.android.ResponseHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * TODO: Save username with the review
 */


public class WriteInterview extends AppCompatActivity {
    private static final String TAG = "WriteInterview";

    private Button submit;
    private BottomNavigationView botNavigation;
    final Context context = this;
    private String companyID;
    private String company;
    Calendar calendar;

    String[] offer_list = {"Yes", "No", "Pending"};

    CustomEditText company_view;
    CustomEditText job_view;
    EditText comments_view;
    TextView date_view;
    Spinner offer_view;
    CheckBox anonymous_view;
    String job;
    String date;
    String offer;
    String comments;
    String anonymous = "false";

    int difficulty = -1;
    int[] difficulty_ids = {R.id.difficulty_1, R.id.difficulty_2, R.id.difficulty_3,
            R.id.difficulty_4, R.id.difficulty_5};

    String experience = null;
    int[] experience_ids = {R.id.positive_button, R.id.neutral_button, R.id.negative_button};

    int grey;
    int purple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_interview);

        fillOfferList();

//        company_view = (EditText) findViewById(R.id.write_interview_company);
//        job_view = (EditText) findViewById(R.id.write_interview_job);

        grey =  ResourcesCompat.getColor(getResources(), R.color.mediumgrey, null);
        purple = ResourcesCompat.getColor(getResources(), R.color.purple, null);

        submit = (Button) findViewById(R.id.submit_interview_button);
        submit.setOnClickListener(submitListener);

        for (int i = 0; i < difficulty_ids.length; i++) {
            ((Button) findViewById(difficulty_ids[i])).setOnClickListener(difficultyListener);
        }

        for (int i = 0; i < experience_ids.length; i++) {
            ((ImageButton) findViewById(experience_ids[i])).setOnClickListener(experienceListener);
        }

        Spinner offer_spinner = ((Spinner) findViewById(R.id.write_interview_offer));
        offer = offer_spinner.getItemAtPosition(0).toString();
        offer_spinner.setOnItemSelectedListener(spinnerListener);

        calendar = Calendar.getInstance();
        ((TextView) findViewById(R.id.write_interview_date)).setOnTouchListener(dateTouchListener);
        ((ImageButton) findViewById(R.id.write_interview_date_icon)).setOnTouchListener(dateTouchListener);

//        botNavigation = (BottomNavigationView) findViewById(R.id.bottomBar);
//        botNavigation.getMenu().getItem(2).setChecked(true);
//        botNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if(item.getItemId() == R.id.jobItem) {
//                    Intent intent = new Intent(WriteInterview.this, WriteReview.class);
//                    startActivity(intent);
//                } else if (item.getItemId() == R.id.homeItem) {
//                    Intent intent = new Intent(WriteInterview.this, MainActivity.class);
//                    startActivity(intent);
//                } else if (item.getItemId() == R.id.interviewItem) {
//                    Intent intent = new Intent(WriteInterview.this, WriteInterview.class);
//                    startActivity(intent);
//                }
//                return false;
//            }
//        });
    }

    private void fillOfferList() {
        List<String> spinnerArray =  new ArrayList<String>();

        for (int i = 0; i < offer_list.length; i++) {
            spinnerArray.add(offer_list[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.write_interview_offer)).setAdapter(adapter);
    }

    private View.OnClickListener submitListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            boolean submit = fetchData();

            if (submit) {
                Map<String, String> companyParams = new HashMap<>();
                companyParams.put("name", company_view.getText().toString());

                //make request to get companyID based on input company
                Kumulos.call("getCompanyByName", companyParams, new ResponseHandler() {
                    @Override
                    public void didCompleteWithResult(Object result) {
                        final ArrayList<LinkedHashMap<String, Object>> objects = (ArrayList<LinkedHashMap<String, Object>>) result;
                        if (objects.size() == 0) {
                            // tell them something went wrong
                            showAlert("Incorrect Company", "We don't have records for the company that you entered. If you want this company to be added to our system contact Dean at dean@ischool.edu.");
                        } else {
                            Map<String, String> checkJob = new HashMap<>();
                            checkJob.put("title", job);
                            Kumulos.call("getJobByName", checkJob, new ResponseHandler() {
                                @Override
                                public void didCompleteWithResult(Object result) {
                                    final ArrayList<LinkedHashMap<String, Object>> objects = (ArrayList<LinkedHashMap<String, Object>>) result;
                                    if(objects.size() == 0){
                                        showAlert("Incorrect Job", "We don't have records for the job that you entered. If you want this job to be added to our system contact Dean at dean@ischool.edu.");
                                    } else{
                                        Log.i("testing", objects.get(0).toString());
                                        companyID = objects.get(0).get("companyID").toString();
                                        GlassDub app = (GlassDub) getApplication();

                                        Map<String, String> reviewParams = new HashMap<>();
                                        reviewParams.put("companyID", companyID);
                                        reviewParams.put("job", job);
                                        reviewParams.put("received_offer", offer);
                                        reviewParams.put("interview_rating", experience);
                                        reviewParams.put("difficulty", Integer.toString(difficulty));
                                        reviewParams.put("title", "fake title");
                                        reviewParams.put("body", comments);
                                        // TODO: Get this from the application object once we have implemented login
                                        reviewParams.put("interviewee", app.getUsernumber());
                                        reviewParams.put("anonymous", anonymous);

                                        Kumulos.call("createInterviewReview", reviewParams, new ResponseHandler() {
                                            @Override
                                            public void didCompleteWithResult(Object result) {
                                                Log.i("testing", result.toString());
                                                // Do updates to UI/data models based on result
                                                if (result.toString().equals("32") || result.toString().equals("64") || result.toString().equals("128")) {
                                                    showAlert("Error", "There was an error when creating your review. Try again.");
                                                } else {
                                                    Intent intent = new Intent(WriteInterview.this, MainActivity.class);
                                                    startActivity(intent);

                                                    Map<String, String> updateParams = new HashMap<>();

                                                    String jobResult =(String) objects.get(0).get("companyID");
                                                    Log.d(TAG, "first object:" + objects.get(0).toString());
                                                    //Log.d(TAG, jobResult.get(0).toString());
                                    /*updateParams.put("jobID", jobResult.get(0).toString());
                                    updateParams.put("rating", rating);

                                    Kumulos.call("updateRating", updateParams, new ResponseHandler() {
                                        @Override
                                        public void didCompleteWithResult(Object result) {
                                            ArrayList<LinkedHashMap<String, Object>> updateResult = (ArrayList<LinkedHashMap<String, Object>>) result;
                                            if (updateResult.size() != 0) {
                                                Log.d(TAG, "updated rating");
                                            }
                                        }
                                    });*/
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
    };

    private boolean fetchData() {

        company_view = ((CustomEditText) findViewById(R.id.write_interview_company));
        job_view = ((CustomEditText) findViewById(R.id.write_interview_job));
        date_view = ((TextView) findViewById(R.id.write_interview_date));
        offer_view = ((Spinner) findViewById(R.id.write_interview_offer));
        comments_view = ((EditText) findViewById(R.id.write_interview_body));
        anonymous_view = (CheckBox) findViewById(R.id.anonymous);

        company = company_view.getText().toString();
        job = job_view.getText().toString();
        comments = comments_view.getText().toString();

        boolean submit = true;

        // check if values are valid - prevent submission if not
        if (offer_view.getSelectedView() != null) {
            offer = offer_view.getSelectedView().toString();
        } else {
            ((TextView) offer_view.getSelectedView()).setError("");
            submit = false;
        }
        if (company == null || company.trim().length() == 0) {
            company_view.setError("");
            submit = false;
        }
        if (job == null || job.trim().length() == 0) {
            job_view.setError("");
            submit = false;
        }
        if (comments == null || comments.trim().length() == 0) {
            //((TextView) findViewById(R.id.write_interview_subOverall)).setError("");
            submit = false;
        }

        if (difficulty == -1) {
            ((TextView) findViewById(R.id.write_interview_difficulty_subtitle)).setError("");
            submit = false;
        }
        if (experience == null) {
            ((TextView) findViewById(R.id.write_interview_experience_title)).setError("");
            submit = false;
        }
        date = date_view.getText().toString();
        if (date.equals("MMMM DD, YYYY ")) {
            ((TextView) findViewById(R.id.write_interview_date_error)).setError("");
            submit = false;
            Log.d(TAG, "start date not filled");
        } else if (calendar.getTime().after(Calendar.getInstance().getTime())) {
            showAlert("Error", "Start date (" + date + ") has not occurred yet.");
        }

        if (submit) {
            Log.d(TAG, "fetchData:\n company:" + company + "\n job:" + job + "\n " + date +
                   "\n offer:" + offer + "\n comments:" + comments);
        }
        if (submit) {
            if (anonymous_view.isChecked()) {
                anonymous = "true";
            } else {
                anonymous = "false";
            }
            Log.d(TAG, "\n anonymous:" + anonymous);
        }
        return submit;
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            offer = parent.getItemAtPosition(position).toString();
            Log.d(TAG, "offer clicked: " + offer);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private View.OnTouchListener dateTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                new DatePickerDialog(WriteInterview.this, dateListener, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
            return true;
        }
    };

    private void updateLabel() {

        String myFormat = "MMMM dd, yyyy ";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ((TextView) findViewById(R.id.write_interview_date)).setText(sdf.format(calendar.getTime()));
    }

    private View.OnClickListener difficultyListener = new View.OnClickListener() {
        public void onClick(View v) {
            Button clicked = (Button) v;
            difficulty = Integer.parseInt(v.getTag().toString());
            clicked.setTextColor(purple);

            Log.d(TAG, "difficulty clicked: " + difficulty);

            int grey = ResourcesCompat.getColor(getResources(), R.color.mediumgrey, null);

            for (int i = 0; i < difficulty_ids.length; i++) {
                if (i != difficulty - 1) {
                    ((Button) findViewById(difficulty_ids[i])).setTextColor(grey);
                }
            }
        }
    };

    private View.OnClickListener experienceListener = new View.OnClickListener() {
        public void onClick(View v) {
            ImageButton clicked = (ImageButton) v;
            experience = v.getTag().toString();
            clicked.setColorFilter(purple);

            Log.d(TAG, "experience clicked: " + experience);

            for (int i = 0; i < experience_ids.length; i++) {
                ImageButton button = (ImageButton) findViewById(experience_ids[i]);
                if (!button.getTag().toString().equals(experience)) {
                    button.setColorFilter(grey);
                    Log.d(TAG, "changed " + button.getTag() + " to grey");
                }
            }
        }
    };
}