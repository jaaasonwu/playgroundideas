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
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.playgroundideas.playgroundideas.BuildConfig;
import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.model.Project;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailProjectActivity_my extends AppCompatActivity {

    private TextView titleView;
    private TextView contryView;
    private TextView descriptionView;
    private TextView startDateView;
    private TextView endDateView;
    private TextView currentFund;
    private TextView goalFund;
    private TextView dayLeft;
    private ImageView imageView;
    private TextView emailAddressView;
    private FloatingActionButton mShare;
    private FloatingActionButton mEmailShare;
    private FloatingActionButton mFacebookShare;
    private Boolean isOpen = false;
    private Boolean isOpenPopupView = false;
    private Button mAddPhoto;
    private Button mAddPhotoFromGallery;
    private Button mAddPhotoFromCamera;
    private PopupWindow mPopWindow;
    private ProjectItem test;
    private File file;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int RESULT_LOAD_IMG = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project_my);

        test = (ProjectItem) getIntent().getParcelableExtra("project_data");
        titleView =(TextView) findViewById(R.id.title_project_detail);
        contryView = (TextView) findViewById(R.id.title_project_country);
        descriptionView = (TextView) findViewById(R.id.description);
        startDateView = (TextView) findViewById(R.id.project_start_date);
        endDateView = (TextView) findViewById(R.id.project_end_date);
        imageView =(ImageView) findViewById(R.id.project_detail_image);
        emailAddressView = (TextView) findViewById(R.id.paypal_email);
        mAddPhoto = (Button) findViewById(R.id.button_new_photo);
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
        currentFund.setText(Integer.toString(test.getmCurrentFund()));
        goalFund.setText(Integer.toString(test.getmGoalFund()));
        dayLeft.setText(Integer.toString(test.getmDayleft()));
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
        Glide.with(this).load(test.getImageUrl()).into(imageView);
    }


    public void shareListener() {
        mEmailShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailProjectActivity_my.this,"Sharing project through Email",Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_SUBJECT,"Project Sharing");
                i.putExtra(Intent.EXTRA_TEXT,test.getProjectTtile() + "\n\n" + test.getProjectDescription());
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
                i.putExtra(Intent.EXTRA_EMAIL,new String[] {test.getEmailAddress()});
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
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(true);
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
                if(imageUri != null) {
                    Glide.with(DetailProjectActivity_my.this).load(imageUri).into(imageView);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(DetailProjectActivity_my.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        } else if(requestCode == REQUEST_IMAGE_CAPTURE|| resultCode == RESULT_OK || data != null){
            try{
                if(data.getExtras().get("data") != null) {
                    Glide.with(DetailProjectActivity_my.this).load(data.getExtras().get("data")).into(imageView);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(DetailProjectActivity_my.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(DetailProjectActivity_my.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}
