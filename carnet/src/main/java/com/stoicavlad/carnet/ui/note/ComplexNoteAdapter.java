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

import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.api.MateriiDatabase;
import com.stoicavlad.carnet.data.model.Materie;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 1/26/14.
 */
public class ComplexNoteAdapter extends ArrayAdapter<Materie> {
    private final Context context;
    private final Materie[] materii;
    @Inject
    MateriiDatabase materiiDatabase;

    public ComplexNoteAdapter(Context context, Materie[] materii) {
        super(context, R.layout.list_row_note_advanced, materii);
        CarnetApp.get(context).inject(this);
        this.context = context;
        this.materii = materii;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RowHolder holder;
        View rowView = convertView;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.list_row_note_advanced, parent, false);
            holder = new RowHolder(rowView);
            rowView.setTag(holder);
        } else {
            holder = (RowHolder) rowView.getTag();
        }
        ButterKnife.inject(this, rowView);
        final Materie materie = materii[position];
        //TITLE
        holder.mTitleTextView.setText(materie.getName());
        //MEDIE
        double medie = materie.getMedie();
        holder.mValueTextView.setText(materie.getMedieAsString(medie));
        if (medie < 5) {
            holder.mValueTextView
                    .setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        } else {
            holder.mValueTextView
                    .setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        }
        //note
        holder.mNoteTextView.setText(materie.getNoteAsString(context.getString(R.string.note)));
        //teza
        int teza = materie.getTeza();
        if (teza == 0) {
            holder.mTezaTextView.setVisibility(View.GONE);
        } else {
            holder.mTezaTextView.setVisibility(View.VISIBLE);
            holder.mTezaTextView.setText(context.getString(R.string.teza) + " : " + teza);
        }
        //overflowButton
        holder.mOverflowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v,materie);
            }
        });
        return rowView;
    }

    public void showPopup(View v, final Materie materie) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popupmenu_materie, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete: {
                        materiiDatabase.deleteMaterie(materie);
                        return true;
                    }
                }
                return false;
            }
        });
        popupMenu.show();
    }

    static class RowHolder {
        @InjectView(R.id.title)
        TextView mTitleTextView;
        @InjectView(R.id.value)
        TextView mValueTextView;
        @InjectView(R.id.note)
        TextView mNoteTextView;
        @InjectView(R.id.teza)
        TextView mTezaTextView;
        @InjectView(R.id.overflow)
        ImageButton mOverflowButton;

        public RowHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}