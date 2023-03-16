package com.example.pacmanoblig;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import static com.example.pacmanoblig.Map.*;
import static com.example.pacmanoblig.Pinky.pinkyView;
import static com.example.pacmanoblig.Stinky.stinkyView;

public class Ghost {

    // Superklasse til alle spøkelsene
    // Variabler for oppretting av disse
    public static int x;
    public static int y;
    public static int speed;
    public static int str;
    public static Image image;
    public static Group g;
    static int start = 0;

    //For bevegelse
    static boolean loddrett = false;
    static boolean vannrett = false;
    static boolean veggKollisjon = false;
    static boolean ghostKollisjon = false;
    static int i = 0;

    //Konstruktør
    public Ghost(int x, int y, int speed, int str, Image image, Group g) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.str = str;
        this.image = image;
        this.g = g;
    }
    public void setSpeed(int speed) {

        this.speed = speed;
    }

    public static Image getImage() {
        return image;
    }
    public void setImage(Image image) {

        this.image = image;
    }

    // Metode for å få posisjonen til spøkelsene, vanlig getTranslateX og getTranslateY funker ikke
    public static Point2D getPos(Group g) {
        double ghostX = g.getBoundsInParent().getMinX();
        double ghostY = g.getBoundsInParent().getMinY();
        Point2D ghostPos = g.localToScene(ghostX, ghostY);
        return ghostPos;
    }

    public static void move(Group g) {

        // Starter med å gå rolig nedover fra spawn, de på høyresiden MÅ gå til høyre i begynnelsen, samme med de på venstre
        if (start < 400) {
            start++;
        } else if (start < 600) {
            g.setTranslateY(g.getTranslateY() + 1);
            start++;
        } else if (start < 800) {
            if (getPos(g).getX() > 330) {
                g.setTranslateX(g.getTranslateX() + speed);
            } else {
                g.setTranslateX(g.getTranslateX() - speed);
            }
            start++;

            // Resten funker dessverre ikke
        } else {
            for (Line l : loddArr) {
                if (l.getBoundsInParent().intersects(g.getBoundsInParent())) {
                    if (l.getStartX() == g.getTranslateX()+15) {
                        loddrett = true;
                        g.setTranslateX(g.getTranslateX() + speed);
                    }
                }
                //System.out.println("l.startX = " + l.getStartX() + " g.translateX = " + getPos(g).getX());
            }
            for (Line v : vannArr) {
                if (v.getBoundsInParent().intersects(g.getBoundsInParent())) {
                    if (v.getStartY() == g.getTranslateY()) {
                        vannrett = true;
                    }
                }
            }
            for (Rectangle vegg : veggArr) {
                if (vegg.getBoundsInParent().intersects(g.getBoundsInParent())) {
                    veggKollisjon = true;
                }
            }
            if (!veggKollisjon) {
                if (loddrett) {
                    g.setTranslateX(g.getTranslateX() - speed);
                }
            }
        }
    }
}