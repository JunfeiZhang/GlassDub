package edu.washington.glassdub.glassdub;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyList extends Fragment {
    private String[] titles = new String[] {
            "Amazon", "Microsoft", "Boeing", "Whitepages", "T-Mobile", "WorkDay",
            "Amazon", "Microsoft", "Boeing"
    };

    private String[] subtitles =
            {"UI/UX Designer","SDE Internship", "Electrical Engineer", "Data Scientist",
                    "Mechanical Engineer", "SDE Internship", "Electrical Engineer",
                    "Data Scientist", "Mechanical Engineer"};

    private String[] contents =
            {"Seattle, WA", "Redmond, WA", "Seattle, WA", "Seattle, WA", "Bonn, Germany",
                    "Pleasanton, CA", "Seattle, WA", "Redmond, WA", "Seattle, WA"};

    private int[] counts = {3, 2, 4, 5, 1, 2, 3, 2, 1};

    private static final String TAG = "CompanyList";

    public CompanyList() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_company_list, container, false);

        if ((titles.length == subtitles.length) && (titles.length == contents.length) &&
                (titles.length == counts.length)) {

            // Populate with data
            CustomAdapter adapter = new CustomAdapter(getActivity(), R.layout.list_item, getData());

            ListView companyReviewList = (ListView) view.findViewById(R.id.company_listview);
            companyReviewList.setAdapter(adapter);

            companyReviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    Intent intent = new Intent(getActivity(), CompanyActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Log.d(TAG, "arrays don't have same length");
        }
        return view;
    }

    private CustomItem[] getData() {
        CustomItem data[] = new CustomItem[titles.length];

        for (int i = 0; i < titles.length; i++) {
            data[i] = new CustomItem(titles[i], subtitles[i], contents[i], counts[i]);
        }

        return data;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public void setUsers(String[] users) {
        this.subtitles = users;
    }

    public void setReviews(String[] reviews) {
        this.contents = reviews;
    }

    public void setCounts(int[] counts) {
        this.counts = counts;
    }

}