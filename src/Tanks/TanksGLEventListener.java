package Tanks;



import Texture.TextureReader;
import com.sun.opengl.util.Animator;

import components.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import javax.media.opengl.glu.GLU;

public class TanksGLEventListener extends TanksListener {
    int animationIndex = 0;

    private void drawBrick(GL gl, Brick b) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[b.index]);
        
        System.out.println("index: " + b.index);
        System.out.println("x: " + b.position.x);
        
        gl.glPushMatrix();
            gl.glTranslated(b.position.x / (maxWidth / 2.0) - 0.9, b.position.y / (maxHeight / 2.0) - 0.9, 0);
            gl.glScaled(.1, .1, 1);
            
            frontFace(gl);
        gl.glPopMatrix();
        
        gl.glDisable(GL.GL_BLEND);
    }


    public enum Directions {

        up,
        down,
        left,
        right,
        down_right,
        up_left,
        up_right,
        down_left,
        W,
        A,
        S,
        D,
        Q,

        W_A,
        W_D,
        S_A,
        S_D

    }

    Directions direction2 = Directions.S;
    Directions direction = Directions.up;
    ArrayList<Bullet> bullets =new ArrayList<>();
    Map map1 = new Map();
    Map map2 = new Map();
    Map map3 = new Map();
    
    Map currentMap = new Map();

    int maxWidth = 60;
    int maxHeight = 60;
    int xPosition = 0;

    int yPosition = 0;

    int x = 0, y = 0;

    int x2 = 0, y2 = 0;
    int bullx =0 , bully =0 ;
//    int x3 =0 , y3=0;
int bulletX = 0;
int bulletY = 0;
    ArrayList<Point2D> bricksPositions = new ArrayList<>();
    ArrayList<Point2D> whiteBricksPositions = new ArrayList<>();
//    Point2D bricksPositions[] = {
//
//        new Point2D(48, 30),
//        new Point2D(36, 30),
//        new Point2D(42, 30),
//        new Point2D(30, 30),
//        new Point2D(26, 30),
//        new Point2D(26, 24),
//        new Point2D(26, 18),
//        new Point2D(14, 30),
//        new Point2D(8, 30),
//    };
//
//    Point2D whiteBricksPositions[] = {
//        new Point2D(54, 30),
//        new Point2D(20, 30),
//        new Point2D(26, 12),
//        new Point2D(26, 30),
//
//
//    };
    
