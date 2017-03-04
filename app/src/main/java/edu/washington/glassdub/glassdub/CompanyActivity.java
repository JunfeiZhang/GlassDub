package edu.washington.glassdub.glassdub;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kumulos.android.Kumulos;
import com.kumulos.android.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CompanyActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private TextView title, descr;
    private Activity act = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        mViewPager = (ViewPager) findViewById(R.id.container);

        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        vpAdapter.addFragments(new ReviewList(), "Reviews");
        vpAdapter.addFragments(new InterviewList(), "Interviews");
        vpAdapter.addFragments(new JobList(), "Jobs");

        mViewPager.setAdapter(vpAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        title = (TextView) findViewById(R.id.cTitle);
        descr = (TextView) findViewById(R.id.cDescr);

        // TODO: get info sent to fragment (comapny ID)
        int companyID = 2; // getArguments().getInt("comapnyID");

        Map<String,String> companyParams = new HashMap<>();
        companyParams.put("companyID", String.valueOf(companyID));

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
                    // TODO: go through and update all the fields
                    if (objects.size() > 0) {
                        LinkedHashMap<String, Object> object = objects.get(0);
                        title.setText(object.get("name").toString());
                        descr.setText((object.get("description").toString()));
                    }
                }
            }
        });
    }
}