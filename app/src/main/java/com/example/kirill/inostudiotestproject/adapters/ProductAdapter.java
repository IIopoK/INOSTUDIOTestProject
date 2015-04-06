package com.example.kirill.inostudiotestproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.kirill.inostudiotestproject.R;
import com.example.kirill.inostudiotestproject.dbUtils.ProductAndDB;
import com.example.kirill.inostudiotestproject.objects.ProductObject;

import java.util.List;

/**
 * Created by iiopok on 02.04.2015.
 */
public class ProductAdapter extends ArrayAdapter<ProductObject> {

    Context context;
    LayoutInflater lInflater;
    List<ProductObject> objects;

    public ProductAdapter(Context context, List<ProductObject> objects) {
        super(context, R.layout.shopping_list_item, objects);
        this.context = context;
        this.objects = objects;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ProductObject getItem(int position) {
        return objects.get(position);
    }

    static class ViewHolder{
        protected TextView tvName;
        protected TextView tvNumber;
        protected CheckBox checkBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if(convertView == null){
            view = lInflater.inflate(R.layout.shopping_list_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);
            viewHolder.tvNumber = (TextView) view.findViewById(R.id.tvNumber);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.cbBought);
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ProductObject element = (ProductObject) viewHolder.checkBox.getTag();
                    element.setIsChecked(isChecked);
                    new ProductAndDB(context).updateProduct(element);
                }
            });
            view.setTag(viewHolder);
            viewHolder.checkBox.setTag(getItem(position));
        }else {
            view = convertView;
            ((ViewHolder) view.getTag()).checkBox.setTag(getItem(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.tvName.setText(getItem(position).getName());
        holder.tvNumber.setText(String.valueOf(getItem(position).getNumber()));
        holder.checkBox.setChecked(getItem(position).getIsChecked());
        return view;
    }
}
