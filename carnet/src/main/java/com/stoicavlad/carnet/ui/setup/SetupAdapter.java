package com.stoicavlad.carnet.ui.setup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.stoicavlad.carnet.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 1/29/14.
 */
public class SetupAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] materii;

    static class RowHolder {
        @InjectView(R.id.textView)
        CheckedTextView mTextView;

        public RowHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public SetupAdapter(Context context, String[] materii) {
        super(context, R.layout.list_row_absente, materii);
        this.context = context;
        this.materii = materii;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RowHolder holder;
        View rowView = convertView;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.list_row_setup, parent, false);
            holder = new RowHolder(rowView);
            assert rowView != null;
            rowView.setTag(holder);
        } else {
            holder = (RowHolder) rowView.getTag();
        }
        ButterKnife.inject(this, rowView);
        holder.mTextView.setText(materii[position]);
//        holder.mTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.mTextView.setChecked(!holder.mTextView.isChecked());
//            }
//        });
        return rowView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
