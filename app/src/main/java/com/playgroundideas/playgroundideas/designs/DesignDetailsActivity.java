package com.playgroundideas.playgroundideas.designs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.playgroundideas.playgroundideas.MainActivity;
import com.playgroundideas.playgroundideas.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DesignDetailsActivity extends AppCompatActivity {
    private FloatingActionButton floatingActionPlus;
    private FloatingActionButton floatingActionEmailShare;
    private FloatingActionButton floatingActionFacebookShare;
    private Animation fabOpenShare, fabCloseShare, fabRClockwise, fabRAnticlockwise;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private String imgUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_details);
        TextView textViewDesc = (TextView) findViewById(R.id.imageTitle);
        final String name = getIntent().getExtras().getString("designName");
        textViewDesc.setText(name);
        ImageView imageView = (ImageView) findViewById(R.id.imageDetails);
        imgUrl = getIntent().getExtras().getString("designDetail");
        Glide.with(getApplicationContext()).load(imgUrl)
                .into(imageView);

        floatingActionPlus = (FloatingActionButton) findViewById(R.id.fab_share_detail);
        floatingActionEmailShare = (FloatingActionButton) findViewById(R.id.fab_email_share_detail);
        floatingActionFacebookShare = (FloatingActionButton) findViewById(R.id.fab_facebook_share_detail);

        Iconify.with(new MaterialModule());

        floatingActionPlus.setImageDrawable(new IconDrawable(this, MaterialIcons.md_add)
                .colorRes(R.color.black).actionBarSize());
        floatingActionEmailShare.setImageDrawable(new IconDrawable(this, MaterialIcons.md_email)
                .colorRes(R.color.black).actionBarSize());
        floatingActionFacebookShare.setImageDrawable(new IconDrawable(this, MaterialIcons.md_share)
                .colorRes(R.color.black).actionBarSize());
        fabOpenShare = AnimationUtils.loadAnimation(this, R.anim.fab_open_share);
        fabRClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise);
        fabCloseShare = AnimationUtils.loadAnimation(this, R.anim.fab_close_share);
        fabRAnticlockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise);

        floatingActionPlus.setOnClickListener(new View.OnClickListener() {
            boolean isOpen = false;
            @Override
            public void onClick(View view) {
                if(isOpen){
                    floatingActionEmailShare.startAnimation(fabCloseShare);
                    floatingActionFacebookShare.startAnimation(fabCloseShare);
                    floatingActionPlus.startAnimation(fabRAnticlockwise);
                    floatingActionEmailShare.setClickable(false);
                    floatingActionFacebookShare.setClickable(false);
                    isOpen = false;
                }
                else{
                    floatingActionEmailShare.startAnimation(fabOpenShare);
                    floatingActionFacebookShare.startAnimation(fabOpenShare);
                    floatingActionPlus.startAnimation(fabRClockwise);
                    floatingActionEmailShare.setClickable(true);
                    floatingActionFacebookShare.setClickable(true);
                    isOpen = true;
                }
            }
        });


        floatingActionEmailShare.setOnClickListener(new View.OnClickListener(){
            Intent intent = null, chooser = null;
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                String[] to = {};
                intent.putExtra(Intent.EXTRA_EMAIL, to);
                intent.putExtra(Intent.EXTRA_SUBJECT, name + " Sharing");
                intent.putExtra(Intent.EXTRA_TEXT, name);
                intent.setType("message/rfc822");
                chooser = Intent.createChooser(intent, "Send Email");
                startActivity(chooser);
            }
        });

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException e) {
                Log.e("Exception", e.getMessage());
            }
        });
        floatingActionFacebookShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap image = null;
                if (ShareDialog.canShow(SharePhotoContent.class)) {
                    try {
                        URL url = new URL(imgUrl);
                        URLConnection conn =url.openConnection();
                        conn.connect();
                        InputStream in;
                        in = conn.getInputStream();
                        image = BitmapFactory.decodeStream(in);
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    SharePhoto photo = new SharePhoto.Builder()
                            .setBitmap(image)
                            .build();
                    SharePhotoContent photoContent = new SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build();
                    shareDialog.show(photoContent);
                }

            }
        });
    }

    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }
}
