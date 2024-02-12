package org.icgeb.basmati_seed_icgeb_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_statistics);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_st);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("Statistics");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView img1 = (ImageView) findViewById(R.id.statimage);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatisticsActivity.this, FullScreenImageActivityStatistics1.class);
                startActivity(intent);
            }
        });

        TextView link1, link2, link3, link4, link5;

        link1 = findViewById(R.id.statlink1);
        link2 = findViewById(R.id.statlink2);
        link3 = findViewById(R.id.statlink3);
        link4 = findViewById(R.id.statlink4);
        link5 = findViewById(R.id.statlink5);

        link1.setMovementMethod(LinkMovementMethod.getInstance());
        link2.setMovementMethod(LinkMovementMethod.getInstance());
        link3.setMovementMethod(LinkMovementMethod.getInstance());
        link4.setMovementMethod(LinkMovementMethod.getInstance());
        link5.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
