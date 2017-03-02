package edu.washington.glassdub.glassdub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */

/**
 * TODO: Load the chosen job review from Kumulos and all of their data (review title, rating, etc.)
 */

public class CompanyReview extends Fragment {
    public CompanyReview() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_company_review, container, false);

        TextView title = (TextView)view.findViewById(R.id.CRtitle);
        TextView rating = (TextView) view.findViewById(R.id.CRrating);
        TextView salary = (TextView) view.findViewById(R.id.CRsalary);
        TextView position = (TextView) view.findViewById(R.id.CRposition);
        TextView start = (TextView) view.findViewById(R.id.CRstart);
        TextView end = (TextView) view.findViewById(R.id.CRend);
        TextView review = (TextView) view.findViewById(R.id.CRbody);
        TextView anonymous = (TextView) view.findViewById(R.id.CRanonymous);


        return view;
    }
}
