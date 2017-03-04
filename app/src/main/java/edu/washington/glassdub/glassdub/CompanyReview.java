package edu.washington.glassdub.glassdub;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kumulos.android.Kumulos;
import com.kumulos.android.ResponseHandler;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */

/**
 * TODO: Load the chosen job review from Kumulos and all of their data (review title, rating, etc.)
 */

public class CompanyReview extends Fragment {
    private TextView title, rating, salary, position, start, end, review, anonymous;
    public CompanyReview() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_company_review, container, false);

        title = (TextView)view.findViewById(R.id.CRtitle);
        rating = (TextView) view.findViewById(R.id.CRrating);
        salary = (TextView) view.findViewById(R.id.CRsalary);
        position = (TextView) view.findViewById(R.id.CRposition);
        start = (TextView) view.findViewById(R.id.CRstart);
        end = (TextView) view.findViewById(R.id.CRend);
        review = (TextView) view.findViewById(R.id.CRbody);
        anonymous = (TextView) view.findViewById(R.id.CRanonymous);

        // TODO: get info sent to fragment (comapny ID)
        int companyRevID = 22; // getArguments().getInt("comapnyRevID");
        Map<String,String> revParams = new HashMap<>();
        revParams.put("job_reviewID", String.valueOf(companyRevID));

        Kumulos.call("getCompanyReview", revParams, new ResponseHandler() {
            @Override
            public void didCompleteWithResult(Object result) {
                if (result.toString().equals("32") || result.toString().equals("64") || result.toString().equals("128")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getActivity());
                    alertDialogBuilder
                            .setTitle("Error")
                            .setMessage("We were unable to retrieve information about the selected review. Try again.")
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
                    ArrayList<LinkedHashMap<String, Object>> objects = (ArrayList<LinkedHashMap<String, Object>>) result;
                    // TODO: go through and update all the fields
                    if (objects.size() > 0) {
                        LinkedHashMap<String, Object> object = objects.get(0);
                        title.setText(object.get("name").toString());
                        review.setText(object.get("body").toString());
                        rating.setText(object.get("rating").toString());
                        salary.setText(object.get("pay_rate").toString());
                        position.setText(object.get("position").toString());
                        start.setText(object.get("start_date").toString());
                        // TODO: dont show this if the user didnt enter it
                        end.setText(object.get("end_date").toString());
                        // TODO: if it is anonymous just print anonymous, otherwise print username
                        anonymous.setText(object.get(anonymous).toString());
                    }
                }
            }
        });

        return view;
    }
}
