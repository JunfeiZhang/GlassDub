package edu.washington.glassdub.glassdub;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;

import com.kumulos.android.*;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Math.round;

/**
 * TODO: Save username with the review
 */

public class WriteReview extends Activity {
    private static final String TAG = "WriteReview";

    private Button submit;
    final Context context = this;
    private String companyID;
    private BottomNavigationView botNavigation;

    Calendar startCal;
    Calendar endCal;

    String company;
    String job;
    String salary;
    String start_date;
    String end_date;
    String review_title;
    String review_body;
    String anonymous = "false";
    String rating = "";

    int[] star_ids = new int[5];

    int lightgrey;
    int purple;

    EditText company_view;
    EditText job_view;
    LinearLayout rating_view;
    EditText salary_view;
    TextView start_date_view;
    TextView end_date_view;
    EditText review_title_view;
    EditText review_body_view;
    CheckBox anonymous_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        company_view = (EditText) findViewById(R.id.write_review_company);
        job_view = (EditText) findViewById(R.id.write_review_job);
        rating_view = (LinearLayout) findViewById(R.id.write_review_rating);
        salary_view = (EditText) findViewById(R.id.write_review_salary);
        start_date_view = (TextView) findViewById(R.id.write_review_start_date);
        end_date_view = (TextView) findViewById(R.id.write_review_end_date);
        review_title_view = (EditText) findViewById(R.id.write_review_title);
        review_body_view = (EditText) findViewById(R.id.write_review_body);
        anonymous_view = (CheckBox) findViewById(R.id.anonymous);


        startCal = Calendar.getInstance();
        endCal = Calendar.getInstance();

        ((TextView) findViewById(R.id.write_review_start_date)).setOnTouchListener(startDateTouchListener);
        ((TextView) findViewById(R.id.write_review_end_date)).setOnTouchListener(endDateTouchListener);
        ((ImageButton) findViewById(R.id.write_review_start_date_icon)).setOnTouchListener(startDateTouchListener);
        ((ImageButton) findViewById(R.id.write_review_end_date_icon)).setOnTouchListener(endDateTouchListener);


        lightgrey =  ResourcesCompat.getColor(getResources(), R.color.lightgrey, null);
        purple = ResourcesCompat.getColor(getResources(), R.color.purple, null);

        rating_view = (LinearLayout) findViewById(R.id.write_review_rating);
        int[] temp = {R.id.star_1, R.id.star_2, R.id.star_3, R.id.star_4, R.id.star_5};
        star_ids = temp;
        for (int i = 0; i < star_ids.length; i++) {
            rating_view.findViewById(star_ids[i]).setOnClickListener(ratingListener);
        }

        submit = (Button) findViewById(R.id.write_review_submit_button);
        submit.setOnClickListener(submitListener);

