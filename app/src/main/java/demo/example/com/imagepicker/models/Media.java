package demo.example.com.imagepicker.models;

import android.graphics.Bitmap;

/**
 * Created by siddharth on 22/7/15.
 */
public class Media {

    private String imgPath;
    private Bitmap bitmap;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String  imgPath) {
        this.imgPath = imgPath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


}
