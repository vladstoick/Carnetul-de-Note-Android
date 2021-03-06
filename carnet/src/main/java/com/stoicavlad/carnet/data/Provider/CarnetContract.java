package com.stoicavlad.carnet.data.provider;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Vlad on 30-Jul-14.
 */
public class CarnetContract {

    public static final String CONTENT_AUTHORITY = "com.stoicavlad.carnet.data.contentprovider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ABSENTE = "absente";
    public static final String PATH_NOTE = "note";
    public static final String PATH_MATEIRE = "materie";

    public static final class MaterieEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MATEIRE).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_MATEIRE;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_MATEIRE;

        public static final String TABLE_NAME = "materie";
        public static final String COLUMN_NAME = "nume";
        public static final String COLUMN_TEZA = "teza";

        //SIMPLE

        public static final String [] COLUMNS_SIMPLE = {
                TABLE_NAME + "." + _ID,
                COLUMN_NAME,
                "AVG ( " +  NoteEntry.TABLE_NAME + "." + NoteEntry.COLUMN_VALUE + " ) "
        };


        public static final int COL_SIMPLE_ID = 0 ;
        public static final int COL_SIMPLE_NAME =  1 ;
        public static final int COL_SIMPLE_MEDIE = 2;

        //DETAIL

        public static final String[] COLUMNS_DETAIL = {
                COLUMN_NAME,
                COLUMN_TEZA,
                "AVG ( " +  NoteEntry.TABLE_NAME + "." + NoteEntry.COLUMN_VALUE + " ) ",
        };

        public static final int COL_DETAIL_NAME = 0;
        public static final int COL_DETAIL_TEZA = 1;
        public static final int COL_DETAIL_MEDIE = 2;

        /// DETAILMEDIE

        public static final String[] COLUMNS_DETAIL_MEDIE = {
                COLUMN_TEZA,
                "SUM ( " +  NoteEntry.TABLE_NAME + "." + NoteEntry.COLUMN_VALUE + " ) ",
                "COUNT ( " +  NoteEntry.TABLE_NAME + "." + NoteEntry.COLUMN_VALUE + " ) "
        };

        public static final int COL_DETAIL_MEDIE_TEZA = 0;
        public static final int COL_DETAIL_MEDIE_SUMA = 1;
        public static final int COL_DETAIL_MEDIE_COUNT_NOTE = 2;

        // MEDIE

        public static final String[] COLUMNS_MEDIE = {
                "AVG ( " +  NoteEntry.TABLE_NAME + "." + NoteEntry.COLUMN_VALUE + " ) ",
                COLUMN_TEZA,

        };

        public static final int COL_MEDIE_NOTE = 0;
        public static final int COL_MEDIE_TEZA = 1;

        //ALL

        public static final String[] COLUMNS_WITH_NOTE = {
                TABLE_NAME + "." + _ID,
                COLUMN_NAME,
                COLUMN_TEZA,
                "AVG ( " +  NoteEntry.TABLE_NAME + "." + NoteEntry.COLUMN_VALUE + " ) ",
                "GROUP_CONCAT(" + NoteEntry.TABLE_NAME + "." + NoteEntry.COLUMN_VALUE + " , \', \')"
        };


        public static final int COL_ID = 0 ;
        public static final int COL_NAME =  1 ;
        public static final int COL_TEZA = 2;
        public static final int COL_MEDIE = 3;
        public static final int COL_NOTE = 4;

        public static Uri buildMaterieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static Uri buildNoteUri(long id){
            return  CONTENT_URI.buildUpon().appendEncodedPath("note")
                    .appendEncodedPath(String.valueOf(id)).build();
        }
    }

    public static final class NoteEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_NOTE).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_NOTE;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_NOTE;

        public static final String TABLE_NAME = "note";

        public static final String COLUMN_VALUE = "value";
        public static final String COLUMN_MATERIE_ID = "materie_id";

        public static final String[] COLUMNS = {
                TABLE_NAME + "." + _ID,
                COLUMN_VALUE,
                COLUMN_MATERIE_ID
        };

        public static final int COL_ID = 0 ;
        public static final int COL_VALUE =  1 ;
        public static final int COL_MATERIE_ID = 2;

        public static Uri buildNotaUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class AbsentaEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ABSENTE).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_ABSENTE;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_ABSENTE;

        public static final String TABLE_NAME = "absente";

        public static final String COLUMN_DATE = "date";

        public static final String[] COLUMNS = {
                TABLE_NAME + "." + _ID,
                COLUMN_DATE
        };

        public static final int COL_ID = 0 ;
        public static final int COL_DATE =  1 ;


        public static Uri buildAbsentaUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
