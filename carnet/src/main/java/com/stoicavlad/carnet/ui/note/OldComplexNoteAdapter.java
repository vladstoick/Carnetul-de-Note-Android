package com.stoicavlad.carnet.ui.note;

/**
 * Created by Vlad on 1/27/14.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.model.Materie;
import com.stoicavlad.carnet.data.model.Nota;
import com.stoicavlad.carnet.data.otto.BusProvider;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 1/26/14.
 */
class OldComplexNoteAdapter extends ArrayAdapter<Materie> {

    private final Context context;
    private final Materie[] materii;
    private ComplexNoteAdapterInteractionListener mListener;

    public OldComplexNoteAdapter(Context context, Materie[] materii) {
        super(context, R.layout.list_row_note_list_advanced, materii);
        BusProvider.getInstance().register(this);
        this.context = context;
        this.materii = materii;
    }

    public void setmListener(ComplexNoteAdapterInteractionListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RowHolder holder;
        View rowView = convertView;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.list_row_note_list_advanced, parent, false);
            holder = new RowHolder(rowView);
            rowView.setTag(holder);
        } else {
            holder = (RowHolder) rowView.getTag();
        }
        ButterKnife.inject(this, rowView);
        final Materie materie = materii[position];
        //TITLE
        holder.mTitleTextView.setText(materie.name);
        //MEDIE
        double medie = materie.getMedie();
        holder.mValueTextView.setText(materie.getMedieAsString(medie));
        if (medie < 4.5) {
            holder.mValueTextView
                    .setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        } else {
            holder.mValueTextView
                    .setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        }
        //note
        holder.mNoteTextView.setText(materie.getNoteAsString(context.getString(R.string.note)));
        //teza
        try {
            Nota teza = materie.getTeza();
            holder.mTezaTextView.setVisibility(View.VISIBLE);
            holder.mTezaTextView.setText(context.getString(R.string.teza) + " : " + teza.nota);

        } catch (NullPointerException e) {
            holder.mTezaTextView.setVisibility(View.GONE);
        }
        //overflowButton
        holder.mOverflowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, materie);
            }
        });
        return rowView;
    }

    void showPopup(View v, final Materie materie) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popupmenu_materie, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete: {
                        if (mListener != null) {
                            mListener.onDeleteMaterie(materie);
                            return true;
                        }
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

    public interface ComplexNoteAdapterInteractionListener {
        public void onDeleteMaterie(Materie materie);
    }

    static class RowHolder {
        @InjectView(R.id.title) TextView mTitleTextView;
        @InjectView(R.id.value) TextView mValueTextView;
        @InjectView(R.id.note) TextView mNoteTextView;
        @InjectView(R.id.teza) TextView mTezaTextView;
        @InjectView(R.id.overflow) ImageButton mOverflowButton;
        public RowHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}