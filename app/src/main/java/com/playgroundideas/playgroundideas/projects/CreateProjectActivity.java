package com.playgroundideas.playgroundideas.projects;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.playgroundideas.playgroundideas.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateProjectActivity extends AppCompatActivity {


    public static final String[] mCountries = {"Australia","American","South Africa","China"};
    public static final String[] mCurrencies = {"AUD","CNY","USD","GPB"};
    Spinner mSpinnerCountries = null;
    Spinner mSpinnerCurrencies = null;
    Button mSetStart;
    Button mSetEnd;
    TextView mShowStart;
    TextView mShowEnd;
    Calendar mStartCalendar;
    Calendar mEndCalendar;
    Date mDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        add_list();
        mStartCalendar = Calendar.getInstance();
        Date date = mStartCalendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = format.format(date);

        mShowStart = (TextView) findViewById(R.id.show_start);
        mShowEnd = (TextView) findViewById(R.id.show_end);
        mShowStart.setText(dateString);
        mShowEnd.setText(dateString);
        mEndCalendar = Calendar.getInstance();
        setOnSetDateClick();
    }

    DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mStartCalendar.set(Calendar.YEAR, year);
            mStartCalendar.set(Calendar.MONTH, monthOfYear);
            mStartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Date date = mStartCalendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = format.format(date);

            mShowStart.setText(dateString);
        }
    };

    DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Calendar temp = Calendar.getInstance();
            temp.set(Calendar.YEAR, year);
            temp.set(Calendar.MONTH, monthOfYear);
            temp.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            if (temp.compareTo(mStartCalendar) < 0) {
                mEndCalendar.set(mStartCalendar.get(Calendar.YEAR),
                        mStartCalendar.get(Calendar.MONTH),
                        mStartCalendar.get(Calendar.DAY_OF_MONTH));
                Toast.makeText(CreateProjectActivity.this, "End date must be after start date!",
                        Toast.LENGTH_LONG).show();
                return;
            }
            mEndCalendar.set(Calendar.YEAR, year);
            mEndCalendar.set(Calendar.MONTH, monthOfYear);
            mEndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Date date = mEndCalendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = format.format(date);

            mShowEnd.setText(dateString);
        }
    };

    private void setOnSetDateClick() {
        mSetStart = (Button) findViewById(R.id.set_start);
        mSetEnd = (Button) findViewById(R.id.set_end);

        mSetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateProjectActivity.this, startDateSetListener, mStartCalendar
                        .get(Calendar.YEAR), mStartCalendar.get(Calendar.MONTH),
                        mStartCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mSetEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateProjectActivity.this, endDateSetListener, mEndCalendar
                        .get(Calendar.YEAR), mEndCalendar.get(Calendar.MONTH),
                        mEndCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    //spinner initial function
    private void add_list() {
       mSpinnerCountries = (Spinner) findViewById(R.id.spinner_countires);
        ArrayAdapter<String>adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,mCountries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCountries.setAdapter(adapter);
        mSpinnerCountries.setSelection(0);

        mSpinnerCurrencies = (Spinner) findViewById(R.id.spinner_currenies);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,mCurrencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCurrencies.setAdapter(adapter);
        mSpinnerCurrencies.setSelection(1);
    }

}