// bull index 4
    String textureNames[] = {"tankUp.png", "tankRight.png","tankLeft.png","tankDown.png", "bull.png", "bricks.png", "white_bricks.png", "Back.jpg"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    /*
     5 means gun in array pos
     x and y coordinate for gun
     */
    public void init(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        // Convert arrays to ArrayLists
//        bricksPositions.addAll(Arrays.asList(
//                new Point2D(48, 30),
//                new Point2D(36, 30),
//                new Point2D(42, 30),
//                new Point2D(30, 30),
//                new Point2D(26, 30),
//                new Point2D(26, 24),
//                new Point2D(26, 18),
//                new Point2D(14, 30),
//                new Point2D(8, 30)
//        ));

//        whiteBricksPositions.addAll(Arrays.asList(
//                new Point2D(54, 30),
//                new Point2D(20, 30),
//                new Point2D(26, 12),
//                new Point2D(26, 30)
//        ));

        
        // Level 1
        map1.bricks.addAll(Arrays.asList(
            new Brick(new Point2D(48, 30), false),
            new Brick(new Point2D(36, 30), false),
            new Brick(new Point2D(42, 30), false),
            new Brick(new Point2D(30, 30), false),
            new Brick(new Point2D(26, 30), false),
            new Brick(new Point2D(26, 24), false),
            new Brick(new Point2D(26, 18), false),
            new Brick(new Point2D(14, 30), false),
            new Brick(new Point2D(8, 30), false),
            new Brick(new Point2D(54, 30), true),
            new Brick(new Point2D(20, 30), true),
            new Brick(new Point2D(26, 12), true),
            new Brick(new Point2D(26, 30), true)
        ));
        // Level 2
        map2.bricks.addAll(Arrays.asList(
            new Brick( new Point2D(30, 40), true ),
            new Brick( new Point2D(36, 40), false ),
            new Brick( new Point2D(24, 40), false ),
            new Brick( new Point2D(18, 40), true ),
            
            new Brick( new Point2D(30, 20), true ),
            new Brick( new Point2D(36, 20), false ),
            new Brick( new Point2D(24, 20), false ),
            new Brick( new Point2D(18, 20), true ),
            
            new Brick( new Point2D(48, 36), false ),
            new Brick( new Point2D(48, 30), true ),
            new Brick( new Point2D(48, 24), false ),
            new Brick( new Point2D(48, 18), true ),
            new Brick( new Point2D(48, 12), false ),
            
            new Brick( new Point2D(6, 36), false ),
            new Brick( new Point2D(6, 30), true ),
            new Brick( new Point2D(6, 24), false ),
            new Brick( new Point2D(6, 18), true ),
            new Brick( new Point2D(6, 12), false )
        ));
        // Level 3
        map3.bricks.addAll(Arrays.asList(
            new Brick( new Point2D(0, 12), false ),
            new Brick( new Point2D(6, 12), false ),
            new Brick( new Point2D(12, 12), false ),
            new Brick( new Point2D(12, 6), true ),
            new Brick( new Point2D(12, 0), false ),
            
            new Brick( new Point2D(12, 24), false ),
            new Brick( new Point2D(12, 30), false ),
            new Brick( new Point2D(12, 36), false ),
            new Brick( new Point2D(12, 42), false ),
            new Brick( new Point2D(12, 48), false ),
            new Brick( new Point2D(12, 54), true ),
            
            new Brick( new Point2D(18, 42), false ),
            new Brick( new Point2D(24, 42), false ),
            new Brick( new Point2D(30, 42), false ),
            new Brick( new Point2D(36, 42), false ),
            new Brick( new Point2D(42, 42), false ),
            
            new Brick( new Point2D(18, 24), false ),
            new Brick( new Point2D(24, 24), false ),
            new Brick( new Point2D(30, 24), false ),
            new Brick( new Point2D(36, 24), false ),
            new Brick( new Point2D(42, 24), false ),
            
            new Brick( new Point2D(42, 42), false ),
            new Brick( new Point2D(42, 36), false ),
            new Brick( new Point2D(42, 30), false ),
            new Brick( new Point2D(42, 24), false ),
            new Brick( new Point2D(42, 18), true ),
            new Brick( new Point2D(42, 12), false ),
            new Brick( new Point2D(42, 6), false ),
            new Brick( new Point2D(42, 0), false )
        ));
        
        currentMap = map1; // current leve
        
        whiteBricksPositions = currentMap.getWhiteBricksPositions();
        
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
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        DrawBackground(gl);
        handleKeyPress();
        animationIndex = animationIndex % 4;
            DrawTank2(gl, x2, y2, animationIndex, 1, direction2);
            DrawTank(gl, x, y, animationIndex, 1, direction);
//        for (Point2D p : bricksPositions)
//            drawBrick(gl, p, false);
//
//        for (Point2D p : whiteBricksPositions)
//            drawBrick(gl, p, true);

        for (Brick b: currentMap.bricks)
            drawBrick(gl, b);
        
        
//bullet

        for (Bullet bullet :bullets) {
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
                        bullet.x++;
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
                        bullet.x--;
                        break;
                    case up_left:
                        bullet.x--;
                        bullet.y++;
                        break;
                }


                DrawTank(gl, bullet.x , bullet.y+1, textures[3], 0.3f, bullet.directions);
                bullx = x ;
                bully = y;
                bulletX = bullet.x ;
                bulletY = bullet.y;
                boolean isPointInList = false;
//                System.out.println(bullet.y +"-------" +bullet.x );
//bull reach whiteBricksPositions
                //error must break bulletX == 54 && bulletY == 30 first
                if(bulletX == 54 && bulletY == 30  ){


                    if (!whiteBricksPositions.isEmpty()) {
                    for (Point2D point : whiteBricksPositions) {
                        if ((point.x == 54 && point.y ==30 )) {
                            isPointInList = true;
                            break;
                        }
                    }
                    }
                    if (isPointInList) {
                        whiteBricksPositions.remove(0);
                        bullet.x=100;
                        bullet.y=100;
                    }


                } else if( (bulletX == 20 && bulletY ==30 )){

                     isPointInList = false;
                    if (!whiteBricksPositions.isEmpty()) {
                    for (Point2D point : whiteBricksPositions) {
                        if ( (point.x == 20 && point.y ==30 )) {
                            isPointInList = true;
                            break;
                        }
                    }
                    }
                    if (isPointInList) {
                        whiteBricksPositions.remove(0);
                        bullet.x=100;
                        bullet.y=100;
                    }


                }else if( (bulletX == 26 && bulletY ==12 )){

                     isPointInList = false;
                    if (!whiteBricksPositions.isEmpty()) {
                        for (Point2D point : whiteBricksPositions) {
                            if ( (point.x == 26 && point.y ==12 ) ) {
                                isPointInList = true;
                                break;
                            }
                        }
                    }
                    if (isPointInList) {
                        whiteBricksPositions.remove(0);
                        bullet.x=100;
                        bullet.y=100;
                    }


                }else if( (bulletX == 26 && bulletY ==30   )){

                    isPointInList = false;
                    if (!whiteBricksPositions.isEmpty()) {
                        for (Point2D point : whiteBricksPositions) {
                            if ((point.x == 26 && point.y ==30 )) {
                                isPointInList = true;
                                break;
                            }
                        }
                    }
                    if (isPointInList) {
                        whiteBricksPositions.remove(0);
                        bullet.x=100;
                        bullet.y=100;
                    }


                }
            }

        }
