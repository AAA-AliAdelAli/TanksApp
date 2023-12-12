package Tanks;

import Texture.TextureReader;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.GLUT;

import components.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.media.opengl.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import java.io.FileInputStream;
import javax.media.opengl.*;
import java.io.File;

public class TanksGLEventListener extends TanksListener {

    int animationIndex = 0;
    int EnemyX = 20;
    int EnemyX2 = 1;
    int EnemyY = 40;
    int EnemyY2 = 1;
    double EnemyYHard = 0;
    double EnemyYHard2 = 1;
    double EnemyYHardII = 0;
    double EnemyYHardII2 = 1;
    double EnemyYHardIII = 54;
    boolean turned = false;
    double EnemyYHardIII2 = 0;
    int score;
    int score2;
    int counter;
    GLUT g = new GLUT();

    double EnemyXMid = 18;
    double EnemyXMid2 = 0.6;
    double EnemyXHard = 20;
    double EnemyXHard2 = 1;
    double EnemyYHardIIII = 19;
    Directions EnemyDirHardIIII = Directions.up;
    double EnemyYHardIIII2 = 1;
    FileInputStream music, music1;
    AudioStream audios, audios1;
    boolean sound = false, sound1 = false;
    boolean onOff = false;


    boolean check_player2 = false;
    private Thread audioThread;
    String namePlayer1="player1";
    String namePlayer2="player2";

