package edu.washington.glassdub.glassdub;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    private String[] titles;
    private String[] subtitles;
    private String[] contents;
    private int[] counts;

    public ListFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle=getArguments();

        //here is your list array
        titles = bundle.getStringArray("titles");
        subtitles = bundle.getStringArray("subtitles");
        contents = bundle.getStringArray("contents");
        counts = bundle.getIntArray("counts");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // Populate with data
        CustomAdapter adapter = new CustomAdapter(getActivity(), R.layout.list_item, getData());

        ListView jobList = (ListView) view.findViewById(R.id.listView1);
        jobList.setAdapter(adapter);

        jobList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Fragment jobItem = new Job();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.list_fragment, jobItem);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
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