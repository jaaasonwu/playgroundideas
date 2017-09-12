package com.playgroundideas.playgroundideas.projects;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.playgroundideas.playgroundideas.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailProjectActivity extends AppCompatActivity {

    private TextView titleView;
    private TextView contryView;
    private TextView currencyView;
    private TextView descriptionView;
    private TextView startDateView;
    private TextView endDateView;
    private ImageView imageView;
    private TextView emailAddressView;
    private ProjectItem sampleProject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project);

        intial();

        titleView =(TextView) findViewById(R.id.title_project_detail);
        contryView = (TextView) findViewById(R.id.title_project_country);
        currencyView = (TextView) findViewById(R.id.currency);
        descriptionView = (TextView) findViewById(R.id.description);
        startDateView = (TextView) findViewById(R.id.project_start_date);
        endDateView = (TextView) findViewById(R.id.project_end_date);
        imageView =(ImageView) findViewById(R.id.project_detail_image);
        emailAddressView = (TextView) findViewById(R.id.paypal_email);

        titleView.setText(sampleProject.getProjectTtile());
        contryView.setText(sampleProject.getCountry());
        currencyView.setText(sampleProject.getCurrency());
        descriptionView.setText(sampleProject.getProjectDescription());
        String ProjectDate = new SimpleDateFormat("dd-MM-yyyy").format(sampleProject.getStartDate());
        startDateView.setText(ProjectDate);
        endDateView.setText(ProjectDate);
        emailAddressView.setText(sampleProject.getEmailAddress());
        Glide.with(this).load(sampleProject.getImageUrl()).into(imageView);
    }

    private void intial() {

        Calendar mCalendar = Calendar.getInstance();
        Date sampleDate = mCalendar.getTime();
        String sampleEmailAddress = "playpus@gmail.com";
        String sampleCountry = "Australia";
        String sampleCurrency = "AUD";
        String sampleDescription = "It is my first project. I like to build playground for children.";
        String sampleTitle = "My Project";
        String sampleImageUrl = "https://playgroundideas.org/wp-content/uploads/2017/02/IMGP0204-1024x768.jpg";

        sampleProject = new ProjectItem(sampleTitle,sampleDate,sampleDate,sampleEmailAddress
                ,sampleCountry,sampleCurrency,sampleDescription,sampleImageUrl);

    }
}
