package com.stoicavlad.carnet.ui.absente;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.UtilityMaterie;
import com.stoicavlad.carnet.data.provider.CarnetContract;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vlad on 30-Jul-14.
 */
@SuppressWarnings({"SameParameterValue", "WeakerAccess"})
public class AbsentaAdapter extends CursorAdapter {

    static class ViewHolder{
        @InjectView(R.id.value)
        TextView mValue;
        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public AbsentaAdapter(Context context, @SuppressWarnings("SameParameterValue") Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_row_absente,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        long date = cursor.getLong(CarnetContract.AbsentaEntry.COL_DATE);
        String formatedDate = UtilityMaterie.getDateFromLong(date);
        viewHolder.mValue.setText(formatedDate);
    }
}
