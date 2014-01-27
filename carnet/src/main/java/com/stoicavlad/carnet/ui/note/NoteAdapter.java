package com.stoicavlad.carnet.ui.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.note.Materie;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 1/26/14.
 */
public class NoteAdapter extends ArrayAdapter<Materie> {
    private final Context context;
    private final Materie[] materii;
    static class RowHolder{
        @InjectView(R.id.title) TextView mTitle;
        @InjectView(R.id.value) TextView mValue;
        public RowHolder(View view){
            ButterKnife.inject(this,view);
        }
    }
    public NoteAdapter(Context context, Materie[] materii) {
        super(context, R.layout.list_row_note, materii);
        this.context = context;
        this.materii = materii;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RowHolder holder;
        View rowView = convertView;
        if( rowView == null ){
            rowView = inflater.inflate(R.layout.list_row_note, parent, false);
            holder = new RowHolder(rowView);
            rowView.setTag(holder);
        } else {
            holder = (RowHolder) rowView.getTag();
        }
        ButterKnife.inject(this,rowView);
        holder.mTitle.setText(materii[position].getName());
        holder.mValue.setText(materii[position].getMedie() + "");
        return rowView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }
}