package com.stoicavlad.carnet.ui.note.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.model.Nota;
import com.stoicavlad.carnet.data.model.VariantaMedie;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Vlad on 2/2/14.
 */
public class NoteDetailMedieAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        private VariantaMedie[] variante;
        Context context;
        private LayoutInflater inflater;


        public NoteDetailMedieAdapter(Context context, VariantaMedie[] variante) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.variante = variante;
        }

        @Override
        public int getCount() {
            return variante.length;
        }

        @Override
        public VariantaMedie getItem(int position) {
            return variante[position];
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
            VariantaMedie variantaMedie = getItem(position);
            holder.text.setText(variantaMedie.getNoteAsString(context));
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

