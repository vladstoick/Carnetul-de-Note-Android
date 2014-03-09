package com.stoicavlad.carnet.ui.setup;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.stoicavlad.carnet.R;


import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SetupFragment extends Fragment{
    @InjectView(R.id.list)
    ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private String[] materii;


    private OnFragmentInteractionListener mListener;

    public SetupFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        materii = getResources().getStringArray(R.array.materii_default);
        Activity activity = getActivity();
        if(activity != null){
            mAdapter = new ArrayAdapter<String>(activity,R.layout.list_row_setup,
                    android.R.id.text1,materii);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup, container, false);
        ButterKnife.inject(this, view);
        mListView.setAdapter(mAdapter);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.setup,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_next:{
                SparseBooleanArray positions = mListView.getCheckedItemPositions();
                ArrayList<String> rezultat = new ArrayList<String>();
                for(int i = 0 ; i < (positions != null ? positions.size() : 0); i++){
                    rezultat.add(materii[positions.keyAt(i)]);
                }
                mListener.onNextSelected(rezultat);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
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

    public interface OnFragmentInteractionListener{
        public void onNextSelected(ArrayList<String> materiiDeAdaugat);
    }
}