    private void drawBrick(GL gl, Brick b) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[b.index]);

        gl.glPushMatrix();
        gl.glTranslated(b.position.x / (maxWidth / 2.0) - 0.9, b.position.y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(.1, .1, 1);

        frontFace(gl);
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    private void checkBullet(Bullet bullet) {
        for (int i = 0; i < currentMap.bricks.size(); i++) {
            if (bulletX >= currentMap.bricks.get(i).position.x - 4
                    && bulletX <= currentMap.bricks.get(i).position.x + 4
                    && bulletY >= currentMap.bricks.get(i).position.y - 5
                    && bulletY <= currentMap.bricks.get(i).position.y + 5) {

                bullet.x = 100;
                bullet.y = 100;

                if (currentMap.bricks.get(i).canBeBroken) {
                    currentMap.bricks.remove(i);
                    score += 10;
                    if (isWinner()) {
                        winner = true;
                    }
                }
            }
        }
    }

    private void checkBullet2(Bullet bullet) {
        for (int i = 0; i < currentMap.bricks.size(); i++) {

            Point2D brickPos = getPosition(currentMap.bricks.get(i).position, false);
            Point2D bulletPos = getPosition(new Point2D(bullet.x, bullet.y), true);

//            System.out.println("brick " + brickPos.x + ", " + brickPos.y);
//            System.out.println("bullet " + bulletPos.x + ", " + bulletPos.y);
            if (bulletPos.x >= brickPos.x - .15
                    && bulletPos.x <= brickPos.x + .15
                    && bulletPos.y >= brickPos.y - .15
                    && bulletPos.y <= brickPos.y + .15) {

                bullet.x = -100;
                bullet.y = -100;

                if (currentMap.bricks.get(i).canBeBroken) {
                    currentMap.bricks.remove(i);
                    score2 += 10;
                    if (isWinner()) {
                        winner = true;
                    }
                }
            }
        }
    }

    private boolean isWinner() {
        return currentMap.getWhiteBricksPositions().size() == 0;
    }

    public enum Directions {
        up, down, left, right, down_right, up_left, up_right, down_left, W, A, S, D, Q, W_A, W_D, S_A, S_D
    }

    Directions direction2 = Directions.S;
    Directions direction = Directions.up;
    Directions EnemyDir = Directions.right;
    Directions EnemyDirMed = Directions.up;
    Directions EnemyDirMed2 = Directions.right;
    Directions EnemyDirHard = Directions.up;
    Directions EnemyDirHardII = Directions.up;
    Directions EnemyDirHardIII = Directions.right;

    ArrayList<Bullet> bullets;
    ArrayList<Bullet> bullets2;
    Map map1 = new Map();
    Map map2 = new Map();
    Map map3 = new Map();

    Map currentMap = new Map();

    int maxWidth = 60, maxHeight = 60;
    int xPosition, yPosition;

    int x, y, x2, y2;
    //    int x3 =0 , y3=0;
    int bulletX, bulletY, bulletX2, bulletY2;

    int lives, lives2;

    public boolean home, winner, gameOver, onePlayer, twoPlayer, easy, medium, hard ,how;
    //    ArrayList<Point2D> bricksPositions = new ArrayList<>();
    ArrayList<Point2D> whiteBricksPositions = new ArrayList<>();

    // bull index 4
    String textureNames[] = {"tankUp.png", "tankUp2.png", "tankLeft.png", "tankDown.png",
            "bull.png", "bricks.png", "white_bricks.png",
            "home.jpg", "level.jpg", "win.png", "gameover.png","how.png" ,"Back.jpg"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    /*
     5 means gun in array pos
     x and y coordinate for gun
     */
    private void homePage() {
        home = true;
        onePlayer = false;
        twoPlayer = false;
        easy = false;
        medium = false;
        hard = false;
        winner = false;
        gameOver = false;
        how=false;
    }

    private void initGame() {

        x = y = 0;
        x2 = y2 = 0;
        xPosition = yPosition = 0;
        bulletX = bulletY = 0;
        bulletX2 = bulletY2 = 0;
        bullets = new ArrayList<>();
        bullets2 = new ArrayList<>();
        lives = 3;
        lives2 = 3;
        score = score2 = 0;
        counter = 0;
//        newGame();

        map1.bricks.clear();
        map2.bricks.clear();
        map3.bricks.clear();

        // Level 1
        map1.bricks.addAll(Arrays.asList(
                new Brick(new Point2D(48, 30), false),
                new Brick(new Point2D(36, 30), false),
                new Brick(new Point2D(42, 30), false),
                new Brick(new Point2D(30, 30), false),
                new Brick(new Point2D(24, 24), false),
                new Brick(new Point2D(24, 18), false),
                new Brick(new Point2D(12, 30), false),
                new Brick(new Point2D(6, 30), false),
                new Brick(new Point2D(54, 30), true),
                new Brick(new Point2D(18, 30), true),
                new Brick(new Point2D(24, 12), true),
                new Brick(new Point2D(24, 30), true)
        ));
        // Level 2
        map2.bricks.addAll(Arrays.asList(
                new Brick(new Point2D(30, 40), true),
                new Brick(new Point2D(36, 40), false),
                new Brick(new Point2D(24, 40), false),
                new Brick(new Point2D(18, 40), true),
                new Brick(new Point2D(30, 20), true),
                new Brick(new Point2D(36, 20), false),
                new Brick(new Point2D(24, 20), false),
                new Brick(new Point2D(18, 20), true),
                new Brick(new Point2D(48, 36), false),
                new Brick(new Point2D(48, 30), true),
                new Brick(new Point2D(48, 24), false),
                new Brick(new Point2D(48, 18), true),
                new Brick(new Point2D(48, 12), false),
                new Brick(new Point2D(6, 36), false),
                new Brick(new Point2D(6, 30), true),
                new Brick(new Point2D(6, 24), false),
                new Brick(new Point2D(6, 18), true),
                new Brick(new Point2D(6, 12), false)
        ));
        // Level 3
        map3.bricks.addAll(Arrays.asList(
                new Brick(new Point2D(0, 12), false),
                new Brick(new Point2D(6, 12), false),
                new Brick(new Point2D(12, 12), false),
                new Brick(new Point2D(12, 6), true),
                new Brick(new Point2D(12, 0), false),
                new Brick(new Point2D(12, 24), false),
                new Brick(new Point2D(12, 30), true),
                new Brick(new Point2D(12, 36), false),
                new Brick(new Point2D(12, 42), false),
                new Brick(new Point2D(12, 48), false),
                new Brick(new Point2D(12, 54), true),
                new Brick(new Point2D(18, 42), false),
                new Brick(new Point2D(24, 42), true),
                new Brick(new Point2D(30, 42), false),
                new Brick(new Point2D(36, 42), true),
                new Brick(new Point2D(42, 42), false),
                new Brick(new Point2D(18, 24), false),
                new Brick(new Point2D(24, 24), false),
                new Brick(new Point2D(30, 24), false),
                new Brick(new Point2D(36, 24), false),
                new Brick(new Point2D(42, 24), false),
                new Brick(new Point2D(42, 42), false),
                new Brick(new Point2D(42, 36), true),
                new Brick(new Point2D(42, 30), false),
                new Brick(new Point2D(42, 24), false),
                new Brick(new Point2D(42, 18), true),
                new Brick(new Point2D(42, 12), false),
                new Brick(new Point2D(42, 6), false),
                new Brick(new Point2D(42, 0), false)
        ));

    }

    public void init(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        homePage();
        initGame();
        //sound
        try {
            Thread.sleep(5000);
            music = new FileInputStream(new File("sound.wav"));
            audios = new AudioStream(music);

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        AudioPlayer.player.start(audios);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }

    }

    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        GLUT g = new GLUT();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();
        try {
            music1 = new FileInputStream(new File("bsound.wav"));
            audios1 = new AudioStream(music1);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        handleKeyPress();
        if (onOff) {
            bullSound();
            onOff = false;

        }
        if (home) {
            DrawBackground(gl, 8);
        }if (how){
            DrawBackground(gl, 12);
        }
        else if (onePlayer) {
            DrawBackground(gl, 9);

        } else if (twoPlayer) {
            DrawBackground(gl, 9);
            check_player2 = true;
        } else if (easy || medium || hard) {
            how =false;

            int level = 13;
            DrawBackground(gl, level);
            if (easy) {

                System.out.println("x2: " + x2);
                currentMap = map1;
                DrawTank(gl, EnemyX, EnemyY, 1, 1, EnemyDir);
                EnemyX += EnemyX2;
                if (EnemyX > 54) {
                    EnemyDir = Directions.left;
                    EnemyX2 = -1;
                }
                if (EnemyX < 0) {
                    EnemyDir = Directions.right;
                    EnemyX2 = 1;
                }
                if (getEnemyDistance() < 6) {
                    x = 0;
                    y = 0;
                    if (--lives == 0) {
                        gameOver = true;
                    }
                }
                if (Math.sqrt(((x2 + 54) - 42) * ((x2 + 54) - 42) + ((y2 + 54) - EnemyY) * ((y2 + 54) - EnemyY)) < 6) {
                    x2 = 0;
                    y2 = 0;
                    if (--lives2 == 0) {
                        gameOver = true;
                    }
                }
//                displayVar(g, gld);

            } else if (medium) {
                currentMap = map2;
                System.out.println("x:"+x);
                DrawTank(gl, 42, EnemyY, 1, 1, EnemyDirMed);
                DrawTank(gl, EnemyXMid, 30, 1, 1, EnemyDirMed2);
                EnemyXMid += EnemyXMid2;
                EnemyY += EnemyY2;
                if (EnemyXMid > 41) {
                    EnemyDirMed2 = Directions.left;
                    EnemyXMid2 = -0.6;
                }
                if (EnemyXMid < 12) {
                    EnemyDirMed2 = Directions.right;
                    EnemyXMid2 = 0.6;
                }
                if (EnemyY > 54) {
                    EnemyDirMed = Directions.down;
                    EnemyY2 = -1;
                }
                if (EnemyY < 0) {
                    EnemyDirMed = Directions.up;
                    EnemyY2 = 1;
                }
                if (Math.sqrt((x - EnemyXMid) * (x - EnemyXMid) + (y - 30) * (y - 30)) < 6) {
                    x = 0;
                    y = 0;
                    if (--lives == 0) {
                        gameOver = true;
                    }

                }
                //col
                if (Math.sqrt((x - 42) * (x - 42) + (y - EnemyY) * (y - EnemyY)) < 6) {
                    x = 0;
                    y = 0;
                    if (--lives == 0) {
                        gameOver = true;
                    }
                }

                if (Math.sqrt(((x2 + 54) - EnemyXMid) * ((x2 + 54) - EnemyXMid) + ((y2 + 54) - 30) * ((y2 + 54) - 30)) < 6) {
                    x2 = 0;
                    y2 = 0;
                    if (--lives2 == 0) {
                        gameOver = true;
                    }

                }
                //col
                if (Math.sqrt(((x2 + 54) - 42) * ((x2 + 54) - 42) + ((y2 + 54) - EnemyY) * ((y2 + 54) - EnemyY)) < 6) {
                    x2 = 0;
                    y2 = 0;
                    if (--lives2 == 0) {
                        gameOver = true;
                    }
                }
            } else if (hard) {

                currentMap = map3;
                DrawTank(gl, 18, EnemyYHard, 1, 1, EnemyDirHard);
                DrawTank(gl, 48, EnemyYHardII, 1, 1, EnemyDirHardII);
                DrawTank(gl, EnemyXHard, EnemyYHardIII, 1, 1, EnemyDirHardIII);
                DrawTank(gl, 0, EnemyYHardIIII, 1, 1, EnemyDirHardIIII);
                EnemyYHardIIII += EnemyYHardIIII2;
                if (EnemyYHardIIII > 53) {
                    EnemyDirHardIIII = Directions.down;
                    EnemyYHardIIII2 = -1;
                }
                if (EnemyYHardIIII < 19) {
                    EnemyDirHardIIII = Directions.up;
                    EnemyYHardIIII2 = 1;
                }
                if (Math.sqrt((x - 0) * (x - 0) + (y - EnemyYHardIIII) * (y - EnemyYHardIIII)) < 6) {
                    x = 0;
                    y = 0;
                    if (--lives == 0) {
                        gameOver = true;
                    }
                }

                if (Math.sqrt(((x2 + 54) - 0) * ((x2 + 54) - 0) + ((y2 + 54) - EnemyYHardIIII) * ((y2 + 54) - EnemyYHardIIII)) < 6) {
                    x2 = 0;
                    y2 = 0;
                    if (--lives2 == 0) {
                        gameOver = true;
                    }
                }

                EnemyXHard += EnemyXHard2;
                EnemyYHardIII += EnemyYHardIII2;

                EnemyYHard += EnemyYHard2;
                EnemyYHardII += EnemyYHardII2;

                if (EnemyXHard > 42) {
                    EnemyDirHardIII = Directions.down;
                    EnemyXHard2 = 0;
                    EnemyYHardIII2 = -1;
                    turned = true;
                }
                if (EnemyYHardIII < 49) {
                    EnemyDirHardIII = Directions.left;
                    EnemyYHardIII2 = 0;
                    EnemyXHard2 = -1;
                }

                if (EnemyXHard < 19) {
                    EnemyDirHardIII = Directions.up;
                    EnemyYHardIII2 = 1;
                    EnemyXHard2 = 0;
                }
                if (EnemyYHardIII > 53 && EnemyXHard < 19) {
                    EnemyDirHardIII = Directions.right;
                    EnemyYHardIII2 = 0;
                    EnemyXHard2 = 1;
//                    turned=false;
                }
                if (Math.sqrt((x - EnemyXHard) * (x - EnemyXHard) + (y - EnemyYHardIII) * (y - EnemyYHardIII)) < 6) {
                    x = 0;
                    y = 0;
                    if (--lives == 0) {
                        gameOver = true;
                    }
                }
                if (Math.sqrt(((x2 + 54) - EnemyXHard) * ((x2 + 54) - EnemyXHard) + ((y2 + 54) - EnemyYHardIII) * ((y2 + 54) - EnemyYHardIII)) < 6) {
                    x2 = 0;
                    y2 = 0;
                    if (--lives2 == 0) {
                        gameOver = true;
                    }
                }

                if (EnemyYHardII > 54) {
                    EnemyDirHardII = Directions.down;
                    EnemyYHardII2 = -1;
                }
                if (EnemyYHardII < 0) {
                    EnemyDirHardII = Directions.up;
                    EnemyYHardII2 = 1;
                }

                if (EnemyYHard > 18) {
                    EnemyDirHard = Directions.down;
                    EnemyYHard2 = -1;
                }
                if (EnemyYHard < 0) {
                    EnemyDirHard = Directions.up;
                    EnemyYHard2 = 1;
                }
                if (Math.sqrt((x - 18) * (x - 18) + (y - EnemyYHard) * (y - EnemyYHard)) < 6) {
                    x = 0;
                    y = 0;
                    if (--lives == 0) {
                        gameOver = true;
                    }
                }
                if (Math.sqrt(((x2 + 54) - 18) * ((x2 + 54) - 18) + ((y + 54) - EnemyYHard) * ((y2 + 54) - EnemyYHard)) < 6) {
                    x2 = 0;
                    y2 = 0;
                    if (--lives2 == 0) {
                        gameOver = true;
                    }
                }
                if (Math.sqrt((x - 48) * (x - 48) + (y - EnemyYHardII) * (y - EnemyYHardII)) < 6) {
                    x = 0;
                    y = 0;
                    if (--lives == 0) {
                        gameOver = true;
                    }
                }
                if (Math.sqrt(((x2 + 54) - 48) * ((x2 + 54) - 48) + ((y2 + 54) - EnemyYHardII) * ((y2 + 54) - EnemyYHardII)) < 6) {
                    x2 = 0;
                    y2 = 0;
                    if (--lives2 == 0) {
                        gameOver = true;
                    }
                }

//                displayVar(g, gld);
            }

            animationIndex = animationIndex % 4;
            DrawTank(gl, x, y, animationIndex, 1, direction);
            if (check_player2) {
                DrawTank2(gl, x2, y2, animationIndex, 1, direction2);

            }
            for (Brick b : currentMap.bricks) {
                drawBrick(gl, b);
            }
//bullet
            for (Bullet bullet : bullets) {
                if (bullet.fired) {
//                System.out.println(bullet.fired);
                    switch (bullet.directions) {
                        case up:
                            bullet.y++;
                            break;
                        case up_right:
                            bullet.x++;
                            bullet.y++;
                            break;
                        case right:
                            bullet.x += 2;
                            break;
                        case down_right:
                            bullet.x++;
                            bullet.y--;
                            break;
                        case down:
                            bullet.y--;
                            break;
                        case down_left:
                            bullet.x--;
                            bullet.y--;
                            break;
                        case left:
                            bullet.x -= 2;
                            break;
                        case up_left:
                            bullet.x--;
                            bullet.y++;
                            break;
                    }

                    DrawTank(gl, bullet.x, bullet.y + 1, textures[3], 0.3f, bullet.directions);

                    bulletX = bullet.x;
                    bulletY = bullet.y;
                    boolean isPointInList = false;

                    checkBullet(bullet);

                }
            }
            //make shot if two player only
            if (check_player2) {
                for (Bullet bullet : bullets2) {
                    if (bullet.fired) {
//                System.out.println(bullet.fired);
                        switch (bullet.directions) {
                            case W:
                                bullet.y++;
                                break;
                            case W_D:
                                bullet.x++;
                                bullet.y++;
                                break;
                            case D:
                                bullet.x++;
                                break;
                            case S_D:
                                bullet.x++;
                                bullet.y--;
                                break;
                            case S:
                                bullet.y--;
                                break;
                            case S_A:
                                bullet.x--;
                                bullet.y--;
                                break;
                            case A:
                                bullet.x--;
                                break;
                            case W_A:
                                bullet.x--;
                                bullet.y++;
                                break;
                        }

                        DrawTank2(gl, bullet.x, bullet.y + 1, textures[3], 0.3f, bullet.directions);
                        bulletX2 = bullet.x;
                        bulletY2 = bullet.y;
                        checkBullet2(bullet);

                    }
                }
            }
        }

        if (winner) {

            DrawBackground(gl, 10);


        }

        if (gameOver) {
            DrawBackground(gl, 11);

        }

        displayVar(g, gld);

    }

    private void displayVar(GLUT g, GLAutoDrawable gld) {


        if (easy || medium ||hard) {
            GL gl2 = gld.getGL();
            gl2.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

            gl2.glRasterPos2f(-.92f, .9f);
            g.glutBitmapString(5, namePlayer1 + " Score ");
            g.glutBitmapString(5, Integer.toString(score));

            gl2.glRasterPos2f(-.92f, .74f);
            g.glutBitmapString(5, namePlayer1 + " Lives");
            g.glutBitmapString(5, Integer.toString(lives));

            gl2.glRasterPos2f(-.92f, .60f);
            g.glutBitmapString(5, " Timer ");
            g.glutBitmapString(5, Long.toString(counter));

            if (check_player2) {
                gl2.glRasterPos2f(-.92f, .82f);
                g.glutBitmapString(5, namePlayer2 + " Score ");
                g.glutBitmapString(5, Integer.toString(score2));

                gl2.glRasterPos2f(-.92f, .66f);
                g.glutBitmapString(5, namePlayer2 + " Lives ");
                g.glutBitmapString(5, Integer.toString(lives2));
            }


            gl2.glEnd();
        }
    }

    public void newGame() {

        javax.swing.Timer timer = new Timer(900, e -> {
            //start counter in text field
            counter++;


        });
        timer.start();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void DrawTank(GL gl, double x, double y, int index, float scale, Directions dir) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);    // Turn Blending On
        int angle = 0; //gl.glRotated(angle,0,0,1); for rotated the to correct dir
        if (dir == Directions.up) {
            angle = 0;
        } else if (dir == Directions.down) {
            angle = 180;
        } else if (dir == Directions.left) {
            angle = 90;

        } else if (dir == Directions.right) {
            angle = -90;
        } else if (dir == Directions.down_right) {
            angle = -135;
        } else if (dir == Directions.down_left) {
            angle = 135;
        } else if (dir == Directions.up_left) {
            angle = 45;
        } else if (dir == Directions.up_right) {
            angle = -45;
        }

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
        gl.glRotated(angle, 0, 0, 1);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawTank2(GL gl, int x, int y, int index, float scale, Directions dir) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);    // Turn Blending On
        int angle = 0; //gl.glRotated(angle,0,0,1); for rotated the to correct dir
        if (dir == Directions.W) {
            angle = 0;
        } else if (dir == Directions.S) {
            angle = 180;
        } else if (dir == Directions.A) {
            angle = 90;
        } else if (dir == Directions.D) {
            angle = -90;
        } else if (dir == Directions.S_D) {
            angle = -135;
        } else if (dir == Directions.S_A) {
            angle = 135;
        } else if (dir == Directions.W_A) {
            angle = 45;
        } else if (dir == Directions.W_D) {
            angle = -45;
        }

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) + 0.9, y / (maxHeight / 2.0) + 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
        gl.glRotated(angle, 0, 0, 1);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }


    public void DrawBackground(GL gl, int index) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, index);    // Turn Blending On

        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void drawBrick(GL gl, Point2D p, boolean canBeCollapsed) {
        int index = canBeCollapsed ? 6 : 5;

        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);

        gl.glPushMatrix();
        gl.glTranslated(p.x / (maxWidth / 2.0) - 0.9, p.y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(.1, .1, 1);

        frontFace(gl);
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    private void frontFace(GL gl) {
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
    }

    public double getDistance() {
        return Math.sqrt((x - (x2 + 54)) * (x - (x2 + 54)) + (y - (y2 + 54)) * (y - (y2 + 54)));
    }

    public double getEnemyDistance() {
        return Math.sqrt((x - EnemyX) * (x - EnemyX) + (y - EnemyY) * (y - EnemyY));
    }

    private Point2D getPosition(Point2D p, boolean fromUp) {
        if (fromUp) {
            return new Point2D(p.x / (maxWidth / 2.0) + 0.9, p.y / (maxHeight / 2.0) + 0.9);
        }

        return new Point2D(p.x / (maxWidth / 2.0) - 0.9, p.y / (maxHeight / 2.0) - 0.9);
    }

    private boolean isBricksForTank1() {
        Point2D tank1Position = getPosition(new Point2D(x, y), false),
                bricksPosition = new Point2D();

        for (Brick b : currentMap.bricks) {
            bricksPosition = getPosition(b.position, false);
            double distance = tank1Position.getDistanceFrom(bricksPosition);

            if (distance < .195) {
                return true;
            }
        }

        return false;
    }

    private boolean isBricksForTank2() {
        Point2D tank2Position = getPosition(new Point2D(x2, y2), true),
                bricksPosition = new Point2D();

        for (Brick b : currentMap.bricks) {
            bricksPosition = getPosition(b.position, false);
            double distance = tank2Position.getDistanceFrom(bricksPosition);

            if (distance < .195) {
                return true;
            }
        }

        return false;
    }

    public void handleKeyPress() {
        if (home == false && onePlayer == false) {
            if (isKeyPressed(KeyEvent.VK_SPACE)) {
                onOff = true;
                bullSound();
                //fire
                System.out.println("here22");
                if (bulletX == x && bulletY == y) {
                    bullets.add(new Bullet(direction, x, y));
                } else if ((Math.abs(bulletX - x) >= 10) || (Math.abs(bulletY - y) >= 5)) {
                    bullets.add(new Bullet(direction, x, y));

                }
            } else if (isKeyPressed(KeyEvent.VK_LEFT) && isKeyPressed(KeyEvent.VK_DOWN)) {
                if (x > 0 && y > 0) {
                    x--;
                    y--;
                    if (getDistance() < 6 || isBricksForTank1()) {
                        x++;
                        y++;
                    }
                }
                direction = Directions.down_left;
            } else if (isKeyPressed(KeyEvent.VK_RIGHT) && isKeyPressed(KeyEvent.VK_DOWN)) {
                // Handle down_right
                if (x < maxWidth - 6 && y > 0) {
                    x++;
                    y--;
                    if (getDistance() < 6 || isBricksForTank1()) {
                        x--;
                        y++;
                    }
                }
                direction = Directions.down_right;
            } else if (isKeyPressed(KeyEvent.VK_UP) && isKeyPressed(KeyEvent.VK_LEFT)) {
                // Handle up_left
                if (y < maxHeight - 6 && x > 0) {
                    y++;
                    x--;
                    if (getDistance() < 6 || isBricksForTank1()) {
                        y--;
                        x++;
                    }
                }
                direction = Directions.up_left;
            } else if (isKeyPressed(KeyEvent.VK_UP) && isKeyPressed(KeyEvent.VK_RIGHT)) {
                // Handle up_right
                if (y < maxHeight - 6 && x < maxWidth - 6) {
                    y++;
                    x++;
                    if (getDistance() < 6 || isBricksForTank1()) {
                        y--;
                        x--;
                    }
                }
                direction = Directions.up_right;
            } else if (isKeyPressed(KeyEvent.VK_LEFT)) {
                // Handle left
                if (x > 0) {
                    x--;
                    if (getDistance() < 6 || isBricksForTank1()) {
                        x++;
                    }
                }
                direction = Directions.left;
            } else if (isKeyPressed(KeyEvent.VK_RIGHT)) {
                // Handle right
                if (x < maxWidth - 6) {
                    x++;
                    if (getDistance() < 6 || isBricksForTank1()) {
                        x--;
                    }
                }
                direction = Directions.right;
            } else if (isKeyPressed(KeyEvent.VK_UP)) {
                // Handle up
//                System.out.println(getEnemyDistance());
                if (y < maxHeight - 6) {
                    y++;
                    if (getDistance() < 6 || isBricksForTank1()) {
                        y--;
                    }
                }
                direction = Directions.up;
            } else if (isKeyPressed(KeyEvent.VK_DOWN)) {
                // Handle down
                if (y > 0) {
                    y--;
                    if (getDistance() < 6 || isBricksForTank1()) {
                        y++;
                    }
                }
                direction = Directions.down;
            }

            //  twoPlayer
            if (isKeyPressed(KeyEvent.VK_Q)) {
                onOff = true;
                bullSound();
                // fire
                if (bulletX2 == x2 && bulletY2 == y2) {
                    bullets2.add(new Bullet(direction2, x2, y2));

                } else if ((Math.abs(bulletX2 - x2) >= 10) || (Math.abs(bulletY2 - y2) >= 5)) {
                    bullets2.add(new Bullet(direction2, x2, y2));
;
                }

            } else if (isKeyPressed(KeyEvent.VK_A) && isKeyPressed(KeyEvent.VK_W)) {
                if (x2 > -maxWidth + 6 && y2 < 0) {
                    x2--;
                    y2++;
                    if (getDistance() < 6 || isBricksForTank2()) {
                        x2++;
                        y2--;
                    }
                }
                direction2 = Directions.W_A;
            } else if (isKeyPressed(KeyEvent.VK_D) && isKeyPressed(KeyEvent.VK_W)) {
                if (x2 < 0 && y2 < 0) {
                    x2++;
                    y2++;
                    if (getDistance() < 6 || isBricksForTank2()) {
                        x2--;
                        y2--;
                    }
                }
                direction2 = Directions.W_D;
            } else if (isKeyPressed(KeyEvent.VK_A) && isKeyPressed(KeyEvent.VK_S)) {
                if (x2 > -maxWidth + 6 && y2 > -maxHeight + 6) {
                    x2--;
                    y2--;
                    if (getDistance() < 6 || isBricksForTank2()) {
                        x2++;
                        y2++;
                    }
                }
                direction2 = Directions.S_A;
            } else if (isKeyPressed(KeyEvent.VK_D) && isKeyPressed(KeyEvent.VK_S)) {
                if (x2 < 0 && y2 > -maxHeight + 6) {
                    x2++;
                    y2--;
                    if (getDistance() < 6 || isBricksForTank2()) {
                        x2--;
                        y2++;
                    }
                }
                direction2 = Directions.S_D;
            } else if (isKeyPressed(KeyEvent.VK_A)) {
                if (x2 > -maxWidth + 6) {
                    x2--;
                    if (getDistance() < 6 || isBricksForTank2()) {
                        x2++;
                    }
                }
                direction2 = Directions.A;
            } else if (isKeyPressed(KeyEvent.VK_D)) {
                if (x2 < 0) {
                    x2++;
                    if (getDistance() < 6 || isBricksForTank2()) {
                        x2--;
                    }
                }
                direction2 = Directions.D;
            } else if (isKeyPressed(KeyEvent.VK_W)) {
                if (y2 < 0) {
                    y2++;
                    if (getDistance() < 6 || isBricksForTank2()) {
                        y2--;
                    }
                }
                direction2 = Directions.W;
            } else if (isKeyPressed(KeyEvent.VK_S)) {
                if (y2 > -maxHeight + 6) {
                    y2--;
                    if (getDistance() < 6 || isBricksForTank2()) {
                        y2++;
                    }
                }
                direction2 = Directions.S;
            }
        }

    }

    private void onOrOffSound() {
        try {

            System.out.println(sound);
            if (sound) {
                System.out.println("here");
                AudioPlayer.player.stop(audios);

            } else {

                AudioPlayer.player.start(audios);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void bullSound() {

        AudioPlayer.player.start(audios1);

    }

    public void mouseClicked(MouseEvent e) {
        double x4 = e.getX();
        double y4 = e.getY();

        Component c = e.getComponent();
        double width = c.getWidth();
        double height = c.getHeight();

        xPosition = (int) ((x4 / width) * 60);
        yPosition = 60 - (int) ((y4 / height) * 60);

        System.out.println("x:" + xPosition + "y:" + yPosition);

        if (home) {
            if (xPosition <= 58 && xPosition >= 52 && yPosition <= 8 && yPosition >= 2) {
                sound = !sound;
                onOrOffSound();
//                home = true;

                //return of how
            }
            if (xPosition <= 58 && xPosition >= 52 && yPosition <= 59 && yPosition >= 54) {
                System.exit(0);
            }
            if (xPosition <= 28 && xPosition >= 4 && yPosition <= 47 && yPosition >= 40) {
                how = false;
                home = false;
                onePlayer = true;
                namePlayer1 = JOptionPane.showInputDialog(null, "name:");
            }

            if ((xPosition >= 4 && xPosition <= 29) && (yPosition >= 6 && yPosition <= 15)) {
                home =false;
                how =true;


            }

            if (xPosition <= 28 && xPosition >= 4 && yPosition <= 31 && yPosition >= 23) {
                how =false;
                home = false;
                twoPlayer = true;
                namePlayer1 = JOptionPane.showInputDialog(null, "name player1:");
                namePlayer2 = JOptionPane.showInputDialog(null, "name player2:");
            }
        }
        if (how){
            if (xPosition >= 0 && xPosition <= 7 && yPosition >= 54 && yPosition <= 60) {
                homePage();
            }


        }
        if (onePlayer) {
            System.out.println("palyer1");
            if (xPosition <= 7 && xPosition >= 0 && yPosition <= 8 && yPosition >= 0) {
                sound = !sound;
                onOrOffSound();

            }
            if (xPosition <= 58 && xPosition >= 52 && yPosition <= 59 && yPosition >= 54) {
                System.exit(0);
            }
            if (xPosition >= 0 && xPosition <= 7 && yPosition >= 54 && yPosition <= 60) {
                homePage();
            }
            if (xPosition <= 54 && xPosition >= 30 && yPosition <= 50 && yPosition >= 41) {
                easy = true;
                onePlayer = false;
                newGame();


            } else if (xPosition <= 53 && xPosition >= 30 && yPosition <= 33 && yPosition >= 24) {

                medium = true;
                onePlayer = false;
                newGame();

            } else if (xPosition <= 53 && xPosition >= 30 && yPosition <= 17 && yPosition >= 8) {
                hard = true;
                onePlayer = false;
                newGame();

            }
        }
        if (twoPlayer) {
            System.out.println("player2");

            if (xPosition <= 7 && xPosition >= 0 && yPosition <= 8 && yPosition >= 0) {
                sound = !sound;
                onOrOffSound();

            }
            if (xPosition <= 58 && xPosition >= 52 && yPosition <= 59 && yPosition >= 54) {
                System.exit(0);
            }
            if (xPosition >= 0 && xPosition <= 7 && yPosition >= 54 && yPosition <= 60) {
                homePage();
            }
            if (xPosition <= 54 && xPosition >= 30 && yPosition <= 50 && yPosition >= 41) {
                easy = true;
                twoPlayer = false;
                newGame();
            } else if (xPosition <= 53 && xPosition >= 30 && yPosition <= 33 && yPosition >= 24) {
                medium = true;
                twoPlayer = false;
                newGame();
            } else if (xPosition <= 53 && xPosition >= 30 && yPosition <= 17 && yPosition >= 8) {

                hard = true;
                twoPlayer = false;
                newGame();

            }
        }

        if (winner) {
            System.out.println("x: " + xPosition + ", y: " + yPosition);
            if (xPosition >= 10 && xPosition <= 32 && yPosition >= 16 && yPosition <= 25) {
                winner = false;
                if (easy) {
                    easy = false;
                    medium = true;
                } else if (medium) {
                    medium = false;
                    hard = true;
                } else if (hard) {
                    homePage();
                }

            } else if (new Point2D(xPosition, yPosition).getDistanceFrom(new Point2D(46, 21)) <= 4) {
                homePage();
                System.out.println("Home Page");
            }

            initGame();
        } else if (gameOver) {
            System.out.println("x: " + xPosition + ", y: " + yPosition);
            if (xPosition >= 23 && xPosition <= 28 && yPosition >= 16 && yPosition <= 18) {
                lives = 3;
                gameOver = false;
            } else if (xPosition >= 32 && xPosition <= 35 && yPosition >= 16 && yPosition <= 18) {
                homePage();
            }
            initGame();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    public BitSet keyBits = new BitSet(256);

    @Override
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void keyTyped(final KeyEvent event) {
        // don't care
    }

    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }

}
