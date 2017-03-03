package edu.washington.glassdub.glassdub;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CompanyActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout tabLayout;

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
    }
}
