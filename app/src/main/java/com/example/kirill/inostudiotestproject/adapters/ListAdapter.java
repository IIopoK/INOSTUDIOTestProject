package com.example.kirill.inostudiotestproject.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kirill.inostudiotestproject.R;
import com.example.kirill.inostudiotestproject.objects.ListObject;

import java.util.List;

/**
 * Created by iiopok on 02.04.2015.
 */
public class ListAdapter extends ArrayAdapter<ListObject> {

    private final List<ListObject> list;
    private final Context context;

    public ListAdapter(Context context, List<ListObject> objects) {
        super(context, R.layout.list_item, objects);
        this.list = objects;
        this.context = context;
    }

    static class ViewHolder {
        protected TextView name;
        protected TextView created;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater lInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = lInflater.inflate(R.layout.list_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.tvName);
            viewHolder.created = (TextView) view.findViewById(R.id.tvCreated);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(list.get(position).getName());
        holder.created.setText(list.get(position).getCreated());
        return view;
    }
}
