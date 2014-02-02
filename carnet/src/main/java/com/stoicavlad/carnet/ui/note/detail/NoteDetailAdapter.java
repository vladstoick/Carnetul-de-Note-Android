package com.stoicavlad.carnet.ui.note.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.model.Nota;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Vlad on 2/1/14.
 */
public class NoteDetailAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    public Nota teza;
    Context context;
    private Nota[] note;
    private LayoutInflater inflater;


    public NoteDetailAdapter(Context context, Nota[] note, Nota teza) {
        this(context, note);
        this.teza = teza;
    }

    public NoteDetailAdapter(Context context, Nota[] note) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.note = note;
    }

    @Override
    public int getCount() {
        return note.length + (teza != null ? 1 : 0);
    }

    @Override
    public Nota getItem(int position) {
        if (teza != null && position == 0) {
            return teza;
        }
        if (teza == null) {
            return note[position];
        }
        return note[position - 1];

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_note_detail, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Nota nota = getItem(position);
        holder.text.setText(nota.nota + " ");

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
        Nota nota = getItem(position);
        String teza = context.getString(R.string.teza);
        String note = context.getString(R.string.note);
        String headerText = nota.tip == Nota.TIP_NOTA_SIMPLA ? note : teza;
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return getItem(position).tip == Nota.TIP_NOTA_SIMPLA ? 1 : 2;
    }

    class HeaderViewHolder {
        @InjectView(R.id.title)
        TextView text;

        HeaderViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    class ViewHolder {
        @InjectView(R.id.value)
        TextView text;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


}

