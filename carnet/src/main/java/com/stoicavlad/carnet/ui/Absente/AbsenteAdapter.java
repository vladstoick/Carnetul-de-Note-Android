package com.stoicavlad.carnet.ui.absente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.model.Absenta;
import com.stoicavlad.carnet.data.model.Materie;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 1/29/14.
 */
public class AbsenteAdapter extends ArrayAdapter<Absenta> {
    private final Context context;
    private final Absenta[] absente;
    static class RowHolder{
        @InjectView(R.id.value) TextView mValue;
        public RowHolder(View view){
            ButterKnife.inject(this, view);
        }
    }
    public AbsenteAdapter(Context context, Absenta[] absente) {
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
        if( rowView == null ){
            rowView = inflater.inflate(R.layout.list_row_absente, parent, false);
            holder = new RowHolder(rowView);
            rowView.setTag(holder);
        } else {
            holder = (RowHolder) rowView.getTag();
        }
        ButterKnife.inject(this,rowView);
        holder.mValue.setText(absente[position].getDate());

        return rowView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }
}
