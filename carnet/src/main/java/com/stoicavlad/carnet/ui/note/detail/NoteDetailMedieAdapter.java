package com.stoicavlad.carnet.ui.note.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.UtilityMaterie;
import com.stoicavlad.carnet.data.VariantaMedie;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Vlad on 2/2/14.
 */
class NoteDetailMedieAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<VariantaMedie> mVariante;

    public void setVariante(ArrayList<VariantaMedie> mVariante) {
        this.mVariante = mVariante;
        notifyDataSetChanged();
    }

    public NoteDetailMedieAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        if(mVariante == null){
            return 0;
        }
        return mVariante.size();
    }

    @Override
    public VariantaMedie getItem(int position) {
        return mVariante.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_note_detail_medie, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VariantaMedie variantaMedie = getItem(position);
        holder.mValueTextView.setText(context.getString(R.string.trebuie_sa_iei) + ": " +
                UtilityMaterie.getStringFromIntArray(variantaMedie.noteNecesare));
        if (variantaMedie.teza != 0) {
            holder.mValueTezaTextView.setVisibility(View.VISIBLE);
            holder.mValueTezaTextView.setText(context.getString(R.string.daca_teza) + " "
                    + variantaMedie.teza);
        } else {
            holder.mValueTezaTextView.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_note_detail_header, parent, false);
            holder = new HeaderViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        VariantaMedie variantaMedie = getItem(position);
        holder.text.setText(context.getString(R.string.pentru_media) + " " +
                variantaMedie.medie + "");
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return getItem(position).medie;
    }



    class HeaderViewHolder {
        @InjectView(R.id.text) TextView text;
        HeaderViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    class ViewHolder {
        @InjectView(R.id.value)
        TextView mValueTextView;
        @InjectView(R.id.valueTeza)
        TextView mValueTezaTextView;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

    }


}

