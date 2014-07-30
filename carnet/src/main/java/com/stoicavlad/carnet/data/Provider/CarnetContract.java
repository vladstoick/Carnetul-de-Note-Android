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
