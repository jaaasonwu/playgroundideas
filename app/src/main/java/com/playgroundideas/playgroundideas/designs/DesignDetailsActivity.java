package com.playgroundideas.playgroundideas.designs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.playgroundideas.playgroundideas.R;

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

        // Initialize floating action button on the UI
        Iconify.with(new MaterialModule()).with(new MaterialCommunityModule());

        floatingActionPlus.setImageDrawable(new IconDrawable(this, MaterialIcons.md_share)
                .colorRes(R.color.white).actionBarSize());
        floatingActionEmailShare.setImageDrawable(new IconDrawable(this, MaterialIcons.md_email)
                .colorRes(R.color.white).actionBarSize());
        floatingActionFacebookShare.setImageDrawable(new IconDrawable(this, MaterialCommunityIcons.mdi_facebook)
                .colorRes(R.color.white).actionBarSize());

        fabOpenShare = AnimationUtils.loadAnimation(this, R.anim.fab_open_share);
        fabRClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise);
        fabCloseShare = AnimationUtils.loadAnimation(this, R.anim.fab_close_share);
        fabRAnticlockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise);

        // Initialize animation of floating action buttons
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

        // Emai sharing
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

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // Response in the app for facebook sharing
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

        // Facebook sharing
        floatingActionFacebookShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse(imgUrl))
                            .build();
                    shareDialog.show(linkContent);
                }

            }
        });
    }

    // Facebook sharing needs it.
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
