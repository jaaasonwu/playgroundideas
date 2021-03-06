package com.playgroundideas.playgroundideas.projects;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.playgroundideas.playgroundideas.R;

import java.text.SimpleDateFormat;

public class DetailProjectActivity extends AppCompatActivity {

    private TextView titleView;
    private TextView contryView;
    private TextView descriptionView;
    private TextView startDateView;
    private TextView endDateView;
    private ImageView imageView;
    private TextView currentFund;
    private TextView goalFund;
    private TextView dayLeft;
    private TextView emailAddressView;
    private FloatingActionButton mShare;
    private FloatingActionButton mEmailShare;
    private FloatingActionButton mFacebookShare;
    private Boolean isOpen = false;
    private ProjectItem test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project);

        //using parcel to pass projectItem from project fragment
        test = (ProjectItem) getIntent().getParcelableExtra("project_data");

        //initial the view
        titleView =(TextView) findViewById(R.id.title_project_detail);
        contryView = (TextView) findViewById(R.id.title_project_country);
        descriptionView = (TextView) findViewById(R.id.description);
        startDateView = (TextView) findViewById(R.id.project_start_date);
        endDateView = (TextView) findViewById(R.id.project_end_date);
        imageView =(ImageView) findViewById(R.id.project_detail_image);
        emailAddressView = (TextView) findViewById(R.id.paypal_email);
        currentFund = (TextView) findViewById(R.id.current_funding);
        goalFund = (TextView) findViewById(R.id.goal_funding);
        dayLeft = (TextView) findViewById(R.id.percentage);
        titleView.setText(test.getProjectTtile());
        contryView.setText(test.getCountry());
        descriptionView.setText(test.getProjectDescription());
        String ProjectDate = new SimpleDateFormat("dd-MM-yyyy").format(test.getStartDate());
        startDateView.setText(ProjectDate);
        endDateView.setText(ProjectDate);
        emailAddressView.setText(test.getEmailAddress());
        mEmailShare = (FloatingActionButton) findViewById(R.id.email_share);
        mFacebookShare = (FloatingActionButton) findViewById(R.id.facebook_share);
        mShare = (FloatingActionButton) findViewById(R.id.shareButton);

        //initial floating button
        Iconify.with(new MaterialModule()).with(new MaterialCommunityModule());
        mShare.setImageDrawable(new IconDrawable(this, MaterialIcons.md_share)
                .colorRes(R.color.white).actionBarSize());
        mEmailShare.setImageDrawable(new IconDrawable(this, MaterialIcons.md_email)
                        .colorRes(R.color.white).actionBarSize());
        mFacebookShare.setImageDrawable(new IconDrawable(this, MaterialCommunityIcons.mdi_facebook)
                .colorRes(R.color.white).actionBarSize());
        currentFund.setText("$" + Integer.toString(test.getmCurrentFund()));
        goalFund.setText("$" + Integer.toString(test.getmGoalFund()));
        dayLeft.setText(Integer.toString(test.getmDayleft()));

        //shareButton listener
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen) {
                    mEmailShare.setVisibility(view.INVISIBLE);
                    mFacebookShare.setVisibility(view.INVISIBLE);
                    isOpen = false;
                } else {
                    mEmailShare.setVisibility(view.VISIBLE);
                    mFacebookShare.setVisibility(view.VISIBLE);
                    isOpen = true;
                }
            }
        });

        contactOwner();
        shareListener();
        Glide.with(this).load(test.getImageUrl()).into(imageView);
    }


    //share function
    public void shareListener() {

        //share project via email
        mEmailShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailProjectActivity.this,"Sharing project through Email",Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_SUBJECT,"Project Sharing");
                i.putExtra(Intent.EXTRA_TEXT,test.getProjectTtile() + "\n\n" + test.getProjectDescription());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(Intent.createChooser(i,"Share peoject through mail applications..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DetailProjectActivity.this,"There are no email application installed.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //share project via facebook
        mFacebookShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailProjectActivity.this,"Sharing project through Facebook",Toast.LENGTH_LONG).show();
                ShareDialog shareDialog;
                FacebookSdk.sdkInitialize(getApplicationContext());
                shareDialog = new ShareDialog(DetailProjectActivity.this);

                imageView.setDrawingCacheEnabled(true);
                Bitmap bitmap = imageView.getDrawingCache();

                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bitmap)
                        .build();

                SharePhotoContent photoContent = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();

                shareDialog.show(photoContent);
            }
        });
    }


    //long click to contact with creator
    public void contactOwner() {
        emailAddressView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(DetailProjectActivity.this,"Sharing project through Email",Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL,new String[] {test.getEmailAddress()});
                i.putExtra(Intent.EXTRA_SUBJECT,"Project Consult");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(Intent.createChooser(i,"Contact with project owner through mail applications..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DetailProjectActivity.this,"There are no email application installed.",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
}
