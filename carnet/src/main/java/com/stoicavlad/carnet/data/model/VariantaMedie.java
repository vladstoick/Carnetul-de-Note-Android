package com.stoicavlad.carnet.data.model;

import android.content.Context;

import com.stoicavlad.carnet.R;

import java.util.ArrayList;


public class VariantaMedie {
    public int medie;
    public boolean posibil = true;
    public Nota[] note;

    /**
     * Constructor for varianta medie
     * @param medie
     * @param materie
     * @param nrNote
     */
    public VariantaMedie(int medie, Materie materie, int nrNote){
        this.medie = medie;

        double nevDeMedie = medie - 0.5;
        ArrayList<VariantaMedie> varianteMedii = new ArrayList<VariantaMedie>();
        int sumaCurenta = calculeazaSumaCurenta(materie);
        int lungimeCurenta = materie.getNoteFaraTeza().length;
        long notaVeche = 0;
        int lungNoua = lungimeCurenta + nrNote;
        double sumaNecesara = nevDeMedie * lungNoua;
        double sumaNecesaraDinNoteleNoi = sumaNecesara - sumaCurenta;
        double notaNecesaraDouble = sumaNecesaraDinNoteleNoi/nrNote;
        long notaNecesara = (long) (Math.ceil(notaNecesaraDouble) );
        if(notaNecesara>10 || notaNecesara == notaVeche){
            posibil = false;
        } else {
            notaVeche = notaNecesara;
            ArrayList<Nota> note = new ArrayList<Nota>();
            for(int j=1;j<=nrNote;j++){
                Nota nota = new Nota();
                nota.nota = (int) notaNecesara;
                note.add(nota);
            }
            this.note = note.toArray(new Nota[note.size()]);
        }
    }

    public String getNoteAsString(Context context){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getString(R.string.trebuie_sa_iei));
        stringBuilder.append(": ");
        for(int i=0;i<note.length;i++){
            if(i>0){
                stringBuilder.append(", ");
            }
            stringBuilder.append(note[i].nota);
        }
        return stringBuilder.toString();
    }

    public static int calculeazaSumaCurenta(Materie materie){
        Nota[] note = materie.getNoteFaraTeza();
        int suma = 0;
        for(Nota nota:note){
            suma += nota.nota;
        }
        return suma;
    }
}
