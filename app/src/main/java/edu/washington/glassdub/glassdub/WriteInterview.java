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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * TODO: Save username with the review
 */


public class WriteInterview extends AppCompatActivity {
    private Button submit;
    private BottomNavigationView botNavigation;

    final Context context = this;
    Calendar calendar;

    String[] offer_list = {"Yes", "No", "Pending"};

    String company_job = null;
    String date = null;
    String offer = null;
    String comments = null;

    int difficulty = -1;
    int[] difficulty_ids = {R.id.difficulty_1, R.id.difficulty_2, R.id.difficulty_3,
            R.id.difficulty_4, R.id.difficulty_5};

    String experience = null;
    int[] experience_ids = {R.id.positive_button, R.id.neutral_button, R.id.negative_button};

    int grey;
    int purple;

    public static final String TAG = "WriteInterview";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_interview);

        fillOfferList();

        grey =  ResourcesCompat.getColor(getResources(), R.color.mediumgrey, null);
        purple = ResourcesCompat.getColor(getResources(), R.color.purple, null);

        submit = (Button) findViewById(R.id.submit_interview_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean filled = fetchData();

                if (filled) {
                    Intent intent = new Intent(WriteInterview.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

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

        botNavigation = (BottomNavigationView) findViewById(R.id.bottomBar);
        botNavigation.getMenu().getItem(2).setChecked(true);
        botNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.jobItem) {
                    Intent intent = new Intent(WriteInterview.this, WriteReview.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.homeItem) {
                    Intent intent = new Intent(WriteInterview.this, MainActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.interviewItem) {
                    Intent intent = new Intent(WriteInterview.this, WriteInterview.class);
                    startActivity(intent);
                }
                return false;
            }
        });
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

    private boolean fetchData() {
        CustomEditText company_view = ((CustomEditText) findViewById(R.id.write_interview_company));
        CustomEditText job_view = ((CustomEditText) findViewById(R.id.write_interview_job));
        TextView date_view = ((TextView) findViewById(R.id.write_interview_date));
        Spinner offer_view = ((Spinner) findViewById(R.id.write_interview_offer));
        EditText comments_view = ((EditText) findViewById(R.id.write_interview_body));


        String company = company_view.getText().toString();
        String job = job_view.getText().toString();
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
            ((TextView) findViewById(R.id.write_interview_subOverall)).setError("");
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
            company_job = company + " - " + job;

            Log.d(TAG, "fetchData:\n company:" + company + "\n job:" + job + "\n " + date +
                   "\n offer:" + offer + "\n comments:" + comments);
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