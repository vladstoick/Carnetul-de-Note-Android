package com.stoicavlad.carnet.data.model;

import android.content.Context;

import com.stoicavlad.carnet.R;

import java.util.ArrayList;


public class VariantaMedie {
    public int medie;
    public boolean posibil = true;
    private Nota[] note;
    public Nota teza;
    /**
     * Constructor for varianta medie
     * @param medie Media dorită
     * @param materie Materia respectivă
     * @param nrNote Numărul de note dorit
     */
    public VariantaMedie(int medie, Materie materie, int nrNote){
        this.medie = medie;
        //media necesara esti media -0.5
        double nevDeMedie = medie - 0.5;
        //sa vedem ce suma au notele de pana acum
        int sumaCurenta = calculeazaSumaCurenta(materie.getNoteFaraTeza());
        //cate note vor fi acum
        int lungNoua = materie.getNoteFaraTeza().length + nrNote;
        //ce suma trebuie sa aiba notele noi
        double sumaNecesaraDinNoteleNoi = nevDeMedie * lungNoua - sumaCurenta;
        //ce nota minima necesara trebuie sa iei
        long notaNecesara = (long) (Math.ceil(sumaNecesaraDinNoteleNoi/nrNote) );
        if(notaNecesara>10){
            posibil = false;
        } else {
            ArrayList<Nota> note = new ArrayList<Nota>();
            for(int j=1;j<=nrNote;j++){
                Nota nota = new Nota();
                nota.nota = (int) notaNecesara;
                note.add(nota);
            }
            this.note = note.toArray(new Nota[note.size()]);
        }
    }

    /**
     * Constructor for varianta medie
     * @param medie Media dorită
     * @param materie Materia respectivă
     * @param nrNote Numărul de note dorit
     * @param teza Nota din teză
     */
    public VariantaMedie(int medie, Materie materie, int nrNote,int teza){
        this.medie = medie;
        int lungimeNoua = materie.getNoteFaraTeza().length + nrNote;
        //media necesara este media -0.5
        double mediaNecesara = medie - 0.5;
        //sa vedem ce medie tb sa fie in oral
        double medieNecesaraOral = ( mediaNecesara * 4 - teza ) / 3;
        //sa vedem ce suma au notele de pana acum
        int sumaCurenta = calculeazaSumaCurenta(materie.getNoteFaraTeza());
        //sa vedem ce suma trebuie sa aiba notele pe care trebuie sa le adunam
        double sumaNecesaraDinNoteleNoi = medieNecesaraOral * lungimeNoua - sumaCurenta;
        long notaNecesara = (long) (Math.ceil(sumaNecesaraDinNoteleNoi/nrNote) );
        if(notaNecesara>10){
            posibil = false;
        } else {
            ArrayList<Nota> note = new ArrayList<Nota>();
            for(int j=1;j<=nrNote;j++){
                Nota nota = new Nota();
                nota.nota = (int) notaNecesara;
                note.add(nota);
            }
            this.note = note.toArray(new Nota[note.size()]);
        }
    }

    public static VariantaMedie[] getVarianteTeza(int medie, Materie materie,int nrNote){
        ArrayList<VariantaMedie> varianteMedii = new ArrayList<VariantaMedie>();
        for(int i=1;i<=10;i++){
            VariantaMedie variantaMedie = new VariantaMedie(medie,materie,nrNote,i);
            if(variantaMedie.posibil){
                varianteMedii.add(variantaMedie);
                Nota nota = new Nota();
                nota.tip = Nota.TIP_NOTA_TEZA;
                nota.nota = i;
                variantaMedie.teza = nota;
            }
        }
        return varianteMedii.toArray(new VariantaMedie[varianteMedii.size()]);
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

    private static int calculeazaSumaCurenta(Nota[] note){
        int suma = 0;
        for(Nota nota:note){
            suma += nota.nota;
        }
        return suma;
    }
}
