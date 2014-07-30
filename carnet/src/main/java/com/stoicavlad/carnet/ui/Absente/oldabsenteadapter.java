package com.stoicavlad.carnet.ui.absente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.model.AbsentaUtility;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 1/29/14.
 */
class oldabsenteadapter extends ArrayAdapter<AbsentaUtility> {
    private final Context context;
    private final AbsentaUtility[] absente;

    static class RowHolder {
        @InjectView(R.id.value)
        TextView mValue;
        public RowHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public oldabsenteadapter(Context context, AbsentaUtility[] absente) {
        super(context, R.layout.list_row_absente, absente);
        this.context = context;
        this.absente = absente;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RowHolder holder;
        View rowView = convertView;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.list_row_absente, parent, false);
            holder = new RowHolder(rowView);
            if(rowView != null){
                rowView.setTag(holder);
            }
        } else {
            holder = (RowHolder) rowView.getTag();
        }
        ButterKnife.inject(this, rowView);
        holder.mValue.setText(absente[position].getDate());

        return rowView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
