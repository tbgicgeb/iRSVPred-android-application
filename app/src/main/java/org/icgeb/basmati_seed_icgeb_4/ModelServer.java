package org.icgeb.basmati_seed_icgeb_3;

import android.net.Uri;

public class ModelServer {

    private Uri image;
    private String title;
    private String desc;
    private int angle;

    public ModelServer(Uri image, String title, String desc, int angle) {
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int direction) {
        this.angle = angle;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}