package org.icgeb.basmati_seed_icgeb_3;

import android.graphics.Bitmap;

public class ParseItem {
    private Bitmap imgUrl;
    private String title;

    public ParseItem(){

    }

    public ParseItem( String title) {
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

}

