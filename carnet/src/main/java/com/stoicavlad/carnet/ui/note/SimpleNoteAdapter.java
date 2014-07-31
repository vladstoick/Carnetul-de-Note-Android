package com.stoicavlad.carnet.ui.note;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 1/26/14.
 */
class SimpleNoteAdapter extends CursorAdapter {

    static class ViewHolder {
        @InjectView(R.id.title) TextView mTitle;
        @InjectView(R.id.value) TextView mValue;
        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    SimpleNoteAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_row_note_list_simple,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String name = cursor.getString(CarnetContract.MaterieEntry.COL_NAME);
        viewHolder.mTitle.setText(name);
        double medie = cursor.getDouble(CarnetContract.MaterieEntry.COL_MEDIE);
        if(medie > 0.5 ) {
            viewHolder.mValue.setText(String.valueOf(medie));
        } else {
            viewHolder.mValue.setText("-");
        }

    }

}