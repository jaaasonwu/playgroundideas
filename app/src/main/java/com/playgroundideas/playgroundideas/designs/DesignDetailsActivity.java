package com.playgroundideas.playgroundideas.designs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.playgroundideas.playgroundideas.MainActivity;
import com.playgroundideas.playgroundideas.R;

public class DesignDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_details);
        TextView textView = (TextView) findViewById(R.id.imageTitle);
        textView.setText(getIntent().getExtras().getString("designName"));
        ImageView imageView = (ImageView) findViewById(R.id.imageDetails);
        imageView.setImageResource(getIntent().getExtras().getInt("designDetail"));
        Button button = (Button) findViewById(R.id.back_button_favorite_list);
        Button button1 = (Button) findViewById(R.id.back_button_browse_list);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName("com.playgroundideas.playgroundideas",
                        "com.playgroundideas.playgroundideas.MainActivity");
                intent.putExtra("tabNum", 0);
                startActivity(intent);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName("com.playgroundideas.playgroundideas",
                        "com.playgroundideas.playgroundideas.MainActivity");
                intent.putExtra("tabNum", 1);
                startActivity(intent);
            }
        });
    }
}
