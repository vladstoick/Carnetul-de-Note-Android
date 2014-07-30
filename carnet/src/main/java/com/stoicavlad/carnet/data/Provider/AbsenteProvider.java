package com.stoicavlad.carnet.data.provider;

import android.net.Uri;

/**
 * Created by Vlad on 30-Jul-14.
 */
public class AbsenteProvider {

    public static final String CONTENT_AUTHORITY = "com.stoicavlad.carnet.data.contentprovider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ABSENTE = "location";
    public static final String PATH_NOTE = "absente";

}
