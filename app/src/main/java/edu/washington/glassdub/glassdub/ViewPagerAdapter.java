package edu.washington.glassdub.glassdub;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Michael on 3/1/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> tabTitles = new ArrayList<>();

    public void addFragments(Fragment fragments, String tabTtitles) {
        this.fragments.add(fragments);
        this.tabTitles.add(tabTtitles);
        //notifyDataSetChanged();
    }

    public void replaceFragment(int pos, Fragment newFrag) {
        fragments.set(pos, newFrag);
        notifyDataSetChanged();
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

}
