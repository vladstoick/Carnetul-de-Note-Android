package com.stoicavlad.carnet.ui.note;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.model.Materie;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Vlad on 1/31/14.
 */
public class NotaCard extends Card {

    @InjectView(R.id.value)
    TextView mValueTextView;
    @InjectView(R.id.note)
    TextView mNoteTextView;
    @InjectView(R.id.teza)
    TextView mTezaTextView;
    private Materie materie;

    public NotaCard(Context context, Materie materie) {
        this(context, R.layout.list_row_note_advanced);
        this.materie = materie;
    }


    public NotaCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }


    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        ButterKnife.inject(this,view);

        //Materie Medie
        double medie = materie.getMedie();
        mValueTextView.setText(materie.getMedieAsString(medie) + "");
        if(medie < 5 ){
            mValueTextView.setTextColor(getContext().getResources()
                    .getColor(android.R.color.holo_red_dark));
        } else {
            mValueTextView.setTextColor(getContext().
                            getResources().getColor(android.R.color.holo_green_dark));
        }
        //note
        mNoteTextView.setText(materie.getNoteAsString(getContext().getString(R.string.note)));
        //teza
        int teza = materie.getTeza();
        if (teza == 0) {
            mTezaTextView.setVisibility(View.GONE);
        } else {
            mTezaTextView.setVisibility(View.VISIBLE);
            mTezaTextView.setText(getContext().getString(R.string.teza) + " : " + teza);
        }


    }
}