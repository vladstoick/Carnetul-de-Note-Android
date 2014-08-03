package com.stoicavlad.carnet.ui.absente;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract.AbsentaEntry;

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
        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });
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
        return new CursorLoader(
                getActivity(),
                AbsentaEntry.CONTENT_URI,
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
