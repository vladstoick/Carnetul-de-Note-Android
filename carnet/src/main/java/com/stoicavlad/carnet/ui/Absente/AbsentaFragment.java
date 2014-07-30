package com.stoicavlad.carnet.ui.absente;

import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract.AbsentaEntry;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AbsentaFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    @InjectView(R.id.list) public ListView mListView;
    private CursorAdapter mAdapter;
    private static final int ABSENTE_LOADER = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_absenta, container, false);
        ButterKnife.inject(this, view);
        View headerView = inflater.inflate(R.layout.header_absenta_button,null);
        Button mCalculate = (Button) headerView.findViewById(R.id.button);
//        mCalculate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO
//            }

        mAdapter = new AbsentaAdapter(getActivity(), null, 0);
        mListView.addHeaderView(headerView);
        mListView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(ABSENTE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Sort order:  Ascending, by date.
        String sortOrder = AbsentaEntry.COLUMN_DATE + " ASC";
        Uri uri = AbsentaEntry.CONTENT_URI;
        return new CursorLoader(
                getActivity(),
                uri,
                AbsentaEntry.COLUMNS,
                null,
                null,
                sortOrder
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }
}
