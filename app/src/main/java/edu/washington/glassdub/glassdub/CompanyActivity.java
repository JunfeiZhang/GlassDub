package edu.washington.glassdub.glassdub;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kumulos.android.Kumulos;
import com.kumulos.android.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CompanyActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private TextView title, descr;
    private LinearLayout rating;
    private Activity act = this;
    private ImageView companyImage;
    private ProgressBar progressBar;
    private LinearLayout companyLayout;
    private BottomNavigationView botNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        Intent intent = getIntent();
        String companyID = intent.getStringExtra("companyID");  // companyID

        //companyImage = (ImageView) findViewById(R.id.companyImage);
        mViewPager = (ViewPager) findViewById(R.id.company_container);


        // initialize bundle
        final Bundle b = new Bundle();
        b.putString("companyID", companyID);

        botNavigation = (BottomNavigationView) findViewById(R.id.bottomBar);
        botNavigation.getMenu().getItem(1).setChecked(true);
        botNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.jobItem) {
                    Intent intent = new Intent(CompanyActivity.this, WriteReview.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.homeItem) {
                    Intent intent = new Intent(CompanyActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.interviewItem) {
                    Intent intent = new Intent(CompanyActivity.this, WriteInterview.class);
                    startActivity(intent);
                }
                return false;
            }
        });


        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        tabLayout = (TabLayout) findViewById(R.id.company_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        title = (TextView) findViewById(R.id.cTitle);
        descr = (TextView) findViewById(R.id.cDescr);
        rating = (LinearLayout) findViewById(R.id.cRating);

        Map<String,String> companyParams = new HashMap<>();
        companyParams.put("companyID", companyID);

        companyLayout = (LinearLayout) findViewById(R.id.companyLayout);
        companyLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(VISIBLE);
        int shortAnimTime = getResources().getInteger(android.R.integer.config_longAnimTime);

        progressBar.animate().setDuration(shortAnimTime).alpha(true ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                companyLayout.setVisibility(VISIBLE);
                super.onAnimationEnd(animation);
                progressBar.setVisibility(GONE);
            }
        });

        Kumulos.call("getCompany", companyParams, new ResponseHandler() {
            @Override
            public void didCompleteWithResult(Object result) {
                if (result.toString().equals("32") || result.toString().equals("64") || result.toString().equals("128")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            act);
                    alertDialogBuilder
                            .setTitle("Error")
                            .setMessage("We We were unable to retrieve information about the selected company. Try again.")
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
                        b.putString("tile",object.get("name").toString());

                        String companyName = object.get("name").toString();
                        title.setText(companyName);

                        descr.setText(object.get("description").toString());
                        // TODO: Show rating with stars
                        setRating(Integer.parseInt(object.get("rating").toString()));
                        // TODO: Do image stuff here
//                        ImageView cImage = (ImageView) findViewById(R.id.cImage);
//                        if(cName.equals("Facebook")) {
//                            cImage.setImageResource(R.drawable.facebook);
//                        } else if (cName.equals("Amazon")) {
//                            cImage.setImageResource(R.drawable.amazon);
//                        } else if (cName.equals("Google")) {
//                            cImage.setImageResource(R.drawable.google);
//                        } else if (cName.equals("Tableau")) {
//                            cImage.setImageResource(R.drawable.tableau);
//                        } else if (cName.equals("Zillow")) {
//                            cImage.setImageResource(R.drawable.zillow);
//                        } else if (cName.equals("Starbucks")) {
//                            cImage.setImageResource(R.drawable.starbucks);
//                        }
                    }
                }
            }
        });


        // put bundle into three different fragments

        Fragment reviewList = new ReviewList();
        reviewList.setArguments(b);

        Fragment interviewList = new InterviewList();
        interviewList.setArguments(b);

        Fragment jobList = new JobList();
        jobList.setArguments(b);


        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        vpAdapter.addFragments(reviewList, "Reviews");
        vpAdapter.addFragments(interviewList, "Interviews");
        vpAdapter.addFragments(jobList, "Jobs");
        mViewPager.setAdapter(vpAdapter);

    }

    private void setRating(int count) {
        int[] stars = {R.id.star_1, R.id.star_2, R.id.star_3, R.id.star_4, R.id.star_5};
        for (int i = 0; i < count; i++) {
            ((ImageView) rating.findViewById(stars[i])).setImageResource(R.drawable.ic_star_gold_24dp);
        }
    }
}