package com.stoicavlad.carnet.data.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.stoicavlad.carnet.data.provider.CarnetContract.MaterieEntry;
import com.stoicavlad.carnet.data.provider.CarnetContract.AbsentaEntry;

public class CarnetDeNoteProvider extends ContentProvider {

    CarnetSqlHelper mCarnetSqlHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int  ABSENTE = 100;
    private static final int ABSENTE_ID = 101;

    private static final int MATERIE = 200;
    private static final int MATERIE_ID = 201;

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CarnetContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, CarnetContract.PATH_ABSENTE, ABSENTE); //100
        matcher.addURI(authority, CarnetContract.PATH_ABSENTE + "/#", ABSENTE_ID); //101

        matcher.addURI(authority, CarnetContract.PATH_MATEIRE, MATERIE); //200
        matcher.addURI(authority, CarnetContract.PATH_MATEIRE + "/#" , MATERIE_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mCarnetSqlHelper = new CarnetSqlHelper(getContext());
        return false;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ABSENTE: return CarnetContract.AbsentaEntry.CONTENT_TYPE;
            case ABSENTE_ID: return CarnetContract.AbsentaEntry.CONTENT_ITEM_TYPE;
            case MATERIE: return CarnetContract.MaterieEntry.CONTENT_TYPE;
            case MATERIE_ID: return CarnetContract.MaterieEntry.CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)){
            case ABSENTE:{
                retCursor = mCarnetSqlHelper.getReadableDatabase().query(
                        AbsentaEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case ABSENTE_ID:{
                retCursor = mCarnetSqlHelper.getReadableDatabase().query(
                        AbsentaEntry.TABLE_NAME,
                        projection,
                        AbsentaEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case MATERIE:{
                retCursor = mCarnetSqlHelper.getReadableDatabase().query(
                        MaterieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case MATERIE_ID:{
                retCursor = mCarnetSqlHelper.getReadableDatabase().query(
                        MaterieEntry.TABLE_NAME,
                        projection,
                        MaterieEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }



    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mCarnetSqlHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case ABSENTE: {
                long _id = db.insert(AbsentaEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = AbsentaEntry.buildAbsentaUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case MATERIE:{
                long _id = db.insert(MaterieEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MaterieEntry.buildMaterieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null, false);
        return returnUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mCarnetSqlHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MATERIE:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MaterieEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mCarnetSqlHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case ABSENTE:
                rowsDeleted = db.delete(AbsentaEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MATERIE:
                rowsDeleted = db.delete(MaterieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MATERIE_ID:
                long id = ContentUris.parseId(uri);
                String newSelection = CarnetContract.MaterieEntry._ID + " = ? ";
                String newSelectionArgs = String.valueOf(id);
                rowsDeleted = db.delete(MaterieEntry.TABLE_NAME, newSelection
                        , new String[]{newSelectionArgs});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        final SQLiteDatabase db = mCarnetSqlHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case ABSENTE:
                rowsUpdated = db.update(CarnetContract.AbsentaEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
