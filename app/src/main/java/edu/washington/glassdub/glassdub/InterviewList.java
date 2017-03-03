package edu.washington.glassdub.glassdub;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */

/**
 * TODO: Load the list of fetched interview reviews from Kumulos (after the user has searched)
 */

public class InterviewList extends Fragment {
    private String[] interviewReviews = new String[] {
            "Interview Review 1", "Interview Review 2", "Interview Review 3"
    };
    //private ViewPagerAdapter vpAdapter;

    public InterviewList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_interview_list, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, interviewReviews);
        ListView interviewList = (ListView) view.findViewById(R.id.listView5);
        interviewList.setAdapter(adapter);

        interviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            Intent intent = new Intent(getContext(), InterviewPage.class);
            startActivity(intent);
            }
        });

        return view;
    }

}
