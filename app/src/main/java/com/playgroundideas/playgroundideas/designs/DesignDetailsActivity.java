package com.playgroundideas.playgroundideas.designs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.playgroundideas.playgroundideas.BuildConfig;
import com.playgroundideas.playgroundideas.R;
import com.playgroundideas.playgroundideas.datasource.local.FileStorage;
import com.playgroundideas.playgroundideas.model.Design;
import com.playgroundideas.playgroundideas.viewmodel.DesignViewModel;

import java.io.File;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class DesignDetailsActivity extends DaggerAppCompatActivity {

    private DesignViewModel viewModel;
    private Design designTemp;
    private Button guideButton;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_details);

        // this integrates the design view model
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DesignViewModel.class);
        viewModel.init(getIntent().getExtras().getLong("designId"));
        viewModel.getDesign().observe(this, new Observer<Design>() {
            @Override
            public void onChanged(@Nullable Design design) {
                designTemp = design;
                TextView textView = findViewById(R.id.imageTitle);
                textView.setText(design.getName());
                ImageView imageView = findViewById(R.id.imageDetails);
                imageView.setImageURI(Uri.fromFile(FileStorage.readFile(design.getImageInfo())));
            }
        });

        guideButton = findViewById(R.id.viewDesignGuideButton);
        guideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open the default pdf reader to show the design guide
                Intent intent = new Intent(Intent.ACTION_VIEW);
                File file = FileStorage.readFile(designTemp.getGuideInfo());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
                    intent.setDataAndType(uri, "application/pdf");
                } else {
                    intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                }
                context.startActivity(intent);
            }
        });
    }
}
