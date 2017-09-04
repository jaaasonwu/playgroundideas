package com.playgroundideas.playgroundideas.projects;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.playgroundideas.playgroundideas.R;

public class CreateProjectActivity extends AppCompatActivity {


    /*public static final String[] mCountries = {"Australia","American","South Africa","China"};
    public static final String[] mCurrencies = {"AUD","CNY","USD","GPB"};
    Spinner sp = null;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.add(R.id.fragment_new_project, new ProjectNew(),"ONE");
        tx.commit();
        //add_list();
    }

    /*private void add_list() {
       sp = (Spinner) getSupportFragmentManager().findFragmentById(R.id.fragment_new_project).getView().findViewById(R.id.spinner_countires);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,mCountries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        sp = (Spinner) getSupportFragmentManager().findFragmentById(R.id.fragment_new_project).getView().findViewById(R.id.spinner_currenies);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,mCurrencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
    }*/
}
