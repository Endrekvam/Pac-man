package com.example.pacmanoblig;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.example.pacmanoblig.Main.ghostArr;

public class Stinky extends Ghost {

    public static Group stinkyView;

    public Stinky(int x, int y) {
        super(x, y, 3, 40, new Image("file:src/main/java/com/example/pacmanoblig/res/stinky.gif"), stinkyView);
        ImageView iv = new ImageView(this.getImage());
        iv.setTranslateX(x+5);
        iv.setTranslateY(y+5);
        stinkyView = new Group(iv);
        ghostArr.add(stinkyView);
    }
}
