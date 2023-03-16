package com.example.pacmanoblig;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.example.pacmanoblig.Main.ghostArr;

public class Dinky extends Ghost {

    public static Group dinkyView;

    public Dinky(int x, int y) {
        super(x, y, 3, 40, new Image("file:src/main/java/com/example/pacmanoblig/res/dinky.gif"), dinkyView);
        ImageView iv = new ImageView(this.getImage());
        iv.setTranslateX(x+5);
        iv.setTranslateY(y+5);
        dinkyView = new Group(iv);
        ghostArr.add(dinkyView);
    }
}
