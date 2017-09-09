package amktest.music.gustavo.amktest.Models;

import android.graphics.drawable.Drawable;

/**
 * Created by gustavoalvarez on 08/09/17.
 */

public class Categories {

    private String name;
    private Drawable image;

    public Categories(String name, Drawable image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }


}
