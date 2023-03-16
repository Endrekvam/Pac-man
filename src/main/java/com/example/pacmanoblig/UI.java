package com.example.pacmanoblig;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UI {

    public static Label poeng;
    public void sidepanel() {

        VBox vbox = new VBox();
        vbox.setTranslateX(710);
        vbox.setTranslateY(10);

        Image title = new Image("file:src/main/java/com/example/pacmanoblig/res/title.png");
        ImageView iv = new ImageView(title);
        iv.setFitWidth(310);
        iv.setY(20);
        iv.setPreserveRatio(true);
        Group root = new Group(iv);

        // Label for poengteller
        poeng = new Label();
        poeng.setFont(Font.font ("ROBOTO", FontWeight.BOLD, 40));
        poeng.setTextFill(Color.WHITE);;
        poeng.setText("score:   " + Entity.score);
        vbox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(root, poeng);
        Main.pane.getChildren().addAll(vbox);
    }
}
