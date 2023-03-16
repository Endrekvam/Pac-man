package com.example.pacmanoblig;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;

import static com.example.pacmanoblig.Dinky.dinkyView;
import static com.example.pacmanoblig.Ghost.move;
import static com.example.pacmanoblig.Pinky.pinkyView;
import static com.example.pacmanoblig.Rinky.rinkyView;
import static com.example.pacmanoblig.Stinky.stinkyView;;

public class Main extends Application {

    private static final int bredde = 1000;
    private static final int hoyde = 700;
    public static int pixelBredde = 50;
    public static int pixelHoyde = 50;
    public static Scene scene;
    public static Pane pane = new Pane();
    Map mappet = new Map();
    public static Entity pacman;
    public static ArrayList<Group> ghostArr = new ArrayList<>(); // Array med spøkelser

    public static Timeline timeline;
    EventHandler<ActionEvent> loop = e -> update();

    @Override
    public void start(Stage stage) {

        // Timeline som kjører hele tiden og oppdaterer spillet hvert 20ms
        timeline = new Timeline(new KeyFrame(Duration.millis(20), loop));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Lager UI på høyre side av skjermen
        UI ui = new UI();
        ui.sidepanel();

        // Oppretter kart, mat, usynlige linjer spiller kan bevege seg på og spøkelsene
        mappet.load();

        stage.setTitle("Brennende Pac-man");
        stage.setResizable(false);
        pane.setStyle("-fx-background-color: black");

        // Oppretter pacman
        pacman = new Entity(330, 255, 40, 40, "file:src/main/java/com/example/pacmanoblig/res/pacmanSpawn.gif");

        // Legger til alt i panen
        pane.getChildren().addAll(rinkyView, dinkyView, stinkyView, pinkyView, pacman);

        scene = new Scene(pane, bredde, hoyde);
        stage.setScene(scene);
        stage.show();

    }
    // Oppdaterer entiteter 50 ganger i sekundet
    private void update() {
        pacman.update(pacman.getTranslateX(), pacman.getTranslateY());
        move(rinkyView);
        move(dinkyView);
        move(stinkyView);
        move(pinkyView);
    };

    public static void main(String[] args){
        launch();
    }
}