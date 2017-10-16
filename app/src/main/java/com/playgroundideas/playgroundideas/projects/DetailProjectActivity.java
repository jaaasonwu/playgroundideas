package com.playgroundideas.playgroundideas.projects;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.playgroundideas.playgroundideas.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.facebook.FacebookSdk;

public class DetailProjectActivity extends AppCompatActivity {

    private TextView titleView;
    private TextView contryView;
    private TextView descriptionView;
    private TextView startDateView;
    private TextView endDateView;
    private ImageView imageView;
    private ProjectItem sampleProject;
    private FloatingActionButton mShare;
    private TextView mEmailShare;
    private TextView mFacebookShare;
    private Boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project);

        initial();

        titleView =(TextView) findViewById(R.id.title_project_detail);
        contryView = (TextView) findViewById(R.id.title_project_country);
        descriptionView = (TextView) findViewById(R.id.description);
        startDateView = (TextView) findViewById(R.id.project_start_date);
        endDateView = (TextView) findViewById(R.id.project_end_date);
        imageView =(ImageView) findViewById(R.id.project_detail_image);

        titleView.setText(sampleProject.getProjectTtile());
        contryView.setText(sampleProject.getCountry());
        descriptionView.setText(sampleProject.getProjectDescription());
        String ProjectDate = new SimpleDateFormat("dd-MM-yyyy").format(sampleProject.getStartDate());
        startDateView.setText(ProjectDate);
        endDateView.setText(ProjectDate);
        emailAddressView.setText(sampleProject.getEmailAddress());
        mEmailShare = (TextView) findViewById(R.id.email_share);
        mFacebookShare = (TextView) findViewById(R.id.facebook_share);
        mShare = (FloatingActionButton) findViewById(R.id.shareButton);
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
        Glide.with(this).load(sampleProject.getImageUrl()).into(imageView);
    }

    private void initial() {

        Calendar mCalendar = Calendar.getInstance();
        Date sampleDate = mCalendar.getTime();
        String sampleEmailAddress = "platypustestplatyground@gmail.com";
        String sampleCountry = "Australia";
        String sampleCurrency = "AUD";
        String sampleDescription = "It is my first project. I like to build playground for children.";
        String sampleTitle = "My Project";
        String sampleImageUrl = "https://playgroundideas.org/wp-content/uploads/2017/02/IMGP0204-1024x768.jpg";

        sampleProject = new ProjectItem(sampleTitle,sampleDate,sampleDate,sampleEmailAddress
                ,sampleCountry,sampleCurrency,sampleDescription,sampleImageUrl);

    }

    public void shareListener() {
        mEmailShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailProjectActivity.this,"Sharing project through Email",Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_SUBJECT,"Project Sharing");
                i.putExtra(Intent.EXTRA_TEXT,sampleProject.getProjectTtile() + "\n" + sampleProject.getProjectDescription());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(Intent.createChooser(i,"Share peoject through mail applications..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DetailProjectActivity.this,"There are no email application installed.",Toast.LENGTH_SHORT).show();
                }
            }
        });

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

    public void contactOwner() {
        emailAddressView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(DetailProjectActivity.this,"Sharing project through Email",Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL,new String[] {sampleProject.getEmailAddress()});
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
