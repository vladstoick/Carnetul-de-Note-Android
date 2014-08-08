package com.stoicavlad.carnet.ui.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.stoicavlad.carnet.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Vlad on 08-Aug-14.
 */
public class NotaSelector extends LinearLayout {

    public int selectedValue = -1;
    public ToggleButton lastToggleButton;

    public interface OnNotaSelectorListener{
        public void didChangeValue(boolean isSelected);
    }

    private OnNotaSelectorListener mListener;

    public void setOnNotaSelectormListener(OnNotaSelectorListener mListener) {
        this.mListener = mListener;
    }

    public NotaSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater  mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.view_notaselector, this, true);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5,
            R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9, R.id.button_10})
    public void didSelectNota(ToggleButton toggleButton){
        if(lastToggleButton != null && lastToggleButton!=toggleButton){
            lastToggleButton.setChecked(false);
        }

        lastToggleButton = toggleButton;
        if(toggleButton.isChecked()) {
            selectedValue = Integer.valueOf(lastToggleButton.getTextOff().toString());
            mListener.didChangeValue(true);
        } else {
            selectedValue = -1;
            mListener.didChangeValue(false);
        }

    }
}
