package com.stoicavlad.carnet.ui.note;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.model.Materie;
import com.stoicavlad.carnet.data.provider.CarnetContract;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 31-Jul-14.
 */
public class ComplexNoteAdapter extends CursorAdapter {

    private Context mContext;
    public ComplexNoteAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext = context;
    }

    static class ViewHolder {
        @InjectView(R.id.title) TextView mTitleTextView;
        @InjectView(R.id.value) TextView mValueTextView;
        @InjectView(R.id.note) TextView mNoteTextView;
        @InjectView(R.id.teza) TextView mTezaTextView;
        @InjectView(R.id.overflow) ImageButton mOverflowButton;
        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_row_note_list_advanced,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        //title
        String name = cursor.getString(CarnetContract.MaterieEntry.COL_NAME);
        viewHolder.mTitleTextView.setText(name);

        //overflow button
        viewHolder.mOverflowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view, cursor);
            }
        });
    }

    void showPopup(View v, final Cursor cursor) {
        PopupMenu popupMenu = new PopupMenu(mContext, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popupmenu_materie, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete: {
                        int id = cursor.getInt(CarnetContract.MaterieEntry.COL_ID);
                        Uri uri = CarnetContract.MaterieEntry.buildMaterieUri(id);
                        mContext.getContentResolver().delete(uri,null,null);
                    }
//                    case R.id.rename: {
//                        if (mListener != null) {
//                            mListener.onRenameMaterie(materie);
//                        }
//                    }
                }
                return false;
            }
        });
        popupMenu.show();
    }

//        //TITLE
//        holder.mTitleTextView.setText(materie.name);
//        //MEDIE
//        double medie = materie.getMedie();
//        holder.mValueTextView.setText(materie.getMedieAsString(medie));
//        if (medie < 4.5) {
//            holder.mValueTextView
//                    .setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
//        } else {
//            holder.mValueTextView
//                    .setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
//        }
//        //note
//        holder.mNoteTextView.setText(materie.getNoteAsString(context.getString(R.string.note)));
//        //teza
//        try {
//            Nota teza = materie.getTeza();
//            holder.mTezaTextView.setVisibility(View.VISIBLE);
//            holder.mTezaTextView.setText(context.getString(R.string.teza) + " : " + teza.nota);
//
//        } catch (NullPointerException e) {
//            holder.mTezaTextView.setVisibility(View.GONE);
//        }
//        //overflowButton
//        holder.mOverflowButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopup(v, materie);
//            }
//        });
//    }
}
