package com.stoicavlad.carnet.ui.note;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.provider.CarnetContract;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NoteListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
    ComplexNoteAdapter.OnOverflowButtonInterface{

    public interface OnFragmentInteractionListener {
        public void showNotaDetail(int materieId);
    }
    private OnFragmentInteractionListener mListener;

    @InjectView(R.id.list) ListView mListView;

    ComplexNoteAdapter mCursorAdapter;

    private static final int MATERIE_LOADER = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notefragment, container, false);
        ButterKnife.inject(this,view);
        // Set the adapter
        mCursorAdapter = new ComplexNoteAdapter(getActivity(),null,0);
        mCursorAdapter.setListener(this);
        mListView.setAdapter(mCursorAdapter);
        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(mListener != null){
                    Cursor cursor = mCursorAdapter.getCursor();
                    cursor.moveToPosition(position);
                    int materieId = cursor.getInt(CarnetContract.MaterieEntry.COL_ID);
                    String materieName = cursor.getString(CarnetContract.MaterieEntry.COL_NAME);
                    mListener.showNotaDetail(materieId);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(MATERIE_LOADER, null, this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                getActivity(),
                CarnetContract.MaterieEntry.CONTENT_URI,
                CarnetContract.MaterieEntry.COLUMNS_WITH_NOTE,
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
    public void showAddTezaDialogFragment(int id, String name) {
        DialogFragment dialogFragment = AddTezaDialogFragment.newInstance(id, name);
        dialogFragment.show(getActivity().getFragmentManager(), "ADD_NOTA");
    }
}
