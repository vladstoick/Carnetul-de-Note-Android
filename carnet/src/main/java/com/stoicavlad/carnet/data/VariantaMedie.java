package com.stoicavlad.carnet.data;

import java.util.ArrayList;

/**
 * Created by Vlad on 05-Aug-14.
 */
public class VariantaMedie {
    public int medie;
    public int[] noteNecesare;
    public boolean ePosibil = false;
    public int teza = 0;

    public VariantaMedie(){}

    public void getNoteNecesareForSuma(double sumaNecesaraAdauagate, int nrNoteDorite){
        if(sumaNecesaraAdauagate /  nrNoteDorite > 10){
            ePosibil = false;
        } else {
            ePosibil = true;
            noteNecesare = new int[nrNoteDorite];
            for(int i = 0 ; i < nrNoteDorite ; i ++ ){
                int notaNecesara = (int) Math.ceil(sumaNecesaraAdauagate / (nrNoteDorite - i ));
                noteNecesare [i] = notaNecesara;
                sumaNecesaraAdauagate -= notaNecesara;
            }
        }

    }

    //WITHOUT TEZA

    public VariantaMedie(int sumaNote, int nrNoteExistente, int nrNoteDorite,  int medieDorita){
        this.medie = medieDorita;

        double sumaNecesaraTotala = (medieDorita-0.5) * (nrNoteExistente + nrNoteDorite);
        double sumaNecesaraAdauagate = sumaNecesaraTotala - sumaNote;

        getNoteNecesareForSuma(sumaNecesaraAdauagate, nrNoteDorite);
    }



    public static ArrayList<VariantaMedie> getVarianteMedieWithoutTeza(int sumaNote,
                                                                       int nrNoteExistete){
       ArrayList<VariantaMedie> variantaMedii = new ArrayList<VariantaMedie>();
       double medieCurentaDouble = sumaNote / nrNoteExistete;
       int medieCurenta = (int) Math.round(medieCurentaDouble);
       for(int i = medieCurenta + 1; i <= 10 ; i++){
           VariantaMedie variantaMedie = new VariantaMedie();
           for(int nrNote = 1 ; nrNote <= 10 && !variantaMedie.ePosibil ; nrNote ++){
               variantaMedie = new VariantaMedie(sumaNote, nrNoteExistete, nrNote, i);
           }
           if(!variantaMedie.ePosibil){
               break;
           } else {
               variantaMedii.add(variantaMedie);
           }
       }
       return variantaMedii;
    }

    //WITH TEZA

   public VariantaMedie(int sumaNote, int nrNoteExistente, int nrNoteDorite,
                        int medieDorita, int teza){
       this.medie = medieDorita;

       double medieNecesaraNote = ( (medieDorita-0.5) * 4 - teza ) / 3;
       double sumaNecesaraTotala = medieNecesaraNote * (nrNoteDorite + nrNoteExistente);
       double sumaNecesaraAduagate = sumaNecesaraTotala - sumaNote;

       getNoteNecesareForSuma(sumaNecesaraAduagate, nrNoteDorite);
   }

    public static ArrayList<VariantaMedie> getVarianteMedieWithTeza(int sumaNote,
                                                                    int nrNoteExistete, int teza){
        ArrayList<VariantaMedie> variantaMedii = new ArrayList<VariantaMedie>();
        double medieCurentaDouble = ( ( sumaNote / nrNoteExistete) * 3 + teza ) / 4;
        int medieCurenta = (int) Math.round(medieCurentaDouble);
        for(int i = medieCurenta + 1; i <= 10 ; i++){
            VariantaMedie variantaMedie = new VariantaMedie();
            for(int nrNote = 1 ; nrNote <= 10 && !variantaMedie.ePosibil ; nrNote ++){
                variantaMedie = new VariantaMedie(sumaNote, nrNoteExistete, nrNote, i, teza);
            }
            if(!variantaMedie.ePosibil){
                break;
            } else {
                variantaMedii.add(variantaMedie);
            }
        }
        return variantaMedii;
    }

    //CU POSIBILA TEZA
    public static ArrayList<VariantaMedie> getVarianteMedieWithPossibleTeza(int sumaNote,
                                                                            int nrNoteExistete){
        ArrayList<VariantaMedie> variantaMedii = new ArrayList<VariantaMedie>();
        double medieCurentaDouble = sumaNote / nrNoteExistete;
        int medieCurenta = (int) Math.round(medieCurentaDouble);
        for(int i = medieCurenta + 1; i <= 10 ; i++){
            for(int teza = 1 ; teza <= 10; teza ++ ) {
                VariantaMedie variantaMedie = new VariantaMedie();
                for (int nrNote = 1; nrNote <= 10 && !variantaMedie.ePosibil; nrNote++) {
                    variantaMedie = new VariantaMedie(sumaNote, nrNoteExistete, nrNote, i, teza);
                }
                if (variantaMedie.ePosibil) {
                    variantaMedie.teza = teza;
                    variantaMedii.add(variantaMedie);
                }
            }
        }
        return variantaMedii;
    }


}
