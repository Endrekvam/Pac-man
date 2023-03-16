package com.example.pacmanoblig;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import static com.example.pacmanoblig.Main.ghostArr;
import static com.example.pacmanoblig.Main.pacman;
import static com.example.pacmanoblig.Map.*;
import static com.example.pacmanoblig.UI.poeng;
import static javafx.scene.input.KeyCode.*;

public class Entity extends ImageView {

    // Variabler for bevegelse av spiller, fart og telling av score
    private boolean treffVegg = false;
    private boolean pacmanDie;
    private boolean linjetreff;
    public static int score = 0;
    private int margin = 15;
    private int ventetid;
    private int speed = 3;

    // Variabler for å holde styr på hvilke knapper som er trykket
    public static KeyCode pressed;
    public static KeyCode lastPressed;
    public static KeyCode vildra;

    public Entity(int x, int y, int hoyde, int bredde, String imagePath) {

        setImage(new Image(imagePath));
        setTranslateX(x);
        setTranslateY(y);
        setFitWidth(bredde);
        setFitHeight(hoyde);
    }

    public void update(double x, double y) {

        // Sjekker først om pacman er død, spillet kjører som vanlig hvis ikke
        // Sjekker hvilken knapp som er trykket, om pacman treffer en vegg, og at han er på enten vannrett eller loddrett linje.
        // Siste forløkka i hver knapp "snapper" pacman til linjekoordinatene.

        if (pacmanDie) {
            if (ventetid < 100) {
                ventetid++;
            } else {
                pacman.setImage(new Image("file:src/main/java/com/example/pacmanoblig/res/pacmanSpawn.gif"));
                pacmanDie = false;
            }
        }


        // sjekker knapper og beveger pacman, bytter bilde ut ifra hvilken knapp som er trykket
        Main.scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W:
                    if (!pacmanDie) {
                        ventetid = 0;
                        if (lastPressed == A) {
                            pacman.setImage(new Image("file:src/main/java/com/example/pacmanoblig/res/pacmanUpFromLeft.gif"));
                        } else {
                            pacman.setImage(new Image("file:src/main/java/com/example/pacmanoblig/res/pacmanUpFromRight.gif"));
                        }
                        pressed = W;
                    }
                    break;
                case A:
                    if (!pacmanDie) {
                        ventetid = 0;
                        pressed = A;
                        pacman.setImage(new Image("file:src/main/java/com/example/pacmanoblig/res/pacmanLeft.gif"));
                    }
                    break;
                case S:
                    if (!pacmanDie) {
                        ventetid = 0;
                        if (lastPressed == A) {
                            pacman.setImage(new Image("file:src/main/java/com/example/pacmanoblig/res/pacmanDownFromLeft.gif"));
                        } else {
                            pacman.setImage(new Image("file:src/main/java/com/example/pacmanoblig/res/pacmanDownFromRight.gif"));
                        }
                        pressed = S;
                    }
                    break;
                case D:
                    if (!pacmanDie) {
                        ventetid = 0;
                        pressed = D;
                        pacman.setImage(new Image("file:src/main/java/com/example/pacmanoblig/res/pacmanRight.gif"));
                    }
                    break;
            }
        });

        // HÅNDTERER PACMANS BEVEGELSE
        treffVegg = false;
        linjetreff = false;

        // HÅNDTERING AV W-KNAPP
        if (pressed == W) {
            for (Rectangle r : veggArr) {
                if (getBoundsInParent().intersects(r.getBoundsInParent())) {
                    treffVegg = true;
                }
            }
            // Går gjennom liste med linjer for å holde styr på posisjon
            for (Line l : loddArr) {
                if (getBoundsInParent().intersects(l.getBoundsInParent())) {
                    linjetreff = true;
                    // Denne ifen gjør at pacman "snapper" på en ny linje dersom han er nærme nok
                    if (((x + 20) >= (l.getStartX() - margin) && (x + 20) <= (l.getStartX() + margin))) {
                        if (treffVegg) {
                            setTranslateY(l.getStartY() +20 );
                            setTranslateY(l.getStartY() - 20);
                            pressed = null;
                            break;
                        } else {
                            setTranslateX(l.getStartX() - 20);
                            setTranslateY(y - speed);
                            lastPressed = W;
                        }
                    }
                }
            }
            if (!linjetreff) {
                pressed = lastPressed;
                vildra = W;
            }
            // forsøk på mer smidig bevegelse
               /* if (vildra == A || vildra == D) {
                    for (Line l : vannArr) {
                        if (getBoundsInParent().intersects(l.getBoundsInParent())) {
                            pressed = vildra;
                            /*if (((y + 20) >= (l.getStartY() - margin) && (y + 20) <= (l.getStartY() + margin))) {

                            }
                        }
                    }
                }*/

            // HÅNDTERING AV A-KNAPPEN

        } else if (pressed == A) {
            for (Rectangle r : veggArr) {
                if (getBoundsInParent().intersects(r.getBoundsInParent())) {
                    treffVegg = true;
                }
            }
            for (Line l : vannArr) {
                if (getBoundsInParent().intersects(l.getBoundsInParent())) {
                    linjetreff = true;
                    if (((y + 20) >= (l.getStartY() - margin) && (y + 20) <= (l.getStartY() + margin))) {
                        if (treffVegg) {
                            setTranslateX(l.getStartX() - 20);
                            setTranslateY(l.getStartY() - 20);
                            pressed = null;
                            break;
                        } else {
                            setTranslateY(l.getStartY() - 20);
                            setTranslateX(x - speed);
                            lastPressed = A;
                        }
                    }
                }
            }
            if (!linjetreff) {
                pressed = lastPressed;
                vildra = A;
            }
               /* if (vildra == W || vildra == S) {
                    for (Line l : loddArr) {
                        if (getBoundsInParent().intersects(l.getBoundsInParent())) {
                            //linjetreff = true;
                            if (((x + 20) >= (l.getStartX() - margin) && (x + 20) <= (l.getStartX() + margin))) {
                                pressed = vildra;
                            }
                        }
                    }
                }*/

            // HÅNDTERING AV S-KNAPPEN

        } else if (pressed == S) {
            for (Rectangle r : veggArr) {
                if (getBoundsInParent().intersects(r.getBoundsInParent())) {
                    treffVegg = true;
                }
            }
            for (Line l : loddArr) {
                if (getBoundsInParent().intersects(l.getBoundsInParent())) {
                    linjetreff = true;
                    if (((x + 20) >= (l.getStartX() - margin) && (x + 20) <= (l.getStartX() + margin))) {
                        if (treffVegg) {
                            setTranslateY(l.getEndY() - 20);
                            setTranslateX(l.getStartX() - 20);
                            pressed = null;
                            break;
                        } else {
                            setTranslateX(l.getStartX() - 20);
                            setTranslateY(y + speed);
                            lastPressed = S;
                        }
                    }
                }
            }
            if (!linjetreff) {
                pressed = lastPressed;
                vildra = S;
            }
               /* if (vildra == A || vildra == D) {
                    for (Line l : vannArr) {
                        if (getBoundsInParent().intersects(l.getBoundsInParent())) {
                            pressed = vildra;
                            /*if (((y + 20) >= (l.getStartY() - margin) && (y + 20) <= (l.getStartY() + margin))) {
                                pressed = vildra;
                            }
                        }
                    }
                }*/

            // HÅNDTERING AV D-KNAPPEN

        } else if (pressed == D) {
            for (Rectangle r : veggArr) {
                if (getBoundsInParent().intersects(r.getBoundsInParent())) {
                    treffVegg = true;
                }
            }
            for (Line l : vannArr) {
                if (getBoundsInParent().intersects(l.getBoundsInParent())) {
                    linjetreff = true;
                    if (((y + 20) >= (l.getStartY() - margin) && (y + 20) <= (l.getStartY() + margin))) {
                        if (treffVegg) {
                            setTranslateX(l.getEndX() - 20);
                            pressed = null;
                            break;
                        } else {
                            setTranslateY(l.getStartY() - 20);
                            setTranslateX(x + speed);
                            lastPressed = D;
                        }
                    }
                }
            }
            if (!linjetreff) {
                pressed = lastPressed;
                vildra = D;
            }
                /*if (vildra == W || vildra == S) {
                    for (Line l : loddArr) {
                        if (getBoundsInParent().intersects(l.getBoundsInParent())) {
                            pressed = vildra;
                           /* if (((x + 20) >= (l.getStartX() - margin) && (x + 20) <= (l.getStartX() + margin))) {

                            }
                        }
                    }
                }*/
        }

        // Kollisjon med mat og oppdatering av score, og fjerning av mat
        try {
            for (Circle mat : matArr) {
                if (getBoundsInParent().intersects(mat.getBoundsInParent())) {
                    score++;
                    Main.pane.getChildren().remove(mat);
                    matArr.remove(mat);
                    poeng.setText("score:   " + Entity.score);
                }
            }
        } catch (Exception e) {}

        // Kollisjon med spøkelse, setter pacman tilbake til startpunkt og setter pacman til død
        // Måtte bruke try catch fordi den kastet en ConcurrentModificationException kontinuerlig
        for (Group g : ghostArr) {
            if (getBoundsInParent().intersects(g.getBoundsInParent())) {
                pressed = null;
                pacmanDie = true;
                pacman.setImage(new Image("file:src/main/java/com/example/pacmanoblig/res/pacmanDie.gif"));
                setTranslateX(330);
                setTranslateY(255);
            }
        }
    }
}