//        System.out.println(bullets.get(0));

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void DrawTank(GL gl, int x, int y, int index, float scale, Directions dir){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);    // Turn Blending On
        int angle =0 ; //gl.glRotated(angle,0,0,1); for rotated the to correct dir
        if (dir == Directions.up){
            angle = 0;
        } else if (dir == Directions.down) {
            angle = 180;
        } else if (dir == Directions.left) {
            angle = 90;

        } else if (dir == Directions.right) {
            angle = -90;
        } else if (dir == Directions.down_right) {
            angle =-135;
        }else if (dir == Directions.down_left){
            angle =135;
        } else if (dir == Directions.up_left) {
            angle = 45;
        }else if (dir == Directions.up_right) {
            angle = -45;
        }

        gl.glPushMatrix();
        gl.glTranslated( x/(maxWidth/2.0) - 0.9, y/(maxHeight/2.0) - 0.9, 0);
        gl.glScaled(0.1*scale, 0.1*scale, 1);
        gl.glRotated(angle,0,0,1);
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
    public void DrawTank2(GL gl, int x, int y, int index, float scale, Directions dir){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);    // Turn Blending On
        int angle =0 ; //gl.glRotated(angle,0,0,1); for rotated the to correct dir
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
        gl.glScaled(0.1*scale, 0.1*scale, 1);
        gl.glRotated(angle,0,0,1);
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
//    public void Drawenmay(GL gl,int x, int y, int index, float scale ,Directions dir){
//        gl.glEnable(GL.GL_BLEND);
//        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On
//
//
//        gl.glPushMatrix();
//        gl.glTranslated(x3 / (maxWidth / 2.0) + 0.9, y3 / (maxHeight / 2.0) + 0.9, 0);
//        gl.glScaled(0.1*scale, 0.1*scale, 1);
////        gl.glRotated(angle,0,0,1);
//        //System.out.println(x +" " + y);
//        gl.glBegin(GL.GL_QUADS);
//        // Front Face
//        gl.glTexCoord2f(0.0f, 0.0f);
//        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
//        gl.glTexCoord2f(1.0f, 0.0f);
//        gl.glVertex3f(1.0f, -1.0f, -1.0f);
//        gl.glTexCoord2f(1.0f, 1.0f);
//        gl.glVertex3f(1.0f, 1.0f, -1.0f);
//        gl.glTexCoord2f(0.0f, 1.0f);
//        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
//        gl.glEnd();
//        gl.glPopMatrix();
//
//        gl.glDisable(GL.GL_BLEND);
//    }
    public void DrawBackground(GL gl){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[texture.length-1]);    // Turn Blending On

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
    
    public double getDistance(){
        return  Math.sqrt((x - (x2+54))*(x - (x2+54))+(y - (y2+54))*(y - (y2+54)));
    }
    
    private Point2D getPosition(Point2D p, boolean fromUp) {
        if (fromUp)
            return new Point2D(p.x / (maxWidth / 2.0) + 0.9, p.y / (maxHeight / 2.0) + 0.9);
        
        return new Point2D(p.x / (maxWidth / 2.0) - 0.9, p.y / (maxHeight / 2.0) - 0.9);
    }
    
