package org.icgeb.basmati_seed_icgeb_3;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class FullScreenImageActivityAlgo2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image_algo2);


        PhotoView photoView = (PhotoView) findViewById(R.id.fullScreenImageViewAlgo2);

        Intent callingActivityIntent2 = getIntent();
        if(callingActivityIntent2 != null) {
            Uri imageUri = callingActivityIntent2.getData();
            if(imageUri != null && photoView !=null) {
                Glide.with(this)
                        .load(imageUri)
                        .into(photoView);
            }
        }




    }

}




