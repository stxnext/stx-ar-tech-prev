package com.stxnext.ar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stxnext.ar.R;
import com.stxnext.ar.model.DrawerMenuItems;

public class DrawerAdapter extends ArrayAdapter<DrawerMenuItems> {

    public DrawerAdapter(Context context) {
        super(context, -1, DrawerMenuItems.values());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.drawer_item_layout, null);
        }

        TextView label = (TextView) convertView.findViewById(R.id.drawer_label);
        label.setText(getItem(position).getTitle());
        label.setAllCaps(true);

        return convertView;
    }

}
