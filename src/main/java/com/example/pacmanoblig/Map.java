package com.example.pacmanoblig;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Map {

    // Variabler som brukes for å opprette kart, mat, vegg, spawnpunkt, spøkelser og vei
    public static ArrayList<Rectangle> veggArr = new ArrayList<>();
    public static Line veiLinjeLodd = new Line();
    public static Line veiLinjeVann = new Line();
    public static ArrayList<Line> vannArr = new ArrayList<>();
    public static ArrayList<Line> loddArr = new ArrayList<>();
    public static ArrayList<Circle> matArr = new ArrayList<>();
    public static Circle mat;
    public static Group flammeGruppe;
    public static Rectangle veggRect;
    boolean truffetVann = false;
    boolean truffetLodd = false;
    private int spawnet = 0;

    public void load(){

        // Kartet
        File map = new File("map.txt");
        int y = 0;
        int x = 0;

        // Scanner som leser kartet og oppretter dette
        try {
            Scanner scanner = new Scanner(map);
            while (scanner.hasNext()) {
                int var = scanner.nextInt();

                // Lager vegger
                if (var == 1) {
                    tegnVegg(x * Main.pixelHoyde, y * Main.pixelBredde, Main.pixelHoyde, Main.pixelBredde);
                    x++;
                    if (x > 13) {
                        y++;
                        x = 0;
                    }

                // Lager mat og veilinje, veilinja er usynlig, men kan settes til synlig i tegnVeiLinje()
                } else if (var == 0){
                    tegnMat(x * Main.pixelHoyde+25, y * Main.pixelBredde+25, 4);
                    tegnVeiLinje((x * Main.pixelHoyde), (y * Main.pixelBredde)+25, (x * Main.pixelHoyde)+25, (y * Main.pixelBredde)+25);
                    x++;

                // Lager spawnpunkt og spøkelser
                } else if (var == 2) {
                    veggRect = new Rectangle(x * Main.pixelHoyde, y * Main.pixelBredde, Main.pixelHoyde, Main.pixelBredde);
                    veggRect.setFill(Color.TRANSPARENT);
                    veggArr.add(veggRect);
                    Main.pane.getChildren().addAll(veggRect);
                    tegnSpawn(x * Main.pixelHoyde, y * Main.pixelBredde, Main.pixelHoyde, Main.pixelBredde);

                    // switch som sjekker hvor mange spøkelser som har blitt opprettet, og oppretter spøkelser basert på dette
                    switch (spawnet) {
                        case 0:
                            Rinky rinky = new Rinky(x * Main.pixelHoyde, y * Main.pixelBredde);break;
                        case 1:
                            Dinky dinky = new Dinky(x * Main.pixelHoyde, y * Main.pixelBredde);break;
                        case 2:
                            Stinky stinky = new Stinky(x * Main.pixelHoyde, y * Main.pixelBredde);break;
                        case 3:
                            Pinky pinky = new Pinky(x * Main.pixelHoyde, y * Main.pixelBredde);break;
                    }
                    spawnet++;
                    x++;
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
    public void tegnVegg(int startX, int startY, int hoyde, int bredde) throws FileNotFoundException{
        Image flamme = new Image(new FileInputStream("src/main/java/com/example/pacmanoblig/res/flamme.png"));
        ImageView flammeView = new ImageView(flamme);
        flammeView.setX(startX);
        flammeView.setY(startY);
        flammeView.setFitHeight(hoyde);
        flammeView.setFitWidth(bredde);
        flammeView.setPreserveRatio(true);

        veggRect = new Rectangle(startX, startY, hoyde, bredde);
        veggRect.setFill(Color.TRANSPARENT);

        veggArr.add(veggRect);
        Main.pane.getChildren().addAll(veggRect);

        flammeGruppe = new Group(flammeView);

        Main.pane.getChildren().addAll(flammeGruppe);

    }
    public void tegnSpawn(int startX, int startY, int hoyde, int bredde) throws FileNotFoundException{
        Image spawn = new Image(new FileInputStream("src/main/java/com/example/pacmanoblig/res/spawn.png"));
        ImageView imageView2 = new ImageView(spawn);
        imageView2.setX(startX);
        imageView2.setY(startY);
        imageView2.setFitHeight(hoyde);
        imageView2.setFitWidth(bredde);
        imageView2.setPreserveRatio(true);

        Group root2 = new Group(imageView2);
        Main.pane.getChildren().addAll(root2);
    }

    // Tegner linje som pacman kan bevege seg på
    public void tegnVeiLinje(int startX, int startY, int sluttX, int sluttY) {

        veiLinjeVann = new Line(startX-25, startY, sluttX, sluttY);
        veiLinjeVann.setStrokeWidth(3);
        veiLinjeVann.setStroke(Color.TRANSPARENT);
        veiLinjeLodd = new Line(startX+25, startY-50, sluttX, sluttY);
        veiLinjeLodd.setStrokeWidth(3);
        veiLinjeLodd.setStroke(Color.TRANSPARENT);
        truffetVann = false;
        truffetLodd = false;
        for (Rectangle r : veggArr) {
            if (veiLinjeVann.getStartX() == r.getX()+25 && veiLinjeVann.getStartY() == r.getY()+25) {
                Main.pane.getChildren().remove(veiLinjeVann);
                truffetVann = true;
            }
        }
        if (truffetVann == false) {
            vannArr.add(veiLinjeVann);
            Main.pane.getChildren().addAll(veiLinjeVann);
        }
        for (Rectangle r : veggArr) {
            if (veiLinjeLodd.getStartX() == r.getX()+25 && veiLinjeLodd.getStartY() == r.getY()+25) {
                Main.pane.getChildren().remove(veiLinjeLodd);
                truffetLodd = true;
            }
        }
        if (truffetLodd == false) {
            loddArr.add(veiLinjeLodd);
            Main.pane.getChildren().addAll(veiLinjeLodd);
        }
    }
    public void tegnMat(int startX, int startY, int radius) {
        mat = new Circle(startX, startY, radius);
        mat.setFill(Color.WHITE);
        matArr.add(mat);
        Main.pane.getChildren().addAll(mat);
    }
}




