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
import com.stoicavlad.carnet.data.Utility;
import com.stoicavlad.carnet.data.provider.CarnetContract;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 31-Jul-14.
 */
public class ComplexNoteAdapter extends CursorAdapter {

    private Context mContext;

    public interface OnOverflowButtonInterface{
        public void showAddTezaDialogFragment(int id, String name);
    }

    private OnOverflowButtonInterface mListener;

    public void setListener(OnOverflowButtonInterface mListener) {
        this.mListener = mListener;
    }

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
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        //title
        String name = cursor.getString(CarnetContract.MaterieEntry.COL_NAME);
        viewHolder.mTitleTextView.setText(name);

        //teza
        int teza = cursor.getInt(CarnetContract.MaterieEntry.COL_TEZA);
        if (teza == 0) {
            viewHolder.mTezaTextView.setVisibility(View.GONE);
        } else {
            viewHolder.mTezaTextView.setVisibility(View.VISIBLE);
            viewHolder.mTezaTextView.setText(context.getString(R.string.teza) + " : " + teza);
        }


        //medie
        double medieNote = cursor.getDouble(CarnetContract.MaterieEntry.COL_MEDIE);
        double medie = Utility.getMedieForMaterie(teza, medieNote);
        if (medie == 0) {
            viewHolder.mValueTextView.setText("-");
        } else {
            viewHolder.mValueTextView.setText(Utility.getTwoDecimalsFromMaterie(medie));
        }
        if (medie < 4.5) {
            viewHolder.mValueTextView
                    .setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        } else {
            viewHolder.mValueTextView
                    .setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        }


        //note
        String noteString = cursor.getString(CarnetContract.MaterieEntry.COL_NOTE);
        String notePrefixString = context.getString(R.string.note) + ": ";

        if( noteString == null ){
            viewHolder.mNoteTextView.setVisibility(View.GONE);
        } else {
            viewHolder.mNoteTextView.setText(notePrefixString + noteString);
            viewHolder.mNoteTextView.setVisibility(View.VISIBLE);
        }
        final int position = cursor.getPosition();
        //overflow button
        viewHolder.mOverflowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view, position);
            }
        });
    }

    void showPopup(View v, final int position) {
        final Cursor cursor = getCursor();
        cursor.moveToPosition(position);
        int teza = cursor.getInt(CarnetContract.MaterieEntry.COL_TEZA);

        PopupMenu popupMenu = new PopupMenu(mContext, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        if(teza == 0) {
            inflater.inflate(R.menu.popupmenu_materie, popupMenu.getMenu());
        } else {
            inflater.inflate(R.menu.popupmenu_materie_fara_teza, popupMenu.getMenu());
        }

         popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete: {
                        int id = cursor.getInt(CarnetContract.MaterieEntry.COL_ID);
                        Uri uri = CarnetContract.MaterieEntry.buildMaterieUri(id);
                        mContext.getContentResolver().delete(uri,null,null);
                        break;
                    }
                    case R.id.add: {
                        int id = cursor.getInt(CarnetContract.MaterieEntry.COL_ID);
                        String materieName = cursor.getString(CarnetContract.MaterieEntry.COL_NAME);
                        mListener.showAddTezaDialogFragment(id, materieName);
                        break;
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

}
