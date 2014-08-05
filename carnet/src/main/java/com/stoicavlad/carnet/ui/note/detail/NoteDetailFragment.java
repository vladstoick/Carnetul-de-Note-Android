package com.stoicavlad.carnet.ui.note.detail;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract;

import java.util.List;

public class NoteDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        NoteDetailAdapter.OnDeleteListener{


    private static final int MATERIE_LOADER = 0;

    private static final String ARG_MATERIE_ID = "MATERIE_ID";
    int materieId;

    private NoteDetailAdapter mCursorAdapter;
    private ListView mListView;

    public NoteDetailFragment() {
        // Required empty public constructor
    }

    public static NoteDetailFragment newInstance(int materieId) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MATERIE_ID, materieId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(MATERIE_LOADER, null, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            materieId = getArguments().getInt(ARG_MATERIE_ID);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_detail, container, false);
        mListView = (ListView) view.findViewById(R.id.list);
        mCursorAdapter = new NoteDetailAdapter(getActivity(),null,0,this);
        mListView.setAdapter(mCursorAdapter);
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                getActivity(),
                CarnetContract.MaterieEntry.buildNoteUri(materieId),
                CarnetContract.NoteEntry.COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mCursorAdapter.swapCursor(null);
    }

    @Override
    public void didDeleteNotaWithId(int id) {
        getActivity().getContentResolver()
                .notifyChange(CarnetContract.MaterieEntry.buildNoteUri(materieId), null, false);
        getActivity().getContentResolver()
                .notifyChange(CarnetContract.MaterieEntry.CONTENT_URI, null, false);
    }
}
