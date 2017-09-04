package com.playgroundideas.playgroundideas.projects;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.playgroundideas.playgroundideas.R;


public class ProjectNew extends Fragment {

    /*public static final String[] mCountries = {"Australia","American","South Africa","China"};
    public static final String[] mCurrencies = {"AUD","CNY","USD","GPB"};
    Spinner sp = null;*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_project_new, container, false);
        //add_list();
        return rootview;
    }

    /*private void add_list() {
        sp = (Spinner) getView().findViewById(R.id.spinner_countires);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,mCountries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        sp = (Spinner) getView().findViewById(R.id.spinner_currenies);
        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,mCurrencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
    }
*/

}
