package com.playgroundideas.playgroundideas.projects;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.playgroundideas.playgroundideas.BuildConfig;
import com.playgroundideas.playgroundideas.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailProjectActivity_my extends AppCompatActivity {

    private TextView titleView;
    private TextView contryView;
    private TextView currencyView;
    private TextView descriptionView;
    private TextView startDateView;
    private TextView endDateView;
    private ImageView imageView;
    private TextView emailAddressView;
    private ProjectItem sampleProject;
    private FloatingActionButton mShare;
    private TextView mEmailShare;
    private TextView mFacebookShare;
    private Boolean isOpen = false;
    private Boolean isOpenPopupView = false;
    private Button mAddPhoto;
    private Button mAddPhotoFromGallery;
    private Button mAddPhotoFromCamera;
    private PopupWindow mPopWindow;
    private File file;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int RESULT_LOAD_IMG = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project_my);

        intial();

        titleView =(TextView) findViewById(R.id.title_project_detail);
        contryView = (TextView) findViewById(R.id.title_project_country);
        currencyView = (TextView) findViewById(R.id.currency);
        descriptionView = (TextView) findViewById(R.id.description);
        startDateView = (TextView) findViewById(R.id.project_start_date);
        endDateView = (TextView) findViewById(R.id.project_end_date);
        imageView =(ImageView) findViewById(R.id.project_detail_image);
        emailAddressView = (TextView) findViewById(R.id.paypal_email);
        mAddPhoto = (Button) findViewById(R.id.button_new_photo);
        titleView.setText(sampleProject.getProjectTtile());
        contryView.setText(sampleProject.getCountry());
        currencyView.setText(sampleProject.getCurrency());
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
        mAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpenPopupView) {
                    mPopWindow.dismiss();
                    isOpenPopupView = false;
                } else {
                    addPhotoBy();
                }
            }
        });
        contactOwner();
        shareListener();
        Glide.with(this).load(sampleProject.getImageUrl()).into(imageView);
    }

    private void intial() {

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
                Toast.makeText(DetailProjectActivity_my.this,"Sharing project through Email",Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_SUBJECT,"Project Sharing");
                i.putExtra(Intent.EXTRA_TEXT,sampleProject.getProjectTtile() + "\n" + sampleProject.getProjectDescription());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(Intent.createChooser(i,"Share peoject through mail applications..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DetailProjectActivity_my.this,"There are no email application installed.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mFacebookShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailProjectActivity_my.this,"Sharing project through Facebook",Toast.LENGTH_LONG).show();
                ShareDialog shareDialog;
                FacebookSdk.sdkInitialize(getApplicationContext());
                shareDialog = new ShareDialog(DetailProjectActivity_my.this);

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
                Toast.makeText(DetailProjectActivity_my.this,"Sharing project through Email",Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL,new String[] {sampleProject.getEmailAddress()});
                i.putExtra(Intent.EXTRA_SUBJECT,"Project Consult");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(Intent.createChooser(i,"Contact with project owner through mail applications..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DetailProjectActivity_my.this,"There are no email application installed.",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    public void addPhotoBy() {
        View conternView = LayoutInflater.from(DetailProjectActivity_my.this).inflate(R.layout.project_new_photo_popupview,null);
        mPopWindow = new PopupWindow(conternView);
        mPopWindow.setContentView(conternView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mAddPhotoFromGallery = (Button) conternView.findViewById(R.id.buttonGallery);
        mAddPhotoFromCamera = (Button) conternView.findViewById(R.id.buttonCamera);
        mPopWindow.showAtLocation(conternView, Gravity.CENTER, 0, 0);
        isOpenPopupView = mPopWindow.isShowing();
        addPhoto();
    }

    public void addPhoto() {

        mAddPhotoFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = getOutputPhotoFile();
                Uri photoUri = FileProvider.getUriForFile(DetailProjectActivity_my.this,BuildConfig.APPLICATION_ID + ".fileProvider", file);
                i.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
                mPopWindow.dismiss();
            }
        });

        mAddPhotoFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,RESULT_LOAD_IMG);
                mPopWindow.dismiss();
            }
        });
    }

    private File getOutputPhotoFile() {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getPackageName());
        if (!file.exists()) {
                file.mkdirs();
            }
        File output = new File(file, System.currentTimeMillis() + ".jpg");
        try {
            if (output.exists()) {
                output.delete();
            }
            output.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == RESULT_LOAD_IMG || resultCode == RESULT_OK || data != null) {
            try {
                final Uri imageUri = data.getData();
                Glide.with(DetailProjectActivity_my.this).load(imageUri).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(DetailProjectActivity_my.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        } else if(requestCode == REQUEST_IMAGE_CAPTURE|| resultCode == RESULT_OK || data != null){
            try{
                Glide.with(DetailProjectActivity_my.this).load(data.getExtras().get("data")).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(DetailProjectActivity_my.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(DetailProjectActivity_my.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}