        botNavigation = (BottomNavigationView) findViewById(R.id.bottomBar);
        botNavigation.getMenu().getItem(0).setChecked(true);
        botNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.jobItem) {
                    Intent intent = new Intent(WriteReview.this, WriteReview.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.homeItem) {
                    Intent intent = new Intent(WriteReview.this, MainActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.interviewItem) {
                    Intent intent = new Intent(WriteReview.this, WriteInterview.class);
                    startActivity(intent);
                }
                return false;
            }
        });

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
                            Log.i("testing", objects.get(0).toString());
                            companyID = objects.get(0).get("companyID").toString();
                            GlassDub app = (GlassDub) getApplication();

                            Map<String, String> reviewParams = new HashMap<>();
                            reviewParams.put("companyID", companyID);
                            reviewParams.put("position", job);
                            reviewParams.put("rating", rating);
                            reviewParams.put("pay_rate", salary);
                            reviewParams.put("start_date", start_date);
                            reviewParams.put("end_date", end_date);
                            reviewParams.put("title", review_title);
                            reviewParams.put("body", review_body);
                            // TODO: Get this from the application object once we have implemented login
                            reviewParams.put("employee", app.getUsernumber());
                            reviewParams.put("anonymous", anonymous);

                            Kumulos.call("createJobReview", reviewParams, new ResponseHandler() {
                                @Override
                                public void didCompleteWithResult(Object result) {
                                    Log.i("testing", result.toString());
                                    // Do updates to UI/data models based on result
                                    if (result.toString().equals("32") || result.toString().equals("64") || result.toString().equals("128")) {
                                        showAlert("Error", "There was an error when creating your review. Try again.");
                                    } else {
                                        Intent intent = new Intent(WriteReview.this, MainActivity.class);
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
    };

    private boolean fetchData() {
        Log.d(TAG, "fetchData");

        /*
        try {
            company = company_view.getText().toString();
        } catch (Exception e) {
            Log.d(TAG, "company" + e.toString());
        }

        try {
            job = job_view.getText().toString();
        } catch (Exception e) {
            Log.d(TAG, "job_review" + e.toString());
        }

        try {
            review_title = review_title_view.getText().toString();
        } catch (Exception e) {
            Log.d(TAG, "review_title" + e.toString());
        }

        try {
            review_body = review_body_view.getText().toString();
        } catch (Exception e) {
            Log.d(TAG, "review_body" + e.toString());
        }

        try {
        salary = salary_view.getText().toString();
        } catch (Exception e) {
            Log.d(TAG, "salary_view" + e.toString());
        }

        try {
        start_date = start_date_view.getText().toString();
        } catch (Exception e) {
            Log.d(TAG, "start_date_view" + e.toString());
        }

        return false;
        */


        boolean submit = true;

        company = company_view.getText().toString();
        job = job_view.getText().toString();
        review_title = review_title_view.getText().toString();
        review_body = review_body_view.getText().toString();
        salary = salary_view.getText().toString() + "";
        start_date = start_date_view.getText().toString();

        // check if values are valid - prevent submission if not
        if (company == null || company.trim().length() == 0) {
            company_view.setError("");
            submit = false;
        }
        if (job == null || job.trim().length() == 0) {
            job_view.setError("");
            submit = false;
        }
        if (review_title == null || review_title.trim().length() == 0) {
            review_title_view.setError("");
            submit = false;
        }
        if (review_body == null || review_body.trim().length() == 0) {
            ((TextView) findViewById(R.id.write_review_subOverall)).setError("");
            submit = false;
        }
        if (salary == null || salary.trim().length() == 0) {
            salary_view.setError("");
            submit = false;
        }
        if (rating == null) {
            ((TextView) findViewById(R.id.write_review_rating_title)).setError("");
            submit = false;
        }
        if (start_date.equals("MMMM DD, YYYY ")) {
            ((TextView) findViewById(R.id.write_review_start_date_error)).setError("");
            submit = false;
            Log.d(TAG, "start date not filled");
        } else if (startCal.getTime().after(Calendar.getInstance().getTime())) {
            showAlert("Error", "Start date (" + start_date + ") has not occurred yet.");
        }

        end_date = end_date_view.getText().toString();

        if (end_date.equals("MMMM DD, YYYY ")) {
            end_date = "N/A";
        } else {
            TextView startDate = (TextView) findViewById(R.id.write_review_start_date);
            if (!startDate.getText().equals("MMMM DD, YYYY ") &&
                    endCal.getTime().before(startCal.getTime())) {
                submit = false;
                Log.d(TAG, "end date after start date");

                showAlert("Error", "Your start date (" + startDate.getText() +
                        ") is after your end date (" + end_date + ")");
            }
        }

        if (submit) {
            if (anonymous_view.isChecked()) {
                anonymous = "true";
            } else {
                anonymous = "false";
            }
            Log.d(TAG, "fetchData:\n company:" + company + "\n job:" + job + "\n salary:" + salary +
                    "\n start_date:" + start_date + "\n end_date:" + end_date +
                    "\n review_title:" + review_title + "\n review_body:" + review_body +
                    "\n anonymous:" + anonymous);
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

    private View.OnTouchListener startDateTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                new DatePickerDialog(WriteReview.this, startDateListener, startCal
                        .get(Calendar.YEAR), startCal.get(Calendar.MONTH),
                        startCal.get(Calendar.DAY_OF_MONTH)).show();
            }
            return true;
        }
    };

    private View.OnTouchListener endDateTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                new DatePickerDialog(WriteReview.this, endDateListener, endCal
                        .get(Calendar.YEAR), endCal.get(Calendar.MONTH),
                        endCal.get(Calendar.DAY_OF_MONTH)).show();
            }
            return true;
        }
    };

    DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            startCal.set(Calendar.YEAR, year);
            startCal.set(Calendar.MONTH, monthOfYear);
            startCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(startCal, R.id.write_review_start_date);
        }
    };

    DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            endCal.set(Calendar.YEAR, year);
            endCal.set(Calendar.MONTH, monthOfYear);
            endCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateLabel(endCal, R.id.write_review_end_date);
        }
    };

    private void updateLabel(Calendar cal, int textView) {

        String myFormat = "MMMM dd, yyyy ";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ((TextView) findViewById(textView)).setText(sdf.format(cal.getTime()));
    }

    private View.OnClickListener ratingListener = new View.OnClickListener() {
        public void onClick(View v) {
            ImageView clicked = (ImageView) v;
            Log.d(TAG, "rating set to: " + rating);
            rating = v.getTag().toString();
            clicked.setColorFilter(purple);

            Log.d(TAG, "rating clicked: " + rating);

            for (int i = 0; i < star_ids.length; i++) {
                ImageView button = (ImageView) findViewById(star_ids[i]);
                if (Integer.parseInt(button.getTag().toString()) <= Integer.parseInt(rating)) {
                    button.setColorFilter(purple);
                } else {
                    button.setColorFilter(lightgrey);
                }
            }
        }
    };
}
