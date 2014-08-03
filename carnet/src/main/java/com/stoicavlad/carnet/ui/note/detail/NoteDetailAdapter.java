package com.stoicavlad.carnet.ui.note.detail;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 01-Aug-14.
 */
public class NoteDetailAdapter extends CursorAdapter {
    public NoteDetailAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }
    class ViewHolder {
        @InjectView(R.id.value) TextView valueTextView;
        @InjectView(R.id.overflow) ImageButton deleteButton;
        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_row_note_detail,
                    viewGroup, false);
        ViewHolder valueViewHolder = new ViewHolder(view);
        view.setTag(valueViewHolder);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        int nota = cursor.getInt(CarnetContract.NoteEntry.COL_VALUE);
        viewHolder.valueTextView.setText(String.valueOf(nota));
        final int id = cursor.getInt(CarnetContract.NoteEntry.COL_ID);
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.getContentResolver().delete(CarnetContract.NoteEntry.buildNotaUri(id)
                        ,null,null);
            }
        });
    }
}