//    private boolean isBricksForTank1() {
//        Point2D tank1Position = getPosition(new Point2D(x, y), false),
//                bricksPosition = new Point2D();
//        
//        for (Point2D p1: bricksPositions) {
//            bricksPosition = getPosition(p1, false);
//            double distance = tank1Position.getDistanceFrom(bricksPosition);
//            
//            if (distance < .195)
//                return true;
//        }
//        
//        for (Point2D p2: whiteBricksPositions) {
//            bricksPosition = getPosition(p2, false);
//            double distance = tank1Position.getDistanceFrom(bricksPosition);
//            
//            if (distance < .195)
//                return true;
//        }
//        
//        return false;
//    }
    
    private boolean isBricksForTank1() {
        Point2D tank1Position = getPosition(new Point2D(x, y), false),
                bricksPosition = new Point2D();
        
        for (Brick b: currentMap.bricks) {
            bricksPosition = getPosition(b.position, false);
            double distance = tank1Position.getDistanceFrom(bricksPosition);
            
            if (distance < .195)
                return true;
        }
        
        return false;
    }
    
    private boolean isBricksForTank2() {
        Point2D tank2Position = getPosition(new Point2D(x2, y2), true),
                bricksPosition = new Point2D();
        
        for (Brick b: currentMap.bricks) {
            bricksPosition = getPosition(b.position, false);
            double distance = tank2Position.getDistanceFrom(bricksPosition);
            
            if (distance < .195)
                return true;
        }
        
        return false;
    }
    
//    private boolean isBricksForTank2() {
//        Point2D tank2Position = getPosition(new Point2D(x2, y2), true),
//                bricksPosition = new Point2D();
//        
//        for (Point2D p1: bricksPositions) {
//            bricksPosition = getPosition(p1, false);
//            double distance = tank2Position.getDistanceFrom(bricksPosition);
//            
//            if (distance < .195)
//                return true;
//        }
//        
//        for (Point2D p2: whiteBricksPositions) {
//            bricksPosition = getPosition(p2, false);
//            double distance = tank2Position.getDistanceFrom(bricksPosition);
//            
//            if (distance < .195)
//                return true;
//        }
//        
//        return false;
//    }
    
    public void handleKeyPress () {
        if (isKeyPressed(KeyEvent.VK_SPACE)) {
            //fire
            if (bulletX == x && bulletY== y) {
                bullets.add(new Bullet(direction, x, y));
                System.out.println("bulletX1 :" + bulletX);
                System.out.println("bulletY1 :" + bulletY);
                System.out.println("X1 :" + x);
                System.out.println("Y1 :" + y);
                System.out.println(bullets.size());
            }
            else if ( (Math.abs(bulletX - x) >= 10) ||  (Math.abs(bulletY - y) >= 5)) {
                bullets.add(new Bullet(direction, x, y));
                System.out.println("bulletX2 :" + bulletX);
                System.out.println("bulletY2 :" + bulletY);
                System.out.println("X2 :" + x);
                System.out.println("Y2 :" + y);
                System.out.println(bullets.size());
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

        //  player2
        if (isKeyPressed(KeyEvent.VK_Q)) {
            // fire

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



    @Override
    public void mouseClicked(MouseEvent e) {
        double x4 = e.getX();
        double y4 = e.getY();

//        System.out.println(x + " " + y);
        Component c = e.getComponent();
        double width = c.getWidth();
        double height = c.getHeight();
//        System.out.println(width + " " + height);
//get percent of GLCanvas instead of
//points and then converting it to our
//'100' based coordinate system.
        xPosition = (int) ((x4 / width) * 60);
        yPosition = ((int) ((y4 / height) * 60));
        yPosition = 60 - yPosition;
        System.out.println("x:" +xPosition + "---" +"y:" +yPosition);


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