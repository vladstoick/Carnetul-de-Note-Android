package com.stoicavlad.carnet.ui.note;

/**
 * Created by Vlad on 1/27/14.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.model.Materie;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 1/26/14.
 */
public class ComplexNoteAdapter extends ArrayAdapter<Materie> {
    private final Context context;
    private final Materie[] materii;
    static class RowHolder{
        @InjectView(R.id.title) TextView mTitleTextView;
        @InjectView(R.id.value) TextView mValueTextView;
        @InjectView(R.id.note) TextView mNoteTextView;
        @InjectView(R.id.teza) TextView mTezaTextView;
        public RowHolder(View view){
            ButterKnife.inject(this, view);
        }
    }
    public ComplexNoteAdapter(Context context, Materie[] materii) {
        super(context, R.layout.list_row_general, materii);
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
            rowView = inflater.inflate(R.layout.list_row_general, parent, false);
            holder = new RowHolder(rowView);
            rowView.setTag(holder);
        } else {
            holder = (RowHolder) rowView.getTag();
        }
        ButterKnife.inject(this,rowView);
        Materie materie = materii[position];
        holder.mTitleTextView.setText(materie.getName());
        holder.mValueTextView.setText(materie.getMedie() + "");
        holder.mNoteTextView.setText(materie.getNoteAsString(context.getString(R.string.note)));
        int teza = materie.getTeza();
        if(teza == 0 ){
            holder.mTezaTextView.setVisibility(View.GONE);
        } else {
            holder.mTezaTextView.setVisibility(View.VISIBLE);
            holder.mTezaTextView.setText(context.getString(R.string.teza) + " : " + teza);
        }
        return rowView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }
}