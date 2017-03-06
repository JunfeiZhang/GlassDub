package edu.washington.glassdub.glassdub;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<CustomItem> {

    Context context;
    int layoutResourceId;
    CustomItem data[] = null;

    View view;
    CustomHolder holder;

    private LayoutInflater mInflater;
    private int stars[] = {R.id.star_1, R.id.star_2, R.id.star_3, R.id.star_4, R.id.star_5};

    private static final String TAG = "CustomAdapter";


    public CustomAdapter(Context context, int layoutResourceId, CustomItem data[]) {

        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        view = convertView;
        holder = null;

        if(view == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);

            holder = new CustomHolder();
            holder.title = (TextView) convertView.findViewById(R.id.list_item_title);
            holder.subtitle = (TextView) convertView.findViewById(R.id.list_item_subtitle);
            holder.main = (TextView) convertView.findViewById(R.id.list_item_subtitle);
            holder.stars = (RelativeLayout) convertView.findViewById(R.id.stars);
            
            convertView.setTag(holder);
        }
        else
        {
            holder = (CustomHolder)convertView.getTag();
        }

        setContents(position);

        return convertView;
    }

    private void setContents(int position) {
        Log.d(TAG, "setContents");
        CustomItem item = data[position];
        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getSubtitle());
        holder.main.setText(item.getMain());

        for (int i = 0; i < item.getStarCount(); i++) {
            ((ImageView) holder.stars.findViewById(stars[i])).setImageResource(R.drawable.ic_star_gold_24dp);
        }
    }

    static class CustomHolder
    {
        TextView title;
        TextView subtitle;
        TextView main;
        RelativeLayout stars;
    }

}